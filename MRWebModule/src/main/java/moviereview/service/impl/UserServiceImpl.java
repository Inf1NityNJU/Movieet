package moviereview.service.impl;

import moviereview.bean.EvaluateBean;
import moviereview.bean.MovieFull;
import moviereview.bean.UserMini;
import moviereview.model.CollectInfo;
import moviereview.model.EvaluateInfo;
import moviereview.model.Page;
import moviereview.model.User;
import moviereview.repository.CollectRepository;
import moviereview.repository.EvaluateRepository;
import moviereview.repository.UserRepository;
import moviereview.service.MovieService;
import moviereview.service.UserService;
import moviereview.util.ResetState;
import moviereview.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

/**
 * Created by vivian on 2017/5/7.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    MovieService movieService;

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
    @ResponseBody
    @RequestMapping(
            value = "/user",
            method = RequestMethod.GET)
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

    @Override
    public ResultMessage evaluate(String movieId, EvaluateBean evaluateBean) {
        int userId = this.getCurrentUser().getId();
        MovieFull movie = movieService.findMovieByMovieID(movieId);
//        List<Actor> actors = movieService.findActorsByIdMovie(movieId);
//        List<Director> directors = movieService.findDirectorsByIdMovie(movieId);
//        List<Genre> genres = movieService.findGenreByIdMovie(movieId);
        EvaluateInfo evaluateInfo = new EvaluateInfo(userId, movieId, LocalDateTime.now().withNano(0).toString(),
                movie.getRank(), movie.getKind(), movie.getDirectors(), movie.getActors());
        evaluateRepository.save(evaluateInfo);
        System.out.println(evaluateInfo.getGenre());
        System.out.println(evaluateInfo.getDirector());
        System.out.println(evaluateInfo.getActor());
        return ResultMessage.SUCCESS;
    }


    @Override
    public Page<MovieFull> getUserCollect(String orderBy, String order, int size, int page) {

        return null;
    }
}
