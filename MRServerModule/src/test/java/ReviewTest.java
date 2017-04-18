import moviereview.dao.MovieDao;
import moviereview.dao.ReviewDao;
import moviereview.dao.impl.DataConst;
import moviereview.dao.impl.MovieDaoImpl;
import moviereview.dao.impl.ReviewDaoImpl;
import moviereview.model.*;
import moviereview.util.GsonUtil;
import moviereview.util.ShellUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by Kray on 2017/4/12.
 */
public class ReviewTest {

    @Autowired
    ReviewDao reviewDao;

    @Autowired
    MovieDaoImpl movieDao;

    private File movieIMDBFile = new File(DataConst.PYTHON_FILE_LOCATION + "/movieIMDB.txt");
    private File scoreAndReviewFile = new File(DataConst.PYTHON_FILE_LOCATION + "/scoreAndReview.txt");
    private File individualMovieFile = new File(DataConst.PYTHON_FILE_LOCATION + "/individualMovie.txt");

    @Test
    public void testIMDBReviewCount() {
        String imdbID = "tt1372710";
        try {
            System.out.println(ShellUtil.getResultOfShellFromCommand("python3 " + DataConst.PYTHON_FILE_LOCATION + "/MovieIMDBReviewCountGetter.py " + imdbID).trim());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }

    @Test
    public void writeIMDBCount() {
        Set<Movie> movies = new HashSet<Movie>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(movieIMDBFile));
            File writeFile = scoreAndReviewFile;// 指定要写入的文件

            if (!writeFile.exists()) {// 如果文件不存在则创建
                writeFile.createNewFile();
            }
            // 获取该文件的缓冲输出流
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(writeFile));

            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    String[] strings = line.split("#");

                    MovieJson movieJson = GsonUtil.parseJson(strings[3], MovieJson.class);
                    Movie movie = new Movie(strings[0], strings[3], movieJson);
                    movies.add(movie);

                    String result = "";
                    result = result.concat(movie.getId());
                    result = result.concat("#");
                    result = result.concat(strings[2]);
                    result = result.concat("#");
                    result = result.concat(movie.getRating());
                    result = result.concat("#");
                    result = result.concat(movie.getImdbId());
                    result = result.concat("#\n");
//                    result = result.concat(ShellUtil.getResultOfShellFromCommand("python3 " + DataConst.PYTHON_FILE_LOCATION + "/MovieIMDBReviewCountGetter.py " + movie.getImdbId()).trim());
//                    result = result.concat("\n");

                    bufferedWriter.append(result);
                    bufferedWriter.flush();// 清空缓冲区

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testScoreAndReview() {
        File movieScoreAndReviewFile = new File(DataConst.PYTHON_FILE_LOCATION + "/scoreAndReview.txt");

        Set<String> movieSet = new HashSet<String>();

        System.out.println("Tags:");

        for (String t : new String[]{"ACTION", "DRAMA"}) {
            System.out.println(t);
            System.out.println(findMoviesByTag(t).size());
            for (Movie movie : findMoviesByTag(t)) {
                movieSet.add(movie.getId());
                System.out.print("Add: ");
                System.out.println(movie.getId());
            }
        }

        List<Double> scores = new ArrayList<>();
        List<Integer> amount = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(movieScoreAndReviewFile));

            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    String[] strings = line.split("#");

                    //有这个 id
                    if (movieSet.contains(strings[0])) {
                        scores.add(Double.parseDouble(strings[2]));
                        amount.add(Integer.parseInt(strings[4]));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        ScoreAndReviewAmount scoreAndReviewAmount = new ScoreAndReviewAmount(scores, amount);
//        System.out.println(scoreAndReviewAmount.toString());
    }

    /*

                    String result = "";
                    result = result.concat(movie.getId());
                    result = result.concat("#");
                    result = result.concat(strings[2]);
                    result = result.concat("#");
                    result = result.concat(movie.getRating());
                    result = result.concat("#");
                    result = result.concat(movie.getImdbId());
                    result = result.concat("#\n");
     */

    @Test
    public void writeAllMovies() {
        try {
            File writeFile = individualMovieFile;// 指定要写入的文件

            if (!writeFile.exists()) {// 如果文件不存在则创建
                writeFile.createNewFile();
            }
            // 获取该文件的缓冲输出流
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(writeFile));
            for (Movie movie : findMoviesByTag("ALL")) {
                bufferedWriter.write(movie.getId() + "#" + movie.getImdbId() + "\n");
            }
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Movie> findMoviesByTag(String tag) {
        Set<Movie> movies = new HashSet<Movie>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(movieIMDBFile));

            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    String[] strings = line.split("#");
                    String[] tags = strings[2].split(",");

                    if (tag.equals("ALL")) {
                        MovieJson movieJson = GsonUtil.parseJson(strings[3], MovieJson.class);
                        Movie movie = new Movie(strings[0], strings[3], movieJson);
                        movies.add(movie);
//                        System.out.println(movie.getId());
                    } else {
                        for (String t : tags) {
                            if (t.toUpperCase().trim().equals(tag)) {
                                MovieJson movieJson = GsonUtil.parseJson(strings[3], MovieJson.class);
                                Movie movie = new Movie(strings[0], strings[3], movieJson);
                                movies.add(movie);
                                break;
                            }
                        }
                    }
                }

                System.out.println(movies.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Movie> resultMovies = new ArrayList<>();
        resultMovies.addAll(movies);
        return resultMovies;
    }

    @Test
    public void testWriteAmazonCount() {
        File writeFile = scoreAndReviewFile;// 指定要写入的文件
        ArrayList<Movie> movies = (ArrayList<Movie>)findMoviesByTag("ALL");

        try {
            if (!writeFile.exists()) {// 如果文件不存在则创建
                writeFile.createNewFile();
            }
            // 获取该文件的缓冲输出流
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(writeFile));

            for (Movie movie : movies) {
                double sum = 0.0;

                ArrayList<Review> reviews1 = (ArrayList<Review>) findAmazonReviewByMovieId(movie.getId());
                for (Review review : reviews1) {
                    sum += review.getScore();
                }
                ArrayList<Review> reviews2 = (ArrayList<Review>) findIMDBReviewByMovieId(movie.getImdbId(), -1);

                for (Review review : reviews2) {
                    sum += review.getScore();
                }
//                sum += Double.parseDouble(movie.getRating()) * imdbcount;
                sum /= (reviews1.size() + reviews2.size());
                System.out.println(movie.getId() + " , " + sum);

                String result = "";
                result = result.concat(movie.getId());
                result = result.concat("#");
                result = result.concat(movie.getGenre());
                result = result.concat("#");
                result = result.concat(movie.getImdbId());
                result = result.concat("#");
                result = result.concat(movie.getRating());
                result = result.concat("#");
                result = result.concat(reviews2.size() + "");
                result = result.concat("#");
                result = result.concat(String.format("%.1f", sum));
                result = result.concat("#");
                result = result.concat((reviews2.size() + reviews1.size()) + "\n");

                bufferedWriter.append(result);
                bufferedWriter.flush();// 清空缓冲区

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Review> findAmazonReviewByMovieId(String productId) {

        try {
            BufferedReader indexBufferedReader = new BufferedReader(new FileReader(new File(DataConst.FILE_LOCATION + "/movieIndex.txt")));
            //在索引中寻找
            String temp;
            //查询时必要的组件和缓存
            BufferedReader beginBufferedReader = null;
            //保存结果的list
            List<Review> reviews = new ArrayList<Review>();
            try {
                indexBufferedReader.readLine();
                while ((temp = indexBufferedReader.readLine()) != null && !temp.split(":")[0].equals(" " + productId)) ;
                if (temp == null || temp.equals("")) {
                    return new ArrayList<>();
                }
                //确定具体文件索引
                int length = temp.split(":")[1].split("/").length;
                int from = Integer.parseInt(temp.split(":")[1].split("/")[0]);
                int to = Integer.parseInt(temp.split(":")[1].split("/")[length - 1]);

                int beginIndex = getFileIndex(from);
                //开始寻找具体文件

                //开始进行查询
                //初始化管道
                beginBufferedReader = new BufferedReader(new FileReader(new File(DataConst.FILE_LOCATION + "/result" + beginIndex + ".txt")));

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
            return new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getFileIndex(int number) {
        return (number - 1) / DataConst.INFO_IN_ONE_FILE;
    }

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

    public List<Review> findIMDBReviewByMovieId(String imdbID, int page) {

        ArrayList<Review> reviews = new ArrayList<>();
        if (imdbID == null) {
            return new ArrayList<>();
        }

        if (page == -1) {

            //本地读取
            try {
                //读取本地文件，加快速度, 此时获得的是所有的 reviews。
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(DataConst.PYTHON_FILE_LOCATION + "/movieIMDBReview.txt")));
                String line;
                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] strings = line.split("#KRAYC#");
                        try {
                            if (strings[0].equals("imdbid:" + imdbID)) {
                                JSONObject jsonObject = new JSONObject(strings[1]);
                                ReviewIMDB reviewIMDB = GsonUtil.parseJson(jsonObject.toString(), ReviewIMDB.class);
                                Review review = new Review(imdbID, reviewIMDB);
                                reviews.add(review);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return reviews;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ArrayList<>();

        }

        return new ArrayList<>();
    }
}

