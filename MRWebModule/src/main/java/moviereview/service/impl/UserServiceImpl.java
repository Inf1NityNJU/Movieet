package moviereview.service.impl;

import moviereview.bean.UserMini;
import moviereview.model.CollectInfo;
import moviereview.model.EvaluateInfo;
import moviereview.model.User;
import moviereview.repository.CollectRepository;
import moviereview.repository.EvaluateRepository;
import moviereview.repository.UserRepository;
import moviereview.service.UserService;
import moviereview.util.ResetState;
import moviereview.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by vivian on 2017/5/7.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CollectRepository collectRepository;

    @Autowired
    EvaluateRepository evaluateRepository;

    @Override
    public ResultMessage signIn(String username, String password) {
        String realPassword = userRepository.findPasswordByUsername(username);
        if (realPassword == null || !realPassword.equals(password)) {
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
    public UserMini findUserById(int id) {
        return null;
    }

    @Override
    public UserMini findUserByUsername(String username) {
        Integer id = userRepository.findIdByUsername(username);
        if (id != null) {
            UserMini userMini = new UserMini(id, username);
            return userMini;
        }
        return null;
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
    public UserMini getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Integer id = userRepository.findIdByUsername(auth.getName());
            if (id != null) {
                UserMini userMini = new UserMini(id, auth.getName());
                return userMini;
            }
            return null;
        }
        return null;
    }

    @Override
    public ResultMessage collect(String movieId) {
        UserMini user = this.getCurrentUser();
        int userId = user.getId();
        LocalDateTime time = LocalDateTime.now().withNano(0);
        CollectInfo collectInfo = new CollectInfo(userId, movieId, time.toString());
        collectRepository.save(collectInfo);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage cancelCollect(String movieId) {
        UserMini user = this.getCurrentUser();
        int userId = user.getId();
        CollectInfo collectInfo = collectRepository.findCollectInfoByUserIdAndMovieId(userId, movieId);
        collectRepository.delete(collectInfo);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage cancelEvaluate(String movieId) {
        UserMini user = this.getCurrentUser();
        int userId = user.getId();
        EvaluateInfo evaluateInfo = evaluateRepository.findEvaluateInfoByUserIdAndMovieId(userId, movieId);
        evaluateRepository.delete(evaluateInfo);
        return ResultMessage.SUCCESS;
    }
}
