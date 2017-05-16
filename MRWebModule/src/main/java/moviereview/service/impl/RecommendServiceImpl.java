package moviereview.service.impl;


import moviereview.bean.*;
import moviereview.dao.util.DataHelper;
import moviereview.model.*;
import moviereview.model.ActorFactor;
import moviereview.model.DirectorFactor;
import moviereview.model.GenreFactor;
import moviereview.repository.MovieRepository;
import moviereview.repository.UserRepository;
import moviereview.service.RecommendService;
import moviereview.util.MovieGenre;
import moviereview.util.RecommendType;
import moviereview.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by SilverNarcissus on 2017/5/12.
 */
@Service
public class RecommendServiceImpl implements RecommendService {
    /**
     * 用户点选喜爱时增加因子
     */
    private static final double FAVORITE_FACTOR = 5.0;

    /**
     * 用户看过时增加因子
     */
    private static final double VIEWED_FACTOR = 1.0;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 每日推荐
     *
     * @param userId 用户ID
     * @return 每日推荐的6部电影
     */
    @Override
    public Set<Movie> everyDayRecommend(int userId, int limit) {
        System.out.println(userRepository);
        User user = userRepository.findUserById(userId);

        Set<Movie> result = new HashSet<>(limit);
        result.addAll(getFavoriteGenreMovies(user, limit / 3));
        result.addAll(getFavoriteActorMovies(user, limit / 3));
        result.addAll(getFavoriteDirectorMovies(user, limit / 3));

        //如果电影不够则加入最新的电影
        while (result.size() < limit) {
            result.addAll(getNewMovie(limit - result.size()));
        }

        return result;
    }

    /**
     * 看完某部电影之后的推荐
     *
     * @param userId  观看用户ID
     * @param type    用户选择的喜好类型
     * @param content 喜好内容
     * @return 含有最多6部电影的电影集合
     */
    public List<Movie> finishSeeingRecommend(int userId, RecommendType type, String content, int limit) {
        switch (type) {

            case GENRE:
                return movieRepository.findMovieByGenre(content, 0, limit);

            case ACTOR:
                return movieRepository.findMovieByActor(content, 0, limit);

            case DIRECTOR:
                return movieRepository.findMovieByDirector(content, 0, limit);

            default:
                return new ArrayList<>(everyDayRecommend(userId, limit));
        }
    }


    /**
     * 得到最新的电影
     *
     * @param limit 需要得到的电影数量
     * @return 含所需数量的最新的电影的列表
     */
    public List<Movie> getNewMovie(int limit) {
        List<Movie> rowResult = findLatestMovies(0, limit * 5, LocalDate.now().toString());

        //下面生成number个不重复的随机数
        Set<Integer> randomNumbers = new HashSet<>(limit);
        Random random = new Random();
        while (randomNumbers.size() < limit) {
            randomNumbers.add(random.nextInt(limit * 5));
        }
        //
        ArrayList<Movie> result = new ArrayList<>(limit);
        for (int i : randomNumbers) {
            result.add(rowResult.get(i));
        }
        return result;
    }

    private List<Movie> findLatestMovies(int start, int count, String now) {
        List<String> movieIds = movieRepository.findLatestMovieId(start, count, now);
        return movieRepository.findLatestMovies(movieIds);
    }

    @Override
    public ResultMessage addGenreFactorWhenViewed(int userId, MovieGenre movieGenre) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        addGenreFactor(movieGenre, user, VIEWED_FACTOR);

        return ResultMessage.SUCCESS;
    }


    @Override
    public ResultMessage addGenreFactorWhenFavored(int userId, MovieGenre movieGenre) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        addGenreFactor(movieGenre, user, FAVORITE_FACTOR);

        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage addActorFactorWhenViewed(int userId, Actor actor) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        addActorFactor(actor.getIdactor(), user, VIEWED_FACTOR);

        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage addActorFactorWhenFavored(int userId, Actor actor) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        addActorFactor(actor.getIdactor(), user, FAVORITE_FACTOR);

        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage addDirectorFactorWhenViewed(int userId, Director director) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        addDirectorFactor(director.getIddirector(), user, VIEWED_FACTOR);

        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage addDirectorFactorWhenFavored(int userId, Director director) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        addDirectorFactor(director.getIddirector(), user, FAVORITE_FACTOR);

        return ResultMessage.SUCCESS;
    }

    /******************************************************************************
     ************************************private************************************
     ******************************************************************************/

    /*
     * 按类型寻找最喜爱的电影
     */
    private List<Movie> getFavoriteGenreMovies(User user, int limit) {
        ArrayList<GenreFactor> factors = new ArrayList<>(user.getGenreFactors());
        if (factors.size() == 0) {
            return Collections.emptyList();
        }

        Collections.sort(factors);
        MovieGenre genre = factors.get(0).getMovieGenre();

        return movieRepository.findMovieByGenre(genre.toString(), 0, limit);
    }

    /*
     * 按演员寻找最喜爱的电影
     */
    private List<Movie> getFavoriteActorMovies(User user, int limit) {
        ArrayList<ActorFactor> factors = new ArrayList<>(user.getActorFactors());
        if (factors.size() == 0) {
            return Collections.emptyList();
        }

        Collections.sort(factors);
        String actor = factors.get(0).getName();
        return movieRepository.findMovieByActor(actor, 0, limit);
    }

    /*
     * 按导演寻找最喜爱的电影
     */
    private List<Movie> getFavoriteDirectorMovies(User user, int limit) {
        ArrayList<DirectorFactor> factors = new ArrayList<>(user.getDirectorFactors());
        if (factors.size() == 0) {
            return Collections.emptyList();
        }

        Collections.sort(factors);
        String director = factors.get(0).getName();
        return movieRepository.findMovieByDirector(director, 0, limit);
    }

    /**
     * 增加类型因子
     */
    private void addGenreFactor(MovieGenre movieGenre, User user, double quantity) {
        //寻找存在的记录
        for (GenreFactor genreFactor : user.getGenreFactors()) {
            if (genreFactor.getMovieGenre() == movieGenre) {
                genreFactor.setFactor(genreFactor.getFactor() + quantity);
                return;
            }
        }
        //如果没找到，则增加一条新纪录
        GenreFactor genreFactor = new GenreFactor(quantity, movieGenre);
        user.getGenreFactors().add(genreFactor);
    }

    /**
     * 增加演员因子
     */
    private void addActorFactor(String actor, User user, double quantity) {
        //寻找存在的记录
        for (ActorFactor actorFactor : user.getActorFactors()) {
            if (actorFactor.getName().equals(actor)) {
                actorFactor.setFactor(actorFactor.getFactor() + quantity);
                return;
            }
        }
        //如果没找到，则增加一条新纪录
        ActorFactor actorFactor = new ActorFactor(quantity, actor);
        user.getActorFactors().add(actorFactor);
    }

    /**
     * 增加导演因子
     */
    private void addDirectorFactor(String director, User user, double quantity) {
        //寻找存在的记录
        for (DirectorFactor directorFactor : user.getDirectorFactors()) {
            if (directorFactor.getName().equals(director)) {
                directorFactor.setFactor(directorFactor.getFactor() + quantity);
                return;
            }
        }
        //如果没找到，则增加一条新纪录
        DirectorFactor directorFactor = new DirectorFactor(quantity, director);
        user.getDirectorFactors().add(directorFactor);
    }
}