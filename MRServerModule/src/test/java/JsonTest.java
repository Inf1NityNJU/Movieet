import moviereview.dao.MovieDao;
import moviereview.dao.impl.DataConst;
import moviereview.model.Movie;
import moviereview.model.MovieJson;
import moviereview.util.GsonUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Kray on 2017/4/16.
 */
public class JsonTest {

    private File movieIndexWithNameFile = new File(DataConst.PYTHON_FILE_LOCATION + "/movieIndexWithName.txt");
    private File movieIMDBFile = new File(DataConst.PYTHON_FILE_LOCATION + "/movieIMDB.txt");

    @Autowired
    private MovieDao movieDao;

    @Test
    public void testJsonWithUnicode() {
        try {
            BufferedReader indexBufferedReader = new BufferedReader(new FileReader(movieIndexWithNameFile));
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
                    if (splitResult[0].equals("B0098VYCCE")) {
                        String tempID = splitResult[0];

                        movie.setId(tempID);

                        String movieName = "";
                        for (int i = 1; i < splitResult.length; i++) {
                            movieName += splitResult[i];
                        }
                        movie.setName(movieName);

                        //找 IMDB
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(movieIMDBFile));

                        String line;
                        try {
                            while ((line = bufferedReader.readLine()) != null) {
                                String[] strings = line.split("#");
                                if (strings[0].equals(tempID)) {
//                                    String jsonStr = strings[3];
//                                    strings[3].getBytes(Charset.forName("unicode"));
                                    String jsonStr = StringEscapeUtils.unescapeJava(strings[3]);
                                    System.out.println(jsonStr);
                                    MovieJson movieJson = GsonUtil.parseJson(jsonStr, MovieJson.class);
                                    movie = new Movie(tempID, jsonStr, movieJson);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                //找不到电影
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
