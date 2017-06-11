package moviereview.service.impl;

import moviereview.bean.*;
import moviereview.model.*;
import moviereview.repository.*;
import moviereview.service.MovieService;
import moviereview.service.RecommendService;
import moviereview.service.UserService;
import moviereview.util.RecommendType;
import moviereview.util.ResetState;
import moviereview.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

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
    MovieRepository movieRepository;

    @Autowired
    CollectRepository collectRepository;

    @Autowired
    EvaluateRepository evaluateRepository;

    @Autowired
    FollowRepository followRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    GenreRepository genreRepository;

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

        User oldUser = userRepository.findUserByUsername(user.getUsername());
        if (oldUser != null) {
            return ResultMessage.EXIST;
        }
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
    public UserFull findUserById(int id) {
        User user = userRepository.findUserById(id);
        user.setLevel(calculateLevel(id));
        if (user != null) {
            return new UserFull(user);
        }
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

        //添加因子
        List<Integer> actorId = actorRepository.findActorIdByMovieId(movieId);
        for (Integer id : actorId) {
            recommendService.addActorFactorWhenViewed(userId, id);
        }
        List<Integer> directorId = directorRepository.findDirectorIdByMovieId(movieId);
        for (Integer id : directorId) {
            recommendService.addDirectorFactorWhenViewed(userId, id);
        }
        List<Integer> genreId = genreRepository.findGenreIdByIdMovie(movieId);
        for (Integer id : genreId) {
            recommendService.addGenreFactorWhenViewed(userId, id);
        }

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
    public EvaluateResult evaluate(int movieId, EvaluateBean evaluateBean) {
        int userId = this.getCurrentUser().getId();
        EvaluateInfo evaluateInfo = new EvaluateInfo(userId, movieId, evaluateBean);
        boolean hasActor = false;
        boolean hasDirector = false;
        boolean hasGenre = false;
        int actorSize = 0;
        int directorSize = 0;
        int genreSize = 0;

        System.err.println(evaluateBean);
        System.err.println(userId);
        //添加因子
        List<Integer> actorId = evaluateBean.getActor();
        if (actorId.size() != 0) {
            hasActor = true;
            actorSize = actorId.size();
            for (Integer id : actorId) {
                System.err.println("actorid: " + id);
                recommendService.addActorFactorWhenFavored(userId, id);
            }
        }
        List<Integer> directorId = evaluateBean.getDirector();
        if (directorId.size() != 0) {
            hasDirector = true;
            directorSize = directorId.size();
            for (Integer id : directorId) {
                System.err.println("directorid: " + id);
                recommendService.addDirectorFactorWhenFavored(userId, id);
            }
        }
        List<Integer> genreId = evaluateBean.getGenre();
        if (genreId.size() != 0) {
            hasGenre = true;
            genreSize = genreId.size();
            for (Integer id : genreId) {
                System.err.println("genreid: " + id);
                recommendService.addGenreFactorWhenFavored(userId, id);
            }
        }

        EvaluateInfo evaluateInfo1 = evaluateRepository.findEvaluateInfoByUserIdAndMovieId(userId, movieId);
        if (evaluateInfo1 != null) {
            evaluateRepository.delete(evaluateInfo1);
        }
        evaluateRepository.save(evaluateInfo);

        Set<Integer> selectId = new HashSet<>();
        List<Movie> movies = new ArrayList<>();
        Map<Integer, Movie> selectMovies = new HashMap<>();
        selectId.add(movieId);

        //为了SQL正确，创建一个空set
        HashSet<Integer> nullSet = new HashSet<>();
        nullSet.add(-1);
        //该用户已经收藏或评价的电影
        HashSet<Integer> exception = getUserMovies(userId, nullSet);

        Movie selectMovie = new Movie();
        if (hasActor) {
            int count = 0;
            while (count < actorSize) {
                List<Movie> movies1 = recommendService.finishSeeingRecommend(userId, RecommendType.ACTOR, actorRepository.findActorById(evaluateBean.getActor().get(count)), 6);
                int size = movies1.size();
                while (size != 0) {
                    selectMovie = movies1.get(size - 1);
                    if(!exception.contains(selectMovie.getId())) {
                        selectId.add(selectMovie.getId());
                        selectMovies.put(selectMovie.getId(), selectMovie);
                    }
                    size--;
                }
                count++;
            }
        }

        if (hasDirector) {
            int count = 0;
            while (count < directorSize) {
                List<Movie> movies1 = recommendService.finishSeeingRecommend(userId, RecommendType.DIRECTOR, directorRepository.findDirectorNameById(evaluateBean.getDirector().get(count)), 6);
                int size = movies1.size();
                while (size != 0) {
                    selectMovie = movies1.get(size - 1);
                    if(!exception.contains(selectMovie.getId())) {
                        selectId.add(selectMovie.getId());
                        selectMovies.put(selectMovie.getId(), selectMovie);
                    }
                    size--;
                }
                count++;
            }
        }

        if (hasGenre) {
            int count = 0;
            while (count < genreSize) {
                List<Movie> movies1 = recommendService.finishSeeingRecommend(userId, RecommendType.GENRE, genreRepository.findGenreById(evaluateBean.getGenre().get(count)), 6);
                int size = movies1.size();
                while (size != 0) {
                    selectMovie = movies1.get(size - 1);
                    if(!exception.contains(selectMovie.getId())) {
                        selectId.add(selectMovie.getId());
                        selectMovies.put(selectMovie.getId(), selectMovie);
                    }
                    size--;
                }
                count++;
            }
        }

        selectId.remove(movieId);
        for (int i : selectId) {
            movies.add(selectMovies.get(i));
        }

        List<Double> scores = new ArrayList<>();
        for (Movie movie : movies) {
            scores.add(movie.getImdb_score());
        }
        Collections.sort(scores);
        Collections.reverse(scores);

        boolean haveMore = false;
        if (movies.size() > 4) {
            haveMore = true;
        }
        List<MovieMini> movieMinis = new ArrayList<>();
        List<Integer> existMovie = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < scores.size(); i++) {
            Double d = scores.get(i);
            if (count < 4) {
                for (Movie movie : movies) {
                    if (movie.getImdb_score().equals(d) && !existMovie.contains(movie.getId())) {
                        movieMinis.add(movieService.findMovieMiniByMovieID(movie.getId()));
                        existMovie.add(movie.getId());
                        break;
                    }
                }
                if (haveMore) {
                    count++;
                }
            }
        }
        return new EvaluateResult(true, new Page<MovieMini>(1, movieMinis.size(), "score", "desc", 4, movieMinis));
    }

    @Override
    public Page<MovieMini> getUserCollect(String userId, String orderBy, String order, int size, int page) {

        page--;

        ArrayList<CollectInfo> collectInfos = new ArrayList<>();
        if (order.toLowerCase().equals("asc")) {
            collectInfos.addAll(collectRepository.findCollectsInfoByUserIdOrderByTimeAsc(Integer.parseInt(userId), page * size, size));
        } else {
            collectInfos.addAll(collectRepository.findCollecstInfoByUserIdOrderByTimeDesc(Integer.parseInt(userId), page * size, size));
        }

        page++;

        if (collectInfos != null) {
            List<MovieMini> movieMinis = new ArrayList<>();
            for (CollectInfo collectInfo : collectInfos) {
                MovieMini movieMini = movieService.findMovieMiniByMovieID(collectInfo.getMovieId());
                if (movieMini != null) {
                    movieMinis.add(movieMini);
                }
            }

            return new Page<MovieMini>(page, size, orderBy, order, collectInfos.size(), movieMinis);
        }

        return new Page<MovieMini>(page, size, orderBy, order, 0, null);

    }

    @Override
    public Page<MovieMini> getUserEvaluate(String userId, String orderBy, String order, int size, int page) {
        page--;

        ArrayList<EvaluateInfo> evaluateInfos = new ArrayList<>();
        if (order.toLowerCase().equals("asc")) {
            evaluateInfos.addAll(evaluateRepository.findEvaluatesByUserIdOrderByTimeAsc(Integer.parseInt(userId), page * size, size));
        } else {
            evaluateInfos.addAll(evaluateRepository.findEvaluatesByUserIdOrderByTimeDesc(Integer.parseInt(userId), page * size, size));
        }

        page++;

        if (evaluateInfos != null) {
            List<MovieMini> movieMinis = new ArrayList<>();
            for (EvaluateInfo evaluateInfo : evaluateInfos) {
                MovieMini movieMini = movieService.findMovieMiniByMovieID(evaluateInfo.getMovieId());
                movieMinis.add(movieMini);
            }
            return new Page<MovieMini>(page, size, orderBy, order, evaluateInfos.size(), movieMinis);
        }

        return new Page<MovieMini>(page, size, orderBy, order, 0, null);
    }

    @Override
    public List<MovieMini> everyDayRecommend(int size) {
        int userId = this.getCurrentUser().getId();
//        int userId = 0;
        List<MovieMini> movieMinis = new ArrayList<>();
        List<Movie> movies = recommendService.everyDayRecommend(userId, size);
        for (Movie movie : movies) {
            MovieMini movieMini = movieService.findMovieMiniByMovieID(movie.getId());
            movieMinis.add(movieMini);
        }
        return movieMinis;
    }

    @Override
    public ResultMessage survey(List<Integer> genres) {
        int userId = getCurrentUser().getId();
        for (Integer i : genres) {
            recommendService.addGenreFactorWhenFavored(userId, i);
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public double getSimilarity(int userId) {
        int id = getCurrentUser().getId();
        double similarity = recommendService.getSimilarValue(id, userId);
        DecimalFormat df = new DecimalFormat("#.####");
        similarity = Double.parseDouble(df.format(similarity));
        return similarity;
    }

    @Override
    public Page<MovieMini> getSimilarMovie(int limit) {
        int id = getCurrentUser().getId();
        List<Movie> movies = recommendService.getSimilarMovie(id, limit);
        List<MovieMini> movieMinis = new ArrayList<>();
        for (Movie movie : movies) {
            MovieMini movieMini = movieService.findMovieMiniByMovieID(movie.getId());
            movieMinis.add(movieMini);
        }
        return new Page<MovieMini>(1, movieMinis.size(), "similar", "desc", limit, movieMinis);
    }

    @Override
    public StateBean movieStateForUser(int movieId) {
        int userId = this.getCurrentUser().getId();
        CollectInfo collectInfo = collectRepository.findCollectInfoByUserIdAndMovieId(userId, movieId);
        if (collectInfo != null) {
            return new StateBean("collect", null);
        }

        EvaluateInfo evaluateInfo = evaluateRepository.findEvaluateInfoByUserIdAndMovieId(userId, movieId);
        if (evaluateInfo != null) {
            EvaluateBean evaluateBean = new EvaluateBean(evaluateInfo);
            return new StateBean("evaluate", evaluateBean);
        }

        return new StateBean(null, null);
    }

    @Override
    public ResultMessage follow(int userId) {
        int currentUserId = this.getCurrentUser().getId();
        FollowInfo followInfo = new FollowInfo(currentUserId, userId);
        FollowInfo temp = followRepository.findFollowInfoByFolloweridAndFollowingid(currentUserId, userId);
        if (temp != null) {
            followRepository.delete(temp);
        }
        followRepository.save(followInfo);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage cancelFollow(int userId) {
        int currentUserId = this.getCurrentUser().getId();
        FollowInfo followInfo = followRepository.findFollowInfoByFolloweridAndFollowingid(currentUserId, userId);
        if (followInfo != null) {
            followRepository.delete(followInfo);
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public StateBean userState(int userId) {
        int currentUserId = this.getCurrentUser().getId();
        boolean follow = false;
        boolean followed = false;
        List<FollowInfo> followInfos = followRepository.findFollowInfoByFollowerid(currentUserId);
        for (FollowInfo followInfo : followInfos) {
            if (followInfo.getFollowingid() == userId) {
                follow = true;
                break;
            }
        }

        followInfos = followRepository.findFollowInfoByFollowingid(currentUserId);
        for (FollowInfo followInfo : followInfos) {
            if (followInfo.getFollowerid() == userId) {
                followed = true;
                break;
            }
        }

        if (follow && followed) {
            return new StateBean("following", null);
        } else if (follow) {
            return new StateBean("follow", null);
        } else {
            return new StateBean(null, null);
        }
    }

    @Override
    public Page<UserMini> getFollowingList(int userId, String orderBy, String order, int size, int page) {
        page--;

        List<UserMini> userMinis = new ArrayList<>();
        List<FollowInfo> followInfos = new ArrayList<>();
        List<FollowInfo> followInfosAll = new ArrayList<>();
        if (order.toLowerCase().equals("asc")) {
            followInfos.addAll(followRepository.findFollowInfoByFolloweridByTimeAsc(userId, page * size, size));
        } else {
            followInfos.addAll(followRepository.findFollowInfoByFolloweridByTimeDesc(userId, page * size, size));
        }
        page++;
        if (followInfos != null) {
            userMinis = this.followInfosToUserMini(followInfos, "following");
            followInfosAll = followRepository.findFollowInfoByFollowerid(userId);
            return new Page<UserMini>(page, size, orderBy, order, followInfosAll.size(), userMinis);
        }
        return new Page<UserMini>(page, size, orderBy, order, 0, null);
    }

    @Override
    public Page<UserMini> getFollowerList(int userId, String orderBy, String order, int size, int page) {
        page--;

        List<UserMini> userMinis = new ArrayList<>();
        List<FollowInfo> followInfos = new ArrayList<>();
        List<FollowInfo> followInfosAll = new ArrayList<>();
        if (order.toLowerCase().equals("asc")) {
            followInfos.addAll(followRepository.findFollowInfoByFollowingidByTimeAsc(userId, page * size, size));
        } else {
            followInfos.addAll(followRepository.findFollowInfoByFollowingidByTimeDesc(userId, page * size, size));
        }
        page++;
        if (followInfos != null) {
            userMinis = this.followInfosToUserMini(followInfos, "follower");
            followInfosAll = followRepository.findFollowInfoByFollowerid(userId);
            return new Page<UserMini>(page, size, orderBy, order, followInfosAll.size(), userMinis);
        }
        return new Page<UserMini>(page, size, orderBy, order, 0, null);
    }

    private List<UserMini> followInfosToUserMini(List<FollowInfo> followInfos, String type) {
        List<UserMini> userMinis = new ArrayList<>();
        int id = 0;
        for (FollowInfo followInfo : followInfos) {
            if (type.equals("following")) {
                id = followInfo.getFollowingid();
            } else if (type.equals("follower")) {
                id = followInfo.getFollowerid();
            }
            UserMini userMini = new UserMini(userRepository.findUserById(id));
            userMinis.add(userMini);
        }
        return userMinis;
    }


    private int calculateLevel(int userId) {
        int[] level = {1, 20, 50, 100, 200, 300, 500, 800, 1000, 1500};
        int collectScore = 1;
        int evaluateScore = 3;
        int followerScore = 3;
        int followingScore = 1;

        int collectAmount = collectRepository.findCollectAmountByUserId(userId);
        int evaluateAmount = evaluateRepository.findEvaluateAmountByUserId(userId);
        int followerAmount = followRepository.findFollowerAmountByFollowingId(userId);
        int followingAmount = followRepository.findFollowingAmountByFollowerId(userId);

        int score = collectScore * collectAmount + evaluateAmount * evaluateScore +
                followerScore * followerAmount + followingScore * followingAmount;

        for (int i = 0; i < level.length; i++) {
            if (score < level[0]) {
                return 1;
            }
            if (score >= level[i] && score < level[i + 1]) {
                return i + 1;
            }
            if (score >= level[level.length - 1]) {
                return level.length;
            }
        }
        return 0;
    }

    /**
     * 得到该用户收藏过或评价过的所有电影
     *
     * @param userId    用户Id
     * @param exception 不需要的电影Id
     * @return 所有电影Id集合
     */
    private HashSet<Integer> getUserMovies(int userId, Set<Integer> exception) {
        HashSet<Integer> movieIds = new HashSet<>();
        movieIds.addAll(userRepository.getUserCollectWithException(userId, exception));
        movieIds.addAll(userRepository.getUserEvaluateWithException(userId, exception));
        return movieIds;
    }
}
