package moviereview.service.impl;

import moviereview.model.CollectInfo;
import moviereview.model.User;
import moviereview.repository.CollectRepository;
import moviereview.repository.UserRepository;
import moviereview.service.UserService;
import moviereview.util.ResetState;
import moviereview.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by vivian on 2017/5/7.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CollectRepository collectRepository;

    @Override
    public ResultMessage signIn(String username, String password) {
        User user = userRepository.findUserByUsername(username);
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
    public ResetState resetPassword(int id, String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public ResultMessage signUp(User user) {
        userRepository.save(user);
        return ResultMessage.SUCCESS;
    }

    @Override
    public User findUserById(int id) {
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public ResultMessage updateUser(User user) {
        return null;
    }

    @Override
    public ResultMessage deleteUser(int id) {
        return null;
    }

    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return this.findUserByUsername(auth.getName());
        }
        return null;
    }

    @Override
    public ResultMessage collect(CollectInfo collectInfo) {
        Integer num = Math.toIntExact(collectRepository.count());
        collectInfo.setCollectId(num + 1);
        CollectInfo c = collectRepository.save(collectInfo);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage cancelCollect(String movieId) {
        User user = this.getCurrentUser();
        int userId = user.getId();
        CollectInfo collectInfo = collectRepository.findCollectInfoByUserIdAndMovieId(userId, movieId);
        collectRepository.delete(collectInfo);
        return ResultMessage.SUCCESS;
    }
}
