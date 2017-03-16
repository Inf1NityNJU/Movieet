package blservice;

import vo.ReviewCountVO;
import vo.ReviewWordsVO;

/**
 * Created by vivian on 2017/3/13.
 */
public interface UserBLService {
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
    public ReviewCountVO[] findYearCountByUserId(String userId);

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
}
