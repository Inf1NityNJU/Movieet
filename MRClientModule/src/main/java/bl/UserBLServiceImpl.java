package bl;

import blservice.UserBLService;
import vo.ReviewCountVO;
import vo.ReviewWordsVO;
import vo.UserVO;

/**
 * Created by vivian on 2017/3/13.
 */
public class UserBLServiceImpl implements UserBLService {
    private User user;

    public UserBLServiceImpl(User user) {
        this.user = user;
    }

    @Override
    public UserVO getUserVO(String id) {
        return user.getUserVO(id);
    }

    @Override
    public ReviewWordsVO getReviewWordsVO(String userId) {
        return user.getReviewWordsVO(userId);
    }

    @Override
    public ReviewCountVO[] findYearCountByUserId(String userId) {
        return user.findYearCountByUserId(userId);
    }

    @Override
    public ReviewCountVO[] findMonthCountByUserId(String userId, String startMonth, String endMonth) {
        return user.findMonthCountByUserId(userId, startMonth, endMonth);
    }

    @Override
    public ReviewCountVO[] findDayCountByUserId(String userId, String startDate, String endDate) {
        return user.findDayCountByUserId(userId, startDate, endDate);
    }
}
