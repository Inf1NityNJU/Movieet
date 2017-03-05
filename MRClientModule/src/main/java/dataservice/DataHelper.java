package dataservice;

import po.MoviePO;
import po.ReviewPO;

import java.util.Iterator;

/**
 * Created by SilverNarcissus on 2017/3/5.
 */
public interface DataHelper {
    /**
     * 通过电影ID寻找该电影的所有评论
     *
     * @param productId 电影ID
     * @return 所有评论集合的迭代器
     */
    public Iterator<ReviewPO> findReviewByMovieId(String productId);

    /**
     * 通过用户ID寻找该用户的所有评论
     *
     * @param userId 用户ID
     * @return 所有评论集合的迭代器
     */
    public Iterator<ReviewPO> findReviewsByUserId(String userId);

    /**
     * 通过电影ID寻找制定的电影
     *
     * @param productId 电影ID
     * @return 指定的电影
     */
    public MoviePO findMovieByMovieId(String productId);
}
