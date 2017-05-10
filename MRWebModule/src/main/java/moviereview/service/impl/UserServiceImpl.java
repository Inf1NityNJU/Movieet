package moviereview.service.impl;

import moviereview.bean.User;
import moviereview.dao.DataHelper;
import moviereview.dao.DataHelperFactory;
import moviereview.service.UserService;
import moviereview.util.LoginState;
import moviereview.util.ResetState;
import moviereview.util.ResultMessage;
import org.springframework.stereotype.Service;

/**
 * Created by vivian on 2017/5/7.
 */

@Service
public class UserServiceImpl implements UserService{

    private DataHelper<User> dataHelper = DataHelperFactory.getHibernateHelper(User.class);

    @Override
    public LoginState login(String account, String password) {

        return null;
    }

    @Override
    public LoginState logout() {
        return null;
    }

    @Override
    public ResetState reset(String account, String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public ResultMessage add(User user) {
        return null;
    }

    @Override
    public User searchByID(String userID) {
        return null;
    }

    @Override
    public ResultMessage update(User user) {
        return null;
    }

    @Override
    public ResultMessage delete(String userID) {
        return null;
    }
}
