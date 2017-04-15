package moviereview.dao;

import moviereview.model.Review;

import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 2017/3/21.
 */
public interface ReviewDao {

    /**
     * 通过用户ID寻找该用户的所有评论
     *
     * @param userId 用户ID
     * @return 所有评论集合的迭代器
     */
    public List<Review> findReviewsByUserId(String userId);

    /**
     * 通过电影ID寻找该电影的所有Amazon评论
     *
     * @param productId 电影ID
     * @return 所有评论集合的迭代器
     */
    public List<Review> findAmazonReviewByMovieId(String productId);

    /**
     * 通过电影 ID 寻找该电影在 IMDB 上的评论
     *
     * @param productId 电影 ID
     * @return 评论 list
     */
    public List<Review> findIMDBReviewByMovieId(String productId, int page);

    /**
     * 得到 IMDB 中共有多少条评论
     *
     * @param productId 电影ID
     * @return 评论数
     */
    public String findIMDBReviewCountByMovieId(String productId);

    /**
     * 通过电影 ID 寻找该电影的词频统计
     *
     * @param productId 电影ID
     * @return 词频统计的迭代器
     */
    public Map<String, Integer> findWordCountByMovieId(String productId);

    /**
     * 通过用户 ID 寻找用户评论的词频统计
     *
     * @param userId 用户ID
     * @return 词频统计的迭代器
     */
    public Map<String, Integer> findWordCountByUserId(String userId);
}
