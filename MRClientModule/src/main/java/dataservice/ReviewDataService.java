package dataservice;

import po.MoviePO;
import po.ReviewPO;

import java.util.Iterator;
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
}
