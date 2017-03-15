package bl;

import blservice.UserBLService;
import vo.ReviewWordsVO;

/**
 * Created by vivian on 2017/3/13.
 */
public class UserBLServiceImpl implements UserBLService{
    private User user;

    public UserBLServiceImpl(User user) {
        this.user = user;
    }

    @Override
    public ReviewWordsVO getReviewWordsVO(String userId) {
        return user.getReviewWordsVO(userId);
    }
}
