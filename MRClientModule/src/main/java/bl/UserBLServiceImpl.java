package bl;

import blservice.UserBLService;
import vo.ReviewCountVO;
import vo.ReviewWordsVO;

/**
 * Created by vivian on 2017/3/13.
 */
public class UserBLServiceImpl implements UserBLService {
    private User user;

    public UserBLServiceImpl(User user) {
        this.user = user;
    }

    @Override
    public ReviewWordsVO getReviewWordsVO(String userId) {
        return user.getReviewWordsVO(userId);
    }

    @Override
    public ReviewCountVO[] findYearCountByUserId(String userId) {
        return new ReviewCountVO[0];
    }

    @Override
    public ReviewCountVO[] findMonthCountByUserId(String userId, String startMonth, String endMonth) {
        return new ReviewCountVO[0];
    }

    @Override
    public ReviewCountVO[] findDayCountByUserId(String userId, String startDate, String endDate) {
        return new ReviewCountVO[0];
    }
}
