import moviereview.dao.impl.DataConst;
import moviereview.model.Movie;
import moviereview.model.MovieJson;
import moviereview.model.ScoreAndReviewAmount;
import moviereview.util.GsonUtil;
import moviereview.util.ShellUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Kray on 2017/4/12.
 */
public class ReviewTest {

    private File movieIMDBFile = new File(DataConst.PYTHON_FILE_LOCATION + "/movieIMDB.txt");
    private File scoreAndReviewFile = new File(DataConst.PYTHON_FILE_LOCATION + "/scoreAndReview.txt");

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

    public List<Movie> findMoviesByTag(String tag) {
        Set<Movie> movies = new HashSet<Movie>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(movieIMDBFile));

            //TODO:要不要改成，先找 movieindexwithname，然后用 id 找 imdb？找到的概率稍微大一点
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    String[] strings = line.split("#");
                    String[] tags = strings[2].split(",");

                    if (tag.equals("ALL")) {
                        MovieJson movieJson = GsonUtil.parseJson(strings[3], MovieJson.class);
                        Movie movie = new Movie(strings[0], strings[3], movieJson);
                        movies.add(movie);
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
}
