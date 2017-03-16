package moviereview.service;

import moviereview.model.Movie;
import moviereview.model.Review;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 2017/3/7.
 */
public interface MovieService {

    /**
     * 通过电影ID寻找指定的电影
     *
     * @param productId 电影ID
     * @return 指定的电影
     */
    public Movie findMovieByMovieId(String productId);

    /**
     * 通过用户ID寻找该用户的所有评论
     *
     * @param userId 用户ID
     * @return 所有评论集合的迭代器
     */
    public List<Review> findReviewsByUserId(String userId);

    /**
     * 通过电影ID寻找该电影的所有评论
     *
     * @param productId 电影ID
     * @return 所有评论集合的迭代器
     */
    public List<Review> findReviewsByMovieId(String productId);


    /**
     * 通过电影 ID 寻找该电影的词频统计
     *
     * @param productId 电影ID
     * @return 词频统计的迭代器
     */
    public Map<String, Integer> findWordCountByMovieId(String productId);

    /**
     * 通过电影 ID 寻找该电影在 IMDB 上的 JSON 串
     *
     * @param productId 电影 ID
     * @return  JSON 形式的 String
     */
    public Map<String, Object> findIMDBJsonStringByMovieId(String productId);
}
