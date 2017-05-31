package moviereview.service.impl;


import moviereview.bean.MovieMini;
import moviereview.model.*;
import moviereview.repository.*;
import moviereview.service.RecommendService;
import moviereview.util.RecommendType;
import moviereview.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private CountryRepository countryRepository;

    /**
     * 每日推荐
     *
     * @param userId 用户ID
     * @return 每日推荐的6部电影
     */
    @Override
    public List<Movie> everyDayRecommend(int userId, int limit) {
        System.out.println(userRepository);
        User user = userRepository.findUserById(userId);

        Set<Movie> result = new HashSet<>(limit);
        result.addAll(getFavoriteGenreMovies(user, limit / 3));
        System.out.println("!!!!!!!");
        result.addAll(getFavoriteActorMovies(user, limit / 3));
        result.addAll(getFavoriteDirectorMovies(user, limit / 3));

        //如果电影不够则加入最新的电影
        while (result.size() < limit) {
            result.addAll(getNewMovie(limit - result.size()));
        }

        return new ArrayList<>(result);
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
        List<Movie> rowResult = movieRepository.findLatestMovies(limit * 2, LocalDate.now().toString());

        System.out.println(rowResult.size());
        //下面生成number个不重复的随机数
        Set<Integer> randomNumbers = new HashSet<>(limit);
        Random random = new Random();
        while (randomNumbers.size() < limit) {
            randomNumbers.add(random.nextInt(limit * 2 - 1));
        }
        //
        ArrayList<Movie> result = new ArrayList<>(limit);
        for (int i : randomNumbers) {
            result.add(rowResult.get(i));
        }
        return result;
    }


    @Override
    public ResultMessage addGenreFactorWhenViewed(int userId, int genre) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        addGenreFactor(genre, user, VIEWED_FACTOR);

        return ResultMessage.SUCCESS;
    }


    @Override
    public ResultMessage addGenreFactorWhenFavored(int userId, int movieGenre) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        addGenreFactor(movieGenre, user, FAVORITE_FACTOR);

        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage addActorFactorWhenViewed(int userId, int actor) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        addActorFactor(actor, user, VIEWED_FACTOR);

        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage addActorFactorWhenFavored(int userId, int actor) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        addActorFactor(actor, user, FAVORITE_FACTOR);

        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage addDirectorFactorWhenViewed(int userId, int director) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        addDirectorFactor(director, user, VIEWED_FACTOR);

        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage addDirectorFactorWhenFavored(int userId, int director) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        addDirectorFactor(director, user, FAVORITE_FACTOR);

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
        int genreId = factors.get(0).getMovieGenre();
        String genre = genreRepository.findGenreById(genreId);

        return movieRepository.findMovieByGenre(genre, 0, limit);
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

        int actorId = factors.get(0).getName();
        String actor = actorRepository.findActorById(actorId);
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
        int directorId = factors.get(0).getName();
        String director = directorRepository.findDirectorById(directorId);
        return movieRepository.findMovieByDirector(director, 0, limit);
    }

    /**
     * 增加类型因子
     */
    private void addGenreFactor(int movieGenre, User user, double quantity) {
        //寻找存在的记录
        for (GenreFactor genreFactor : user.getGenreFactors()) {
            if (genreFactor.getMovieGenre() == movieGenre) {
                genreFactor.setFactor(genreFactor.getFactor() + quantity);
                userRepository.save(user);
                return;
            }
        }
        //如果没找到，则增加一条新纪录
        GenreFactor genreFactor = new GenreFactor(quantity, movieGenre, user);
        user.getGenreFactors().add(genreFactor);
        userRepository.save(user);
    }

    /**
     * 增加演员因子
     */
    private void addActorFactor(int actor, User user, double quantity) {
        //寻找存在的记录
        for (ActorFactor actorFactor : user.getActorFactors()) {
            if (actorFactor.getName() == (actor)) {
                actorFactor.setFactor(actorFactor.getFactor() + quantity);
                userRepository.save(user);
                return;
            }
        }
        //如果没找到，则增加一条新纪录
        ActorFactor actorFactor = new ActorFactor(quantity, actor, user);
        user.getActorFactors().add(actorFactor);
        userRepository.save(user);
    }

    /**
     * 增加导演因子
     */
    private void addDirectorFactor(int director, User user, double quantity) {
        //寻找存在的记录
        for (DirectorFactor directorFactor : user.getDirectorFactors()) {
            if (directorFactor.getName() == (director)) {
                directorFactor.setFactor(directorFactor.getFactor() + quantity);
                userRepository.save(user);
                return;
            }
        }
        //如果没找到，则增加一条新纪录
        DirectorFactor directorFactor = new DirectorFactor(quantity, director, user);
        user.getDirectorFactors().add(directorFactor);
        userRepository.save(user);
    }

//    public List<MovieMini> findSimilarMovie(int idmovie, int limit) {
//        List<Integer> genres = genreRepository.findGenreIdByIdMovie(idmovie);
//        //System.out.println(genres);
//        Movie movie = movieRepository.findMovieById(idmovie);
//
//
//        double low = movie.getImdb_score() - 1;
//        double high = movie.getImdb_score() + 1;
//
//        List<MovieMini> result = new ArrayList<>(limit);
//        for (Movie finding : movieRepository.findSimilarMovie(idmovie, low, high, genres, limit)) {
//            result.add(new MovieMini(finding, null));
//        }
//
//        return result;
//    }

    public List<MovieMini> findSimilarMovie(int idmovie, int limit) {
        List<Integer> countryId = countryRepository.findCountryIdByIdMovie(idmovie);
//        List<Integer> movieId = movieRepository.
        return null;
    }
}