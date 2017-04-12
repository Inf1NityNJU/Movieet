import moviereview.dao.impl.DataConst;
import moviereview.model.Movie;
import moviereview.model.MovieJson;
import moviereview.util.GsonUtil;
import moviereview.util.ShellUtil;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
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
                    result = result.concat(ShellUtil.getResultOfShellFromCommand("python3 " + DataConst.PYTHON_FILE_LOCATION + "/MovieIMDBReviewCountGetter.py " + movie.getImdbId()).trim());
                    result = result.concat("\n");

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
}
