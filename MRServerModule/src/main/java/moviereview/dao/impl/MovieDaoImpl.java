package moviereview.dao.impl;

import moviereview.model.*;
import moviereview.util.GsonUtil;
import moviereview.util.ShellUtil;
import moviereview.dao.MovieDao;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kray on 2017/3/7.
 */

@Repository
public class MovieDaoImpl implements MovieDao {

    //file
    private File movieIndexFile;
    private File userIndexFile;
    private File movieIndexWithNameFile;
    private File tempResultFile;
    private File movieIMDBFile;
    private File movieScoreAndReviewFile;

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
     * 分割源文件并索引
     */
    public MovieDaoImpl() {
        //初始化file
        File resultFile = new File(DataConst.FILE_LOCATION + "/result0.txt");
        movieIndexFile = new File(DataConst.FILE_LOCATION + "/movieIndex.txt");
        userIndexFile = new File(DataConst.FILE_LOCATION + "/userIndex.txt");
        movieIndexWithNameFile = new File(DataConst.PYTHON_FILE_LOCATION + "/movieIndexWithName.txt");
        tempResultFile = new File(DataConst.PYTHON_FILE_LOCATION + "/tempResult.txt");
        movieIMDBFile = new File(DataConst.PYTHON_FILE_LOCATION + "/movieIMDB.txt");
        movieScoreAndReviewFile = new File(DataConst.PYTHON_FILE_LOCATION + "/scoreAndReview.txt");
        //初始化一级I/O
        try {
            FileWriter resultWriter = new FileWriter(resultFile, true);
            FileWriter movieIndexWriter = new FileWriter(movieIndexFile, true);
            FileWriter userIndexWriter = new FileWriter(userIndexFile, true);
            FileWriter tempResultWriter = new FileWriter(tempResultFile, false);

            movieIndexBufferedWriter = new BufferedWriter(movieIndexWriter);
            resultBufferedWriter = new BufferedWriter(resultWriter);
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

    /**
     * 通过电影ID寻找指定的电影
     *
     * @param productId 电影ID
     * @return 指定的电影
     */
    public Movie findMovieByMovieId(String productId) {
        try {
            BufferedReader indexBufferedReader = getBufferedReader(movieIndexWithNameFile);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(movieIMDBFile));
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(movieScoreAndReviewFile));
            //在索引中寻找
            String temp = null;
            //查询时必要的组件和缓存
            BufferedReader beginBufferedReader = null;
            //保存结果
            Movie movie = new Movie();
            try {
                while (true) {
                    temp = indexBufferedReader.readLine();

                    if (temp == null) {
                        break;
                    }

                    //找 ID
                    String[] splitResult = temp.split(",");
                    //如果 ID 匹配,找到,设定名字、ID
                    if (splitResult[0].equals(productId)) {
                        String tempID = splitResult[0];

                        movie.setId(tempID);

                        String movieName = "";
                        for (int i = 1; i < splitResult.length; i++) {
                            movieName += splitResult[i];
                        }
                        movie.setName(movieName);

                        //找 IMDB
                        String line;
                        try {
                            while ((line = bufferedReader.readLine()) != null) {
                                String[] strings = line.split("#");
                                if (strings[0].equals(tempID)) {
                                    String jsonStr = strings[3];
                                    MovieJson movieJson = GsonUtil.parseJson(jsonStr, MovieJson.class);
                                    Double score = 0.0;

                                    //todo
                                    String line2;
                                    while ((line2 = bufferedReader2.readLine()) != null) {
                                        String[] strings2 = line2.split("#");

                                        if (strings2[0].equals(productId)) {
                                            //有这个 id
                                            if (strings2[3].equals("N/A")) {
                                                score = -1.0;
                                            } else {
                                                score = Double.parseDouble(strings2[5]);
                                            }
                                        }
                                    }

                                    movie = new Movie(tempID, jsonStr, movieJson, score);
                                    return movie;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("Not found in imdb");
                        return movie;
                    }
                }
                //找不到电影
                System.out.println(productId + " 1");
                return new Movie("-1", "Not Found");
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
            bufferedReader.close();
            indexBufferedReader.close();
            bufferedReader2.close();
            System.out.println(productId + " 2");
            return new Movie("-1", "Not Found");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(productId + " 3");
            return new Movie("-1", "Not Found");
        }
    }

    /**
     * 通过电影 ID 寻找该电影在 IMDB 上的 JSON 串
     *
     * @param productId 电影 ID
     * @return JSON 形式的 String
     */
    public Map<String, Object> findIMDBJsonStringByMovieId(String productId) {
        String stringResult = ShellUtil.getResultOfShellFromCommand("python3 " + DataConst.PYTHON_FILE_LOCATION + "/MovieIMDBGetter.py " + productId);
        try {
            JSONObject jsonObject = new JSONObject(stringResult);
            return jsonObject.toMap();
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }

    /**
     * 根据关键词找电影
     *
     * @param keyword 关键词
     * @return 电影 list
     */
    public List<Movie> findMoviesByKeyword(String keyword) {
        Set<Movie> movies = new HashSet<Movie>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(movieIMDBFile));
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(movieScoreAndReviewFile));

            //TODO:要不要改成，先找 movieindexwithname，然后用 id 找 imdb？找到的概率稍微大一点
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    String[] strings = line.split("#");
                    try {
                        //每个关键词（用空格分割）都要找
                        boolean flag = true;
                        for (String subKeyword : keyword.split(" ")) {
                            flag &= strings[1].toLowerCase().contains(subKeyword);
                        }
                        if (flag) {
                            movies.add(findMovieByMovieId(strings[0]));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Fail to transform movie: " + line);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            bufferedReader.close();
            bufferedReader2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Movie> resultMovies = new ArrayList<>();
        resultMovies.addAll(movies);
        return resultMovies;
    }

    /**
     * 根据分类找电影
     *
     * @param tags 电影分类, 且已经是大写
     * @return 电影 list
     */
    public List<Movie> findMoviesByTags(String[] tags) {
        Set<Movie> movies = new HashSet<Movie>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(movieIMDBFile));
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(movieScoreAndReviewFile));

            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    String[] strings = line.split("#");
                    //tag全都转换成大写
                    String movieTags = strings[2].toUpperCase();

                    boolean flag = true;
                    for (String tag : tags) {
                        tag = tag.toUpperCase();
                        if (tag.equals("ALL")) {
                            flag = true;
                            break;
                        } else {
                            //比对每一个标签是否在这里
                            flag &= movieTags.contains(tag.trim());
                        }
                    }

                    if (flag) {
                        movies.add(findMovieByMovieId(strings[0]));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            bufferedReader.close();
            bufferedReader2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Movie> resultMovies = new ArrayList<>();
        resultMovies.addAll(movies);
        return resultMovies;
    }

    /**
     * 得到电影分类和电影数量的关系
     *
     * @return
     */
    public MovieGenre findMovieGenreCount() {
        try {
            Map<String, Integer> map = new HashMap<>();
            List<Movie> movieList = findMoviesByTags(new String[]{"all"});

            for (Movie movie : movieList) {
                for (String s : movie.getGenre().split(",")) {
                    s = s.trim();
                    if (map.get(s) == null) {
                        map.put(s, 1);
                    } else {
                        map.replace(s, map.get(s), map.get(s) + 1);
                    }
                }
            }

            ArrayList<String> strings = new ArrayList<>();
            strings.addAll(map.keySet());
            ArrayList<Integer> counts = new ArrayList<>();
            counts.addAll(map.values());
            return new MovieGenre(strings, counts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据 tags 得到电影评分和评论数量的关系
     *
     * @param tags
     * @return
     */
    public ScoreAndReviewAmount findScoreAndReviewAmountByTags(String[] tags) {
        Map<String, String> movieSet = new HashMap<>();
        List<String> resultNames = new ArrayList<>();
        List<Double> resultScores = new ArrayList<>();
        List<Integer> resultAmounts = new ArrayList<>();

        for (Movie movie : findMoviesByTags(tags)) {
            movieSet.putIfAbsent(movie.getId(), movie.getName());
        }

        Map<String, Double> scoreSet = new HashMap<>();
        Map<String, Integer> amountSet = new HashMap<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(movieScoreAndReviewFile));

            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    String[] strings = line.split("#");

                    //有这个 id
                    if (movieSet.keySet().contains(strings[0])) {
                        Double score;
                        if (strings[3].equals("N/A")) {
                            score = -1.0;
                        } else {
                            score = Double.parseDouble(strings[5]);
                        }
                        Integer amount = Integer.parseInt(strings[6]);

                        scoreSet.putIfAbsent(strings[0], score);
                        amountSet.putIfAbsent(strings[0], amount);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        resultNames.addAll(movieSet.values());
        resultScores.addAll(scoreSet.values());
        resultAmounts.addAll(amountSet.values());

        return new ScoreAndReviewAmount(resultNames, resultScores, resultAmounts);
    }
}
