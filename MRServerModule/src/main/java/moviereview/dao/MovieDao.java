package moviereview.dao;

import moviereview.model.Movie;
import moviereview.model.Review;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 2017/3/7.
 */
public interface MovieDao {

    /**
     * 通过电影ID寻找指定的电影
     *
     * @param productId 电影ID
     * @return 指定的电影
     */
    public Movie findMovieByMovieId(String productId);

    /**
     * 通过电影 ID 寻找该电影在 IMDB 上的 JSON 串
     *
     * @param productId 电影 ID
     * @return  JSON 形式的 String
     */
    public Map<String, Object> findIMDBJsonStringByMovieId(String productId);
}
