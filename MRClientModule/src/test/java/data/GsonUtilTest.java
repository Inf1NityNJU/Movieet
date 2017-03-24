package data;

import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import po.MoviePO;
import po.PagePO;
import po.ReviewPO;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by SilverNarcissus on 2017/3/8.
 */
public class GsonUtilTest {

    @Test
    public void parseJsonWithGson() throws Exception {
        String json = "{"+getJsonAttribute("id")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("name")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("imageURL")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("duration")+":"+1+","+
                getJsonAttribute("genre")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("releaseDate")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("country")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("language")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("plot")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("imdbId")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("director")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("writers")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("actors")+":"+getJsonAttribute("test1")+ "}";

        MoviePO moviePO = GsonUtil.parseJson(json, MoviePO.class);
        assertEquals("test1", moviePO.getId());
        assertEquals("test1", moviePO.getName());
    }

    @Test
    public void parseJsonArrayWithGson() throws Exception {
        String json =  "["+"{"+getJsonAttribute("id")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("name")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("imageURL")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("duration")+":"+1+","+
                getJsonAttribute("genre")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("releaseDate")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("country")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("language")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("plot")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("imdbId")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("director")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("writers")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("actors")+":"+getJsonAttribute("test1")+ "}"+","+

                "{"+getJsonAttribute("id")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("name")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("imageURL")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("duration")+":"+1+","+
                getJsonAttribute("genre")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("releaseDate")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("country")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("language")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("plot")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("imdbId")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("director")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("writers")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("actors")+":"+getJsonAttribute("test1")+ "}"+
                "]";

        List<MoviePO> moviePOList = GsonUtil.parseJsonAsList(json, MoviePO[].class);
        MoviePO moviePO = moviePOList.get(0);
        assertEquals(2, moviePOList.size());
//        assertEquals("TEST1", moviePO.getId());
//        assertEquals("test1", moviePO.getName());
    }

    private String getJsonAttribute(String attribute) {
        return "\"" + attribute + "\"";
    }

    @Test
    public void parseJsonMap(){
        String json = "{" + getJsonAttribute("pageNo") + ":" + "\"2\""+
                "," + getJsonAttribute("pageSize") + ":" + "\"2\"" + ","+
                  getJsonAttribute("orderBy") + ":" + getJsonAttribute("TEST1") +
                "," + getJsonAttribute("result") + ":" + "["+"{"+getJsonAttribute("id")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("name")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("imageURL")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("duration")+":"+1+","+
                getJsonAttribute("genre")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("releaseDate")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("country")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("language")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("plot")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("imdbId")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("director")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("writers")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("actors")+":"+getJsonAttribute("test3")+ "}"+","+

                "{"+getJsonAttribute("id")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("name")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("imageURL")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("duration")+":"+1+","+
                getJsonAttribute("genre")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("releaseDate")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("country")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("language")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("plot")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("imdbId")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("director")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("writers")+":"+getJsonAttribute("test1")+","+
                getJsonAttribute("actors")+":"+getJsonAttribute("Silver")+ "}"+
                "]"+"}";
        PagePO<MoviePO> moviePOPagePO = GsonUtil.parseJsonInGeneric(json,new TypeToken<PagePO<MoviePO>>(){}.getType());
        System.out.println(moviePOPagePO.getResult().size());
        System.out.println(moviePOPagePO.getResult().get(0).getActors());
        System.out.println(moviePOPagePO.getResult().get(1).getActors());

    }

//    {
//        "pageNo": {number}, // 页码
//        "pageSize": {number}, // 每页显示的条目数
//        "orderBy": {string}, 排序字段名称
//        "order": {string}, // 排序方向
//        "totalCount": {number}, // 总条目数
//        "results": [
//        {
//            "id": {number | string}, // 实体id
//            "name": {string}, // 实体名称
//            // 实体其它字段
//        },
//        ...
//    ]
//    }
}