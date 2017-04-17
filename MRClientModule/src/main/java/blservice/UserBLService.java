package blservice;

import util.ReviewSortType;
import vo.*;

/**
 * Created by vivian on 2017/3/13.
 */
public interface UserBLService {
    /**
     * 根据 id 获得相应的UserVO
     *
     * @param id 用户id
     * @return 符合条件的UserVO
     */
    public UserVO findUserById(String id);

    /**
     * 根据 userId 获得评论文字长度分布
     *
     * @param userId 用户ID
     * @return ReviewWordsVO
     */
    public ReviewWordsVO getReviewWordsVO(String userId);

    /**
     * 根据电影 id 查找每年评论数量
     *
     * @param userId
     * @return
     */
    public ReviewCountVO[] findYearCountByUserId(String userId, String startyear, String endYear);

    /**
     * 根据电影 id 查找每月评论数量
     *
     * @param userId
     * @param startMonth eg. 2017-01
     * @param endMonth   eg. 2017-03
     * @return
     */
    public ReviewCountVO[] findMonthCountByUserId(String userId, String startMonth, String endMonth);


    /**
     * 根据电影 id 和起始时间和结束时间查找每日评论数量
     *
     * @param userId
     * @param startDate eg. 2017-01-12
     * @param endDate   eg. 2017-02-03
     * @return
     */
    public ReviewCountVO[] findDayCountByUserId(String userId, String startDate, String endDate);

    /**
     * 根据用户 id 寻找关于该用户的最高分词
     *
     * @param userId 用户 id
     * @return 如果用户id正确且存在，则返回分词列表VO<br>
     * 否则返回null
     */
    public WordVO findWordsByUserId(String userId);

    /**
     * 根据用户Id得到评论详情（Amazon上的电影），sortType表示电影评论详情的排序方法
     *
     * @param movieId        用户Id
     * @param reviewSortType 评论详情的排序方法
     * @return 相应的ReivewVOs
     */
    public PageVO<ReviewVO> findReviewsByUserIdInPage(String movieId, ReviewSortType reviewSortType, int page);
}
