package bl;

import blservice.UserBLService;

/**
 * Created by vivian on 2017/3/16.
 */
public class UserBLFactory {

    private static User user = new User();
    private static UserBLService userBLService;

    public synchronized static UserBLService getMovieBLService() {
        if (userBLService == null) {
            userBLService = new UserBLServiceImpl(user);
        }
        return userBLService;
    }
}
