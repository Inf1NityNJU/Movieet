package bl;

import blservice.UserBLService;
import util.ReviewSortType;
import vo.*;

/**
 * Created by vivian on 2017/3/13.
 */
class UserBLServiceImpl implements UserBLService {
    private User user;

    public UserBLServiceImpl(User user) {
        this.user = user;
    }

    @Override
    public UserVO findUserById(String id) {
        return user.findUserById(id);
    }

    @Override
    public ReviewWordsVO getReviewWordsVO(String userId) {
        return user.getReviewWordsLengthVO(userId);
    }

    @Override
    public ReviewCountVO[] findYearCountByUserId(String userId, String startyear, String endYear) {
        return user.findYearCountByUserId(userId, startyear, endYear);
    }

    @Override
    public ReviewCountVO[] findMonthCountByUserId(String userId, String startMonth, String endMonth) {
        return user.findMonthCountByUserId(userId, startMonth, endMonth);
    }

    @Override
    public ReviewCountVO[] findDayCountByUserId(String userId, String startDate, String endDate) {
        return user.findDayCountByUserId(userId, startDate, endDate);
    }

    @Override
    public WordVO findWordsByUserId(String userId) {
        return user.findWordsByUserId(userId);
    }

    @Override
    public PageVO<ReviewVO> findReviewsByUserIdInPage(String movieId, ReviewSortType reviewSortType, int page) {
        return user.findReviewsByUserIdInPage(movieId, reviewSortType, page);
    }
}
