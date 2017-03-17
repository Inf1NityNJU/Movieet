package dataservice;

import po.MoviePO;
import po.ReviewPO;
import po.WordPO;

import java.util.List;

/**
 * Created by SilverNarcissus on 2017/3/5.
 */
public interface ReviewDataService {
    /**
     * 通过电影ID寻找该电影的所有评论
     *
     * @param productId 电影ID
     * @return 所有评论集合的迭代器
     */
    public List<ReviewPO> findReviewsByMovieId(String productId);

    /**
     * 通过用户ID寻找该用户的所有评论
     *
     * @param userId 用户ID
     * @return 所有评论集合的迭代器
     */
    public List<ReviewPO> findReviewsByUserId(String userId);

    /**
     * 通过电影ID寻找指定的电影
     *
     * @param productId 电影ID
     * @return 指定的电影
     */
    public MoviePO findMovieByMovieId(String productId);

    /**
     * 根据电影 id 寻找关于该电影的最高分词
     *
     * @param movieId 电影 id
     * @return 分词列表PO
     */
    public WordPO findWordsByMovieId(String movieId);
}
