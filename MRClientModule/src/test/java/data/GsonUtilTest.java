package data;

import org.junit.Test;
import po.MoviePO;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by SilverNarcissus on 2017/3/8.
 */
public class GsonUtilTest {

    @Test
    public void parseJsonWithGson() throws Exception {
        String json = "{" + getJsonAttribute("id") + ":" + getJsonAttribute("TEST") +
                "," + getJsonAttribute("name") + ":" + getJsonAttribute("test") + "}";

        MoviePO moviePO = GsonUtil.parseJson(json, MoviePO.class);
        assertEquals("TEST", moviePO.getId());
        assertEquals("test", moviePO.getName());
    }

    @Test
    public void parseJsonArrayWithGson() throws Exception {
        String json = "[{" + getJsonAttribute("id") + ":" + getJsonAttribute("TEST1") +
                "," + getJsonAttribute("name") + ":" + getJsonAttribute("test1") + "}," +
                "{" + getJsonAttribute("id") + ":" + getJsonAttribute("TEST2") +
                "," + getJsonAttribute("name") + ":" + getJsonAttribute("test2") + "}" + "]";

        List<MoviePO> moviePOList = GsonUtil.parseJsonAsList(json, MoviePO[].class);
        MoviePO moviePO = moviePOList.get(0);
        assertEquals(2, moviePOList.size());
        assertEquals("TEST1", moviePO.getId());
        assertEquals("test1", moviePO.getName());
    }

    private String getJsonAttribute(String attribute) {
        return "\"" + attribute + "\"";
    }

}