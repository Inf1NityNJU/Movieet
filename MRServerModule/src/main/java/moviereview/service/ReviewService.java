package moviereview.service;

import moviereview.model.Page;
import moviereview.model.Review;
import moviereview.util.ReviewSortType;

import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 2017/3/21.
 */
public interface ReviewService {

    /**
     * 通过用户ID寻找该用户的所有评论
     *
     * @param userId 用户ID
     * @return 所有评论集合的迭代器
     */
    public Page<Review> findAmazonReviewByUserId(String userId, int page, String sortType, boolean asc);

    /**
     * 通过电影ID寻找该电影的分页 Amazon 评论
     *
     * @param productId 电影ID
     * @return 所有评论集合的迭代器
     */
    public Page<Review> findAmazonReviewByMovieId(String productId, int page, String sortType, boolean asc);

    /**
     * 通过电影 ID 寻找该电影的分页 IMDB 评论
     *
     * @param productId 电影 ID
     * @return 评论 list
     */
    public Page<Review> findIMDBReviewByMovieId(String productId, int page, String sortType, boolean asc);

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

    /**
     * 获得所有 Amazon 评论
     *
     * @param productId 电影 ID
     * @return
     */
    public List<Review> findAllAmazonReviewById(String productId);

    /**
     * 获得所有 imdb 评论
     *
     * @param productId 电影 ID
     * @return
     */
    public List<Review> findAllIMDBReviewById(String productId);

    /**
     * 获得所有用户评论
     *
     * @param userId
     * @return
     */
    public List<Review> findAllReviewByUserId(String userId);
}
