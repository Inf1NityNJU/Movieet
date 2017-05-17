package moviereview.service.impl;

import moviereview.bean.*;
import moviereview.model.*;
import moviereview.repository.CollectRepository;
import moviereview.repository.EvaluateRepository;
import moviereview.repository.UserRepository;
import moviereview.service.MovieService;
import moviereview.service.RecommendService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivian on 2017/5/7.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    MovieService movieService;

    @Autowired
    RecommendService recommendService;

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
                movie.getRank(), evaluateBean.getTags(), movie.getKind(), movie.getDirectors(), movie.getActors(), evaluateBean.isGenre(), evaluateBean.isDirector(), evaluateBean.isActor());
        evaluateRepository.save(evaluateInfo);
        System.out.println(evaluateInfo.getGenre());
        System.out.println(evaluateInfo.getDirector());
        System.out.println(evaluateInfo.getActor());
        return ResultMessage.SUCCESS;
    }


    @Override
    public Page<MovieFull> getUserCollect(String userId, String orderBy, String order, int size, int page) {

        page--;

        ArrayList<CollectInfo> collectInfos = new ArrayList<>();
        if (order.toLowerCase().equals("asc")) {
            collectInfos.addAll(collectRepository.findCollectsInfoByUserIdOrderByTimeAsc(Integer.parseInt(userId), page * size, size));
        } else {
            collectInfos.addAll(collectRepository.findCollecstInfoByUserIdOrderByTimeDesc(Integer.parseInt(userId), page * size, size));
        }

        page++;

        if (collectInfos!=null){
            List<MovieFull> movieFulls = new ArrayList<>();
            for (CollectInfo collectInfo : collectInfos) {
                MovieFull movieFull = movieService.findMovieByMovieID(collectInfo.getMovieId());
                if (movieFull!=null){
                    movieFulls.add(movieFull);
                }
            }

            return new Page<MovieFull>(page, size, orderBy, order, collectInfos.size(), movieFulls);
        }

        return new Page<MovieFull>(page, size, orderBy, order, 0, null);

    }

    @Override
    public Page<MovieFull> getUserEvaluate(String userId, String orderBy, String order, int size, int page) {
        page--;

        ArrayList<EvaluateInfo> evaluateInfos = new ArrayList<>();
        if (order.toLowerCase().equals("asc")) {
            evaluateInfos.addAll(evaluateRepository.findEvaluatesByUserIdOrderByTimeAsc(Integer.parseInt(userId), page * size, size));
        } else {
            evaluateInfos.addAll(evaluateRepository.findEvaluatesByUserIdOrderByTimeDesc(Integer.parseInt(userId), page * size, size));
        }

        page++;

        List<MovieFull> movieFulls = new ArrayList<>();
        for (EvaluateInfo evaluateInfo : evaluateInfos) {
            MovieFull movieFull = movieService.findMovieByMovieID(evaluateInfo.getMovieId());
            movieFulls.add(movieFull);
        }

        return new Page<MovieFull>(page, size, orderBy, order, evaluateInfos.size(), movieFulls);
    }

    @Override
    public List<MovieMini> everyDayRecommend(int size) {
        int userId = this.getCurrentUser().getId();
//        int userId = 0;
        List<MovieMini> movieMinis = new ArrayList<>();
        List<Movie> movies = recommendService.everyDayRecommend(userId, size);
        for (Movie movie : movies) {
            MovieMini movieMini = new MovieMini(movie);
            movieMinis.add(movieMini);
        }
        return movieMinis;
    }

    @Override
    public MovieStateForUser movieStateForUser(String movieId) {
        int userId = this.getCurrentUser().getId();
        CollectInfo collectInfo = collectRepository.findCollectInfoByUserIdAndMovieId(userId, movieId);
        if (collectInfo != null) {
            return new MovieStateForUser("collect", null);
        }

        EvaluateInfo evaluateInfo = evaluateRepository.findEvaluateInfoByUserIdAndMovieId(userId, movieId);
        if (evaluateInfo != null) {
            EvaluateBean evaluateBean = new EvaluateBean((int) evaluateInfo.getScore(), evaluateInfo.getTags(),
                    evaluateInfo.isLike_genre(), evaluateInfo.isLike_director(), evaluateInfo.isLike_actor());
            return new MovieStateForUser("evaluate", evaluateBean);
        }

        return new MovieStateForUser("", null);
    }
}
