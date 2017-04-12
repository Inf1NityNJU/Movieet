import moviereview.dao.impl.DataConst;
import moviereview.model.Movie;
import moviereview.model.MovieGenre;
import moviereview.model.MovieJson;
import moviereview.util.GsonUtil;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kray on 2017/4/12.
 */
public class MovieTest {

    File movieIMDBFile = new File(DataConst.PYTHON_FILE_LOCATION + "/movieIMDB.txt");

    @Test
    public void testFindGenre() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(movieIMDBFile));

            String line;
//            MovieGenre movieGenre = new MovieGenre();
            Map<String, Integer> map = new HashMap<>();

            try {
                while ((line = bufferedReader.readLine()) != null) {
                    String[] strings = line.split("#");
                    String[] tags = strings[2].split(",");

                    for (String s : tags) {
                        s = s.trim();
                        if (map.get(s) == null) {
                            map.put(s, 0);
                        } else {
                            map.replace(s, map.get(s), map.get(s) + 1);
                        }
                    }
                }

                int i = 0;
                for (String s : map.keySet()) {
                    System.out.print(s + " , ");
                    System.out.println(map.get(s));
                    i += map.get(s);
                }
                System.out.println(i);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
