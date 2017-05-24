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
        Integer integer = userRepository.findNextId();
        if (integer != null) {
            user.setId(integer + 1);
        } else {
            user.setId(0);
        }
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
    public ResultMessage collect(int movieId) {
        UserMini user = this.getCurrentUser();
        int userId = user.getId();
        LocalDateTime time = LocalDateTime.now().withNano(0);
        CollectInfo collectInfo = new CollectInfo(userId, movieId, time.toString());
        CollectInfo collectInfo1 = collectRepository.findCollectInfoByUserIdAndMovieId(userId, movieId);
        if (collectInfo1 != null) {
            collectRepository.delete(collectInfo1);
        }
        collectRepository.save(collectInfo);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage cancelCollect(int movieId) {
        UserMini user = this.getCurrentUser();
        int userId = user.getId();
        CollectInfo collectInfo = collectRepository.findCollectInfoByUserIdAndMovieId(userId, movieId);
        collectRepository.delete(collectInfo);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage cancelEvaluate(int movieId) {
        UserMini user = this.getCurrentUser();
        int userId = user.getId();
        EvaluateInfo evaluateInfo = evaluateRepository.findEvaluateInfoByUserIdAndMovieId(userId, movieId);
        evaluateRepository.delete(evaluateInfo);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage evaluate(int movieId, EvaluateBean evaluateBean) {
        int userId = this.getCurrentUser().getId();
        MovieFull movie = movieService.findMovieByMovieID(movieId);
        EvaluateInfo evaluateInfo = new EvaluateInfo(userId, movieId, evaluateBean);

//        Random random = new Random();
//        if (evaluateBean. && movie.getActors().size() > 0) {
//            int num = random.nextInt(movie.getActors().size());
//            recommendService.addActorFactorWhenViewed(userId, new Actor(movie.getActors().get(num)));
//            System.out.println(movie.getActors().get(num));
//        }
//
//        if (evaluateBean.isDirector() && movie.getDirectors().size() > 0) {
//            int num = random.nextInt(movie.getDirectors().size());
//            recommendService.addDirectorFactorWhenViewed(userId, new Director(movie.getDirectors().get(num)));
//        }
//
//        if (evaluateBean.isGenre() && movie.getGenres().size() > 0) {
//            int num = random.nextInt(movie.getGenres().size());
//            recommendService.addGenreFactorWhenViewed(userId, MovieGenre.getMovieGenreByName(movie.getGenres().get(num)));
//        }

        EvaluateInfo evaluateInfo1 = evaluateRepository.findEvaluateInfoByUserIdAndMovieId(userId, movieId);
        if (evaluateInfo1 != null) {
            evaluateRepository.delete(evaluateInfo1);
        }
        evaluateRepository.save(evaluateInfo);

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

        if (collectInfos != null) {
            List<MovieFull> movieFulls = new ArrayList<>();
            for (CollectInfo collectInfo : collectInfos) {
                MovieFull movieFull = movieService.findMovieByMovieID(collectInfo.getMovieId());
                if (movieFull != null) {
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
    public MovieStateForUser movieStateForUser(int movieId) {
        int userId = this.getCurrentUser().getId();
        CollectInfo collectInfo = collectRepository.findCollectInfoByUserIdAndMovieId(userId, movieId);
        if (collectInfo != null) {
            return new MovieStateForUser("collect", null);
        }

        EvaluateInfo evaluateInfo = evaluateRepository.findEvaluateInfoByUserIdAndMovieId(userId, movieId);
        if (evaluateInfo != null) {
//            EvaluateBean evaluateBean = new EvaluateBean((int)evaluateInfo.getScore(), evaluateInfo.getKeywords(),
//                    evaluateInfo.getGenre(), evaluateInfo.getDirector(), evaluateInfo.getActor());
//            return new MovieStateForUser("evaluate", evaluateBean);
        }

        return new MovieStateForUser("", null);
    }
}
