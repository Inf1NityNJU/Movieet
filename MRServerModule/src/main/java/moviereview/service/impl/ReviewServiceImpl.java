package moviereview.service.impl;

import moviereview.dao.MovieDao;
import moviereview.dao.ReviewDao;
import moviereview.model.Review;
import moviereview.service.PageService;
import moviereview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 2017/3/21.
 */
@Service
public class ReviewServiceImpl implements ReviewService, PageService<Review>{

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
     * 得到一共要分多少页
     *
     * @param list  总的列表
     * @return  分页数
     */
    public int getTotalPageNum(List<Review> list){
        return list.size() / 10;
    }

    /**
     * 返回第 pageNum 页的数据
     *
     * @param list  总的列表
     * @param pageNum  第几页
     * @return
     */
    public List<Review> getSubListOfPage(List<Review> list, int pageNum){
        //TODO
        return null;
    }
}
