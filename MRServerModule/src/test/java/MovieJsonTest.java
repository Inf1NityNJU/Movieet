import com.google.gson.GsonBuilder;
import moviereview.dao.impl.DataConst;
import moviereview.model.Movie;
import moviereview.model.MovieJson;
import moviereview.util.ShellUtil;
import org.apache.commons.exec.ExecuteException;
import org.json.JSONObject;
import org.junit.Test;
import moviereview.util.GsonUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * Created by Kray on 2017/3/25.
 */
public class MovieJsonTest {

    @Test
    public void testTransformJson() {
        String json = "{\"Title\":\"Zootopia\",\"Year\":\"2016\",\"Rated\":\"PG\",\"Released\":\"04 Mar 2016\",\"Runtime\":\"108 min\",\"Genre\":\"Animation, Adventure, Comedy\",\"Director\":\"Byron Howard, Rich Moore, Jared Bush\",\"Writer\":\"Byron Howard (story by), Rich Moore (story by), Jared Bush (story by), Jim Reardon (story by), Josie Trinidad (story by), Phil Johnston (story by), Jennifer Lee (story by), Jared Bush (screenplay), Phil Johnston (screenplay)\",\"Actors\":\"Ginnifer Goodwin, Jason Bateman, Idris Elba, Jenny Slate\",\"Plot\":\"From the largest elephant to the smallest shrew, the city of Zootopia is a mammal metropolis where various animals live and thrive. When Judy Hopps becomes the first rabbit to join the police force, she quickly learns how tough it is to enforce the law. Determined to prove herself, Judy jumps at the opportunity to solve a mysterious case. Unfortunately, that means working with Nick Wilde, a wily fox who makes her job even harder.\",\"Language\":\"English\",\"Country\":\"USA\",\"Awards\":\"Nominated for 1 Oscar. Another 26 wins & 52 nominations.\",\"Poster\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BOTMyMjEyNzIzMV5BMl5BanBnXkFtZTgwNzIyNjU0NzE@._V1_SX300.jpg\",\"Metascore\":\"78\",\"imdbRating\":\"8.1\",\"imdbVotes\":\"268,192\",\"imdbID\":\"tt2948356\",\"Type\":\"movie\",\"Response\":\"True\"}";
        MovieJson movieJson = GsonUtil.parseJson(json, MovieJson.class);
        System.out.println(movieJson.toString());
    }

    @Test
    public void testWriteIMDBJsonToFile() {
        ArrayList<String> cantLoadMovieIDs = new ArrayList<>();

        int i = 0;

        File readFile = new File(DataConst.FILE_LOCATION + "/movieIndexWithName.txt");// 指定要读取的文件
        File writeFile = new File(DataConst.FILE_LOCATION + "/movieIMDB.txt");// 指定要写入的文件
        try {
            // 获得该文件的缓冲输入流
            BufferedReader bufferedReader = new BufferedReader(new FileReader(readFile));

            if (!writeFile.exists()) {// 如果文件不存在则创建
                writeFile.createNewFile();
            }
            // 获取该文件的缓冲输出流
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(writeFile));

            String line;// 用来保存每次读取一行的内容
            while ((line = bufferedReader.readLine()) != null) {

                i++;

                System.out.println(i);


                try {
                    sleep(5);

                    String[] strings = line.split(",");

                    if (strings.length != 2) {
                        cantLoadMovieIDs.add(line);
                        continue;
                    }

                    String movieID = strings[0];

                    String result = ShellUtil.getResultOfShellFromCommand("python3 " + DataConst.PYTHON_FILE_LOCATION + "/MovieIMDBGetter.py " + movieID);

                    try {
                        if (result.equals("") || result.equals("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}\n")) {
                            cantLoadMovieIDs.add(line);
                            continue;
                        } else {
                            try {
                                MovieJson movieJson = GsonUtil.parseJson(result, MovieJson.class);
                                Movie movie = new Movie(movieID, result, movieJson);
                                // 写入电影信息
                                bufferedWriter.append(movie.toString());
                                bufferedWriter.flush();// 清空缓冲区
                            } catch (Exception e) {
                                e.printStackTrace();

                                cantLoadMovieIDs.add(line);
                                continue;
                            }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //读不出来的写进文件
            for (String s : cantLoadMovieIDs) {
                bufferedWriter.append(s);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();// 关闭输出流
            bufferedReader.close();// 关闭输入流

            System.out.println("DONE!!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
