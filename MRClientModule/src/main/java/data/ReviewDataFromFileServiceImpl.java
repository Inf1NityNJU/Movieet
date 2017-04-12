package data;

import dataservice.ReviewDataService;
import po.*;
import util.MovieGenre;
import util.MovieSortType;
import util.ReviewSortType;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by SilverNarcissus on 2017/3/5.
 */
class ReviewDataFromFileServiceImpl implements ReviewDataService {

    //constant
    private static final String SEPARATOR = "--------------------";
    private static final int INFO_IN_ONE_FILE = 1000;
    private static final String FILE_LOCATION = "/Users/SilverNarcissus/Documents/软工三大作业/smallCache";
    //file
    private File movieIndexFile;
    private File userIndexFile;
    /**
     * writer
     */
    //BufferedWriter
    private BufferedWriter resultBufferedWriter;
    private BufferedWriter movieIndexBufferedWriter;
    private BufferedWriter userIndexBufferedWriter;

    /**
     * reader
     */
    //BufferedReader
    private BufferedReader sourceFileBufferedReader;


    /**
     * logger
     */
    private Logger logger;
    private FileHandler fileHandler;

    /**
     * 分割源文件并索引
     *
     * @param filePath 文件路径
     */
    public ReviewDataFromFileServiceImpl(String filePath) {
        //初始化file
        File sourceFile = new File(filePath);
        File resultFile = new File(FILE_LOCATION + "/result0.txt");
        movieIndexFile = new File(FILE_LOCATION + "/movieIndex.txt");
        userIndexFile = new File(FILE_LOCATION + "/userIndex.txt");
        //初始化一级I/O
        try {
            FileReader sourceFileReader = new FileReader(sourceFile);
            FileWriter resultWriter = new FileWriter(resultFile, true);
            FileWriter movieIndexWriter = new FileWriter(movieIndexFile, true);
            FileWriter userIndexWriter = new FileWriter(userIndexFile, true);

            movieIndexBufferedWriter = new BufferedWriter(movieIndexWriter);
            resultBufferedWriter = new BufferedWriter(resultWriter);
            sourceFileBufferedReader = new BufferedReader(sourceFileReader);
            userIndexBufferedWriter = new BufferedWriter(userIndexWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化缓存I/O

        //初始化logger
        initLogger();
    }

    public void trimTxt() {
        //用来保存信息索引
        int count = 1;
        //换行符
        String lineSeparator = System.lineSeparator();
        String lastProductID = "";

        String[] cache = new String[2];

        try {
            for (int i = 0; i < 10000000; i++) {
                if (i % INFO_IN_ONE_FILE == 0) {
                    resultBufferedWriter = changeFileToWrite(resultBufferedWriter, count);
                }

                //写信息间的分隔符
                resultBufferedWriter.write(SEPARATOR + count + SEPARATOR + lineSeparator);
                //------写一条信息------
                for (int number = 1; number < 9; number++) {

                    String line = sourceFileBufferedReader.readLine();
                    //判断是否读到文件尾
                    if (line == null) {
                        flushFiles();
                        logger.config("file split finnish correctly!");
                        return;
                    }
                    cache = line.split(":", 2);

                    //处理脏数据
                    if (cache.length == 1) {
                        resultBufferedWriter.write(" " + cache[0]);
                        number--;
                        continue;
                    }
                    //写分隔符
                    if (number != 1) {
                        resultBufferedWriter.write(lineSeparator);
                    }

                    //做索引
                    //电影索引
                    if (number == 1) {
                        if (lastProductID.equals(cache[1])) {
                            movieIndexBufferedWriter.write("/" + count);
                        } else {
                            movieIndexBufferedWriter.write(lineSeparator);
                            movieIndexBufferedWriter.write(cache[1] + ":" + count);
                            lastProductID = cache[1];
                        }
                    }
                    //用户索引
                    if (number == 2) {
                        userIndexBufferedWriter.write(cache[1] + ":" + count + lineSeparator);
                    }

                    //

                    try {
                        resultBufferedWriter.write(number + ":" + cache[1]);
                    } catch (Exception e) {
                        System.out.println(cache[0]);
                    }

                    //如果是text
                    if (cache[0].contains("text")) {
                        resultBufferedWriter.write(number);
                        String temp;
                        while ((temp = sourceFileBufferedReader.readLine()).length() != 0) {
                            resultBufferedWriter.write(temp + lineSeparator);
                        }
                        resultBufferedWriter.write(lineSeparator);
                    }
                    //如果不是text,则什么也不做
                }
                //------结束写一条信息-----
                count++;
            }
            flushFiles();
        } catch (IOException e) {
            closeFiles();
            e.printStackTrace();
        }
    }

    /**
     * flush buffer
     */
    private void flushFiles() {
        try {
            movieIndexBufferedWriter.flush();
            resultBufferedWriter.flush();
            userIndexBufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * change a file to record new data
     *
     * @param i data NO.
     * @throws IOException handle by up level
     */
    private BufferedWriter changeFileToWrite(BufferedWriter resultBufferedWriter, int i) throws IOException {
        System.out.println("in");

        if (resultBufferedWriter != null) {
            resultBufferedWriter.close();
        }

        File fileToWrite = new File(FILE_LOCATION + "/result" + getFileIndex(i) + ".txt");
        FileWriter resultWriter = new FileWriter(fileToWrite, true);
        return new BufferedWriter(resultWriter);
    }

    /**
     * close file stream
     */
    private void closeFiles() {
        try {
            movieIndexBufferedWriter.close();
            sourceFileBufferedReader.close();
            resultBufferedWriter.close();
            userIndexBufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ReviewPO> findReviewsByMovieId(String productId) {
        BufferedReader indexBufferedReader = getBufferedReader(movieIndexFile);
        //在索引中寻找
        String temp;
        //查询时必要的组件和缓存
        BufferedReader beginBufferedReader = null;
        //保存结果的list
        List<ReviewPO> reviews = new ArrayList<ReviewPO>();
        try {
            indexBufferedReader.readLine();
            while ((temp = indexBufferedReader.readLine()) != null && !temp.split(":")[0].equals(" " + productId)) ;
            if (temp == null) {
                return Collections.emptyList();
            }
            //确定具体文件索引
            int length = temp.split(":")[1].split("/").length;
            int from = Integer.parseInt(temp.split(":")[1].split("/")[0]);
            int to = Integer.parseInt(temp.split(":")[1].split("/")[length - 1]);

            int beginIndex = getFileIndex(from);
            //开始寻找具体文件

            //开始进行查询
            //初始化管道
            beginBufferedReader = getBufferedReader(new File(FILE_LOCATION + "/result" + beginIndex + ".txt"));

            String tag;
            while (true) {
                //找到序号标签
                while (!(tag = beginBufferedReader.readLine()).startsWith(SEPARATOR)) ;
                //找到了合适的标签
                if (Integer.parseInt(tag.split(SEPARATOR)[1]) == from) {
                    for (int k = from; k <= to; k++) {
                        //如果必要，更换文件
                        if ((k - 1) % INFO_IN_ONE_FILE == 0) {
                            beginBufferedReader = changeFileToRead(beginBufferedReader, k);
                            //略过第一个标签
                            beginBufferedReader.readLine();
                        }
                        //test code
                        System.out.println(k);
//                        if (k == 647001) {
//                            for (int sd = 0; sd < 10; sd++) {
//                                System.out.println(beginBufferedReader.readLine());
//                            }
//                        }
                        //
                        reviews.add(parseDataToReviewPO(beginBufferedReader));
                        beginBufferedReader.readLine();
                    }
                    break;
                }
            }


            assert reviews.size() == to - from + 1 : "Error in find movies";

            return reviews;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (beginBufferedReader != null) {
                    beginBufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * get File index by data number
     *
     * @param number data number
     * @return file index
     */
    private int getFileIndex(int number) {
        return (number - 1) / INFO_IN_ONE_FILE;
    }

    /**
     * get BufferedReader connecting to certain file
     *
     * @param fileToRead certain file
     * @return BufferedReader connecting to certain file
     */
    private BufferedReader getBufferedReader(File fileToRead) {
        FileReader reader = null;
        try {
            reader = new FileReader(fileToRead);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert reader != null : "can't connect to file " + fileToRead.getName();

        return new BufferedReader(reader);
    }

    /**
     * get BufferedWriter connecting to certain file
     *
     * @param fileToWrite certain file
     * @return BufferedWriter connecting to certain file
     */
    private BufferedWriter getBufferedWriter(File fileToWrite, boolean append) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileToWrite, append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert writer != null : "can't connect to file " + movieIndexFile.getName();

        return new BufferedWriter(writer);
    }

    /**
     * handle the page fault
     */
    private BufferedReader changeFileToRead(BufferedReader beginBufferedReader, int dataNumber) throws IOException {
        System.out.println("change file in find");

        if (beginBufferedReader != null) {
            beginBufferedReader.close();
        }
        File fileToRead = new File(FILE_LOCATION + "/result" + getFileIndex(dataNumber) + ".txt");
        System.out.println(fileToRead.getName());
        FileReader beginFileReader = new FileReader(fileToRead);
        return new BufferedReader(beginFileReader);

    }

    private ReviewPO parseDataToReviewPO(BufferedReader reader) {
        String[] props = new String[8];
        try {
            for (int i = 0; i < 8; i++) {
                String[] temp = reader.readLine().split(": ");
                if (temp.length == 1) {
                    props[i] = "-1";
                } else {
                    props[i] = temp[1];
                }
            }
//            for (String s : props) {
//                System.out.println(s);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ReviewPO(props[0]
                , props[1]
                , props[2]
                , props[3]
                , Integer.parseInt(props[4].split("\\.")[0])
                , Long.parseLong(props[5])
                , props[6]
                , props[7]
        );
    }

    private void initLogger() {
        logger = Logger.getLogger("DataLogger");

        //设置handler
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        fileHandler = null;
        try {
            fileHandler = new FileHandler("../log.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        fileHandler.setLevel(Level.CONFIG);
        logger.addHandler(fileHandler);

        //设置formatter
        fileHandler.setFormatter(new DataLogFormatter());
    }

    /**
     * close all files
     * warning! This method should only be invoked when exit the system
     */
    public void close() {
        closeFiles();
        fileHandler.close();
    }

    @Override
    public List<ReviewPO> findReviewsByUserId(String userId) {
        //用来读取索引列表
        BufferedReader indexBufferedReader = getBufferedReader(userIndexFile);
        //用来读取信息；
        BufferedReader dataBufferedReader = null;
        //用来保存搜索到的信息编号
        int index = 0;
        //用来暂存上一个信息文件编号
        int previousNumber = -1;
        //缓存一行信息
        String temp;
        List<ReviewPO> reviews = new ArrayList<ReviewPO>();
        try {
            while ((temp = indexBufferedReader.readLine()) != null) {
                //增加索引号
                index++;
                //略过不是该用户ID的索引
                if (!temp.split(":")[0].equals(" " + userId)) {
                    continue;
                }
                //下面就已经找到了需要的索引
                //查看是否需要更换文件
                if (getFileIndex(index) != previousNumber) {
                    dataBufferedReader = changeFileToRead(dataBufferedReader, index);
                }
                //
                String tag;
                while (true) {
                    //找到序号标签
                    while (!(tag = dataBufferedReader.readLine()).startsWith(SEPARATOR)) ;
                    //找到了合适的标签
                    if (Integer.parseInt(tag.split(SEPARATOR)[1]) == index) {
                        reviews.add(parseDataToReviewPO(dataBufferedReader));
                        break;
                    }
                }
            }

            //返回
            for (ReviewPO reviewPO : reviews) {
                System.out.println(reviewPO);
            }
            System.out.println(reviews.size());

            return reviews;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (indexBufferedReader != null) {
                    indexBufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        assert false : "we should't get here!";
        return null;
    }

    @Override
    public MoviePO findMovieByMovieId(String productId) {
        // TODO: 2017/3/5
        return null;
    }

    @Override
    public WordPO findWordsByMovieId(String movieId) {
        return null;
    }

    @Override
    public WordPO findWordsByUserId(String userId) {
        return null;
    }

    @Override
    public PagePO<MoviePO> findMoviesByKeywordInPage(String movieName, int page) {
        return null;
    }

    @Override
    public PagePO<ReviewPO> findReviewsByMovieIdInPageFromAmazon(String productId, ReviewSortType reviewSortType, int page) {
        return null;
    }

    @Override
    public PagePO<ReviewPO> findReviewsByMovieIdInPageFromImdb(String productId, ReviewSortType reviewSortType, int page) {
        return null;
    }

    @Override
    public PagePO<MoviePO> findMoviesByTagInPage(EnumSet<MovieGenre> tag, MovieSortType movieSortType, int page) {
        return null;
    }

    @Override
    public MovieGenrePO findMovieGenre() {
        return null;
    }

    @Override
    public ScoreAndReviewAmountPO findRelationBetweenScoreAndReviewAmount() {
        return null;
    }

    @Override
    public boolean checkNetWork() {
        return false;
    }
}
