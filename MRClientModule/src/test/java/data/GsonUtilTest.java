package data;

import org.junit.Test;
import po.MoviePO;

import java.util.List;
import java.util.Map;

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

    @Test
    public void parseJsonMap(){
        String json = "{" + getJsonAttribute("pageNo") + ":" + 2+
                "," + getJsonAttribute("pageSize") + ":" + 2 + ","+
                  getJsonAttribute("orderBy") + ":" + getJsonAttribute("TEST1") +
                "," + getJsonAttribute("result") + ":" + "[{\"id\":1,\"name\":\"silver\"}]";
        Map<String,String> map=GsonUtil.parseJsonAsMap(json);
        map.values().stream().forEach(System.out::println);
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