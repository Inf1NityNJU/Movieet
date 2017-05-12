package moviereview.service.impl;

import moviereview.dao.UserDao;
import moviereview.model.User;
import moviereview.service.UserService;
import moviereview.util.LoginState;
import moviereview.util.ResetState;
import moviereview.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vivian on 2017/5/7.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public ResultMessage signIn(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return ResultMessage.FAILED;
        }

        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage signOut() {
        return null;
    }

    @Override
    public ResetState reset(int id, String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public ResultMessage signUp(User user) {
        ResultMessage resultMessage = userDao.create(user);
        return resultMessage;
    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public ResultMessage update(User user) {
        return null;
    }

    @Override
    public ResultMessage delete(int id) {
        return null;
    }
}
