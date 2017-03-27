package moviereview.service.impl;

import moviereview.dao.ReviewDao;
import moviereview.model.Page;
import moviereview.model.Review;
import moviereview.service.ReviewService;
import moviereview.util.MovieSortType;
import moviereview.util.ReviewComparator;
import moviereview.util.ReviewSortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 2017/3/21.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    /**
     * 通过用户ID寻找该用户的所有评论
     *
     * @param userId 用户ID
     * @return 所有评论集合的迭代器
     */
    public List<Review> findReviewsByUserId(String userId) {
        return reviewDao.findReviewsByUserId(userId);
    }

    /**
     * 通过电影 ID 寻找该电影的词频统计
     *
     * @param productId 电影ID
     * @return 词频统计的迭代器
     */
    public Map<String, Integer> findWordCountByMovieId(String productId) {
        return reviewDao.findWordCountByMovieId(productId);
    }

    /**
     * 通过用户 ID 寻找用户评论的词频统计
     *
     * @param userId 用户ID
     * @return 词频统计的迭代器
     */
    public Map<String, Integer> findWordCountByUserId(String userId) {
        return reviewDao.findWordCountByUserId(userId);
    }

    /**
     * 通过电影ID寻找该电影的所有评论
     *
     * @param productId 电影ID
     * @return 所有评论集合的迭代器
     */
    public List<Review> findReviewsByMovieId(String productId) {
        return reviewDao.findReviewsByMovieId(productId);
    }

    /**
     * 根据特定的条件比较评论
     *
     * @param sortType 排序选项
     */
    public void sortReviewsByComparator(List<Review> movies, ReviewSortType sortType) {
        movies.sort(ReviewComparator.sortReviewsBySortType(sortType));
    }

    /**
     * 根据电影Id得到电影详情，sortType表示电影评论详情的排序方法
     *
     * @param movieId       电影Id
     * @param reviewSortType 电影评论详情的排序方法
     * @return 相应的Reivews
     */
    public Page findReviewsByMovieIdInPage(String movieId, ReviewSortType reviewSortType, int page) {
        //TODO
        return null;
    }
}
