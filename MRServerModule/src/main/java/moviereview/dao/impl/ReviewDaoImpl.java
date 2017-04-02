package moviereview.dao.impl;

import moviereview.dao.ReviewDao;
import moviereview.model.Review;
import moviereview.model.ReviewIMDB;
import moviereview.util.GsonUtil;
import moviereview.util.ShellUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kray on 2017/3/21.
 */

@Repository
public class ReviewDaoImpl implements ReviewDao {

    @Autowired
    private MovieDaoImpl movieDao;

    //file
    private File movieIndexFile;
    private File userIndexFile;
    private File movieIndexWithNameFile;
    private File tempResultFile;

    /**
     * writer
     */
    //BufferedWriter
    private BufferedWriter resultBufferedWriter;
    private BufferedWriter movieIndexBufferedWriter;
    private BufferedWriter userIndexBufferedWriter;
    private BufferedWriter tempResultBufferedWriter;

    /**
     * logger
     */
    private Logger logger;
    private FileHandler fileHandler;

    /**
     * flush buffer
     */
    private void flushFiles() {
        try {
            movieIndexBufferedWriter.flush();
            resultBufferedWriter.flush();
            userIndexBufferedWriter.flush();
            tempResultBufferedWriter.flush();
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
        if (resultBufferedWriter != null) {
            resultBufferedWriter.close();
        }

        File fileToWrite = new File(DataConst.FILE_LOCATION + "/result" + getFileIndex(i) + ".txt");
        FileWriter resultWriter = new FileWriter(fileToWrite, true);
        return new BufferedWriter(resultWriter);
    }

    /**
     * close file stream
     */
    private void closeFiles() {
        try {
            movieIndexBufferedWriter.close();
            resultBufferedWriter.close();
            userIndexBufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get File index by data number
     *
     * @param number data number
     * @return file index
     */
    private int getFileIndex(int number) {
        return (number - 1) / DataConst.INFO_IN_ONE_FILE;
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
        if (beginBufferedReader != null) {
            beginBufferedReader.close();
        }
        File fileToRead = new File(DataConst.FILE_LOCATION + "/result" + getFileIndex(dataNumber) + ".txt");
        FileReader beginFileReader = new FileReader(fileToRead);
        return new BufferedReader(beginFileReader);
    }

    private Review parseDataToReviewPO(BufferedReader reader) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Review(props[0]
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

    public ReviewDaoImpl() {
        //初始化file
//        File sourceFile = new File(filePath);
        File resultFile = new File(DataConst.FILE_LOCATION + "/result0.txt");
        movieIndexFile = new File(DataConst.FILE_LOCATION + "/movieIndex.txt");
        userIndexFile = new File(DataConst.FILE_LOCATION + "/userIndex.txt");
        movieIndexWithNameFile = new File(DataConst.FILE_LOCATION + "/movieIndexWithName.txt");
        tempResultFile = new File(DataConst.PYTHON_FILE_LOCATION + "/tempResult.txt");
        //初始化一级I/O
        try {
//            FileReader sourceFileReader = new FileReader(sourceFile);
            FileWriter resultWriter = new FileWriter(resultFile, true);
            FileWriter movieIndexWriter = new FileWriter(movieIndexFile, true);
            FileWriter userIndexWriter = new FileWriter(userIndexFile, true);
            FileWriter tempResultWriter = new FileWriter(tempResultFile, false);

            movieIndexBufferedWriter = new BufferedWriter(movieIndexWriter);
            resultBufferedWriter = new BufferedWriter(resultWriter);
//            sourceFileBufferedReader = new BufferedReader(sourceFileReader);
            userIndexBufferedWriter = new BufferedWriter(userIndexWriter);
            tempResultBufferedWriter = new BufferedWriter(tempResultWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化缓存I/O

        //初始化logger
        initLogger();
    }

    /**
     * 通过用户ID寻找该用户的所有评论
     *
     * @param userId 用户ID
     * @return 所有评论集合的迭代器
     */
    public List<Review> findReviewsByUserId(String userId) {
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
        List<Review> reviews = new ArrayList<Review>();
        try {

            Set<Review> reviewSet = new HashSet<Review>();
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
                    while (!(tag = dataBufferedReader.readLine()).startsWith(DataConst.SEPARATOR)) ;
                    //找到了合适的标签
                    if (Integer.parseInt(tag.split(DataConst.SEPARATOR)[1]) == index) {
                        reviewSet.add(parseDataToReviewPO(dataBufferedReader));
//                        reviews.add(parseDataToReviewPO(dataBufferedReader));
                        break;
                    }
                }
            }

            reviews.addAll(reviewSet);

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

    /**
     * 通过电影ID寻找该电影的所有评论
     *
     * @param productId 电影ID
     * @return 所有评论集合的迭代器
     */
    public List<Review> findReviewsByMovieId(String productId) {

        BufferedReader indexBufferedReader = getBufferedReader(movieIndexFile);
        //在索引中寻找
        String temp;
        //查询时必要的组件和缓存
        BufferedReader beginBufferedReader = null;
        //保存结果的list
        List<Review> reviews = new ArrayList<Review>();
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
            beginBufferedReader = getBufferedReader(new File(DataConst.FILE_LOCATION + "/result" + beginIndex + ".txt"));

            String tag;
            while (true) {
                //找到序号标签
                while (!(tag = beginBufferedReader.readLine()).startsWith(DataConst.SEPARATOR)) ;
                //找到了合适的标签
                if (Integer.parseInt(tag.split(DataConst.SEPARATOR)[1]) == from) {
                    for (int k = from; k <= to; k++) {
                        //如果必要，更换文件
                        if ((k - 1) % DataConst.INFO_IN_ONE_FILE == 0) {
                            beginBufferedReader = changeFileToRead(beginBufferedReader, k);
                            //略过第一个标签
                            beginBufferedReader.readLine();
                        }
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
     * 通过电影 ID 寻找该电影在 IMDB 上的评论
     *
     * @param productId 电影 ID
     * @return 评论 list
     */
    public List<Review> findIMDBReviewByMovieId(String productId, int page) {
        ArrayList<Review> reviews = new ArrayList<>();
        String imdbID = movieDao.findMovieByMovieId(productId).getImdbId();
        String stringResult = ShellUtil.getResultOfShellFromCommand("python3 " + DataConst.PYTHON_FILE_LOCATION + "/MovieIMDBReviewGetter.py " + imdbID + " " + page);
        try {
            JSONArray jsonArray = new JSONArray(stringResult);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    ReviewIMDB reviewIMDB = GsonUtil.parseJson(jsonArray.get(i).toString(), ReviewIMDB.class);
                    Review review = new Review(productId, reviewIMDB);
                    reviews.add(review);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return reviews;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * 通过电影 ID 寻找该电影的词频统计
     *
     * @param productId 电影ID
     * @return 词频统计的迭代器
     */
    public Map<String, Integer> findWordCountByMovieId(String productId) {
        try {
            ArrayList<Review> reviews = (ArrayList<Review>) findReviewsByMovieId(productId);

            //写评论到文件里
            this.writeReviewsIntoFile(reviews);

            //分词
            return this.splitWordsFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<String, Integer>();
    }

    /**
     * 通过用户 ID 寻找用户评论的词频统计
     *
     * @param userId 用户ID
     * @return 词频统计的迭代器
     */
    public Map<String, Integer> findWordCountByUserId(String userId) {

        try {
            ArrayList<Review> reviews = (ArrayList<Review>) findReviewsByUserId(userId);

            //写评论到文件里
            this.writeReviewsIntoFile(reviews);

            //分词
            return this.splitWordsFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<String, Integer>();
    }

    /**
     * 写评论到文件里
     *
     * @param reviews 评论数组
     * @throws Exception
     */
    private void writeReviewsIntoFile(ArrayList<Review> reviews) throws Exception {
        //读取评论写到文件里
        BufferedWriter output = tempResultBufferedWriter;
        try {
            File file = tempResultFile;
            output = getBufferedWriter(file, false);
            output = new BufferedWriter(new FileWriter(file));

            //都放到 Set 里
            Set<Review> reviewSet = new HashSet<Review>();
            for (Review review : reviews) {
                reviewSet.add(review);
            }
            for (Review review : reviewSet) {
                output.write(review.getText());
                output.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) output.close();
        }
    }

    /**
     * 进行分词
     *
     * @return
     */
    private Map<String, Integer> splitWordsFromFile() {
        String wordResult = ShellUtil.getResultOfShellFromFile(DataConst.PYTHON_FILE_LOCATION + "/WordCounter.sh");

        Map<String, Integer> result = new HashMap<String, Integer>();
        String[] pairs = wordResult.split("\n");
        for (String pair : pairs) {
            String[] pairSplit = pair.trim().split(" ");
            if (pairSplit.length == 2) {
                result.put(pairSplit[1], Integer.parseInt(pairSplit[0]));
            }
        }

        Set<Map.Entry<String, Integer>> mapEntries = result.entrySet();
        List<Map.Entry<String, Integer>> aList = new LinkedList<Map.Entry<String, Integer>>(mapEntries);
        Collections.sort(aList, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> ele1, Map.Entry<String, Integer> ele2) {
                return ele2.getValue().compareTo(ele1.getValue());
            }
        });
        Map<String, Integer> aMap2 = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < Math.min(10, aList.size()); i++) {
            Map.Entry<String, Integer> entry = aList.get(i);
            aMap2.put(entry.getKey(), entry.getValue());
        }

        return aMap2;
    }

}
