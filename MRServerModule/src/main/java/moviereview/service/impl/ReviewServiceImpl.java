package moviereview.service.impl;

import moviereview.dao.ReviewDao;
import moviereview.model.Page;
import moviereview.model.Review;
import moviereview.service.ReviewService;
import moviereview.util.ReviewSortType;
import moviereview.util.Sort;
import moviereview.util.comparator.ReviewComparatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public Page<Review> findReviewsByUserId(String userId, int page, String sortType, boolean asc) {
        Sort sort = new Sort(sortType, asc);
        ArrayList<Review> reviews = (ArrayList<Review>) reviewDao.findReviewsByUserId(userId);
        if (reviews == null) {
            return new Page<Review>();
        }
        reviews.sort(ReviewComparatorFactory.sortReviewsBySortType(sort.toString()));

        if (page * 10 > reviews.size()) {
            return new Page<Review>();
        } else {
            return new Page<Review>(
                    page,
                    10,
                    sort.getOrder(),
                    sort.getAsc(),
                    reviews.size() + "",
                    reviews.subList(page * 10, Math.min((page + 1) * 10, reviews.size())));
        }
    }

    /**
     * 通过电影 ID 寻找该电影在 IMDB 上的评论
     *
     * @param productId 电影 ID
     * @return 评论 list
     */
    public Page<Review> findIMDBReviewByMovieId(String productId, int page, String sortType, boolean asc) {
        Sort sort = new Sort(sortType, asc);
        ArrayList<Review> reviews = (ArrayList<Review>) reviewDao.findIMDBReviewByMovieId(productId, page);
        if (reviews == null) {
            return new Page<Review>();
        }
        reviews.sort(ReviewComparatorFactory.sortReviewsBySortType(sort.toString()));

        String count = reviewDao.findIMDBReviewCountByMovieId(productId);
        if (count.equals("-1") || count.equals("") || count == null) {
            return new Page<Review>();
        }
        int totalImdbReviewCount = Integer.parseInt(count);

        if (page * 10 > totalImdbReviewCount) {
            return new Page<Review>();
        } else {
            return new Page<Review>(
                    page,
                    10,
                    sort.getOrder(),
                    sort.getAsc(),
                    reviewDao.findIMDBReviewCountByMovieId(productId),
                    //在线
//                    reviews);
                    //本地
                    reviews.subList(page * 10, Math.min((page + 1) * 10, reviews.size())));
        }
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
    public Page<Review> findReviewsByMovieId(String productId, int page, String sortType, boolean asc) {
        Sort sort = new Sort(sortType, asc);
        ArrayList<Review> reviews = (ArrayList<Review>) reviewDao.findReviewsByMovieId(productId);
        if (reviews == null) {
            return new Page<Review>();
        }
        reviews.sort(ReviewComparatorFactory.sortReviewsBySortType(sort.toString()));

        if (page * 10 > reviews.size()) {
            return new Page<Review>();
        } else {
            return new Page<Review>(
                    page,
                    10,
                    sort.getOrder(),
                    sort.getAsc(),
                    reviews.size() + "",
                    reviews.subList(page * 10, Math.min((page + 1) * 10, reviews.size())));
        }
    }

    /**
     * 根据电影Id得到电影详情，sortType表示电影评论详情的排序方法
     *
     * @param movieId        电影Id
     * @param reviewSortType 电影评论详情的排序方法
     * @return 相应的Reivews
     */
    public Page findReviewsByMovieIdInPage(String movieId, ReviewSortType reviewSortType, int page) {
        //TODO
        return null;
    }

    /**
     * 获得所有 amazon 评论
     *
     * @param productId
     * @return
     */
    public List<Review> findAllAmazonReviewById(String productId) {
        return reviewDao.findReviewsByMovieId(productId);
    }

    /**
     * 获得所有 imdb 评论
     *
     * @param productId
     * @return
     */
    public List<Review> findAllIMDBReviewById(String productId) {
        int totalCount = Integer.parseInt(reviewDao.findIMDBReviewCountByMovieId(productId));
        List<Review> resultReview = new ArrayList<>();
        for (int i = 0; i < totalCount; i += 10) {
            resultReview.addAll(reviewDao.findIMDBReviewByMovieId(productId, i));
        }
        return resultReview;
    }

}
