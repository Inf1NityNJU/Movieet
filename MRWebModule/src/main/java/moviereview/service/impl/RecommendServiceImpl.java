package moviereview.service.impl;


import moviereview.dao.util.DataHelper;
import moviereview.model.*;
import moviereview.util.MovieGenre;
import moviereview.util.RecommendType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by SilverNarcissus on 2017/5/12.
 *
 */
public class RecommendServiceImpl {
    private static final String SQL_FOR_LATEST_MOVIE = "SELECT * FROM Movie ORDER BY date";
    private static final String SQL_HEAD_FOR_MOVIE = "SELECT * FROM Movie";
    @Autowired
    private DataHelper<UserPO> userDataHelper;

    private DataHelper<Movie> movieDataHelper;

    /**
     * 每日推荐
     *
     * @param userId 用户ID
     * @return 每日推荐的6部电影
     */
    public Set<Movie> everyDayRecommend(String userId) {
        UserPO user = userDataHelper.exactlyQuery("userId", userId, UserPO.class);

        Set<Movie> result = new HashSet<>(6);
        result.addAll(getFavoriteGenreMovies(user, 2));
        result.addAll(getFavoriteActorMovies(user, 2));
        result.addAll(getFavoriteDirectorMovies(user, 2));

        //如果电影不够则加入最新的电影
        while (result.size() < 6) {
            result.addAll(getNewMovie(6 - result.size()));
        }

        return result;
    }

    /**
     * 看完某部电影之后的推荐
     *
     * @param userId 观看用户ID
     * @param type 用户选择的喜好类型
     * @param content 喜好内容
     * @return 含有最多6部电影的电影集合
     */
    public Set<Movie> finishSeeingRecommend(String userId, RecommendType type, String content) {
        switch (type) {

            case GENRE:
                return new HashSet<>(movieDataHelper.sqlQuery(
                        SQL_HEAD_FOR_MOVIE + " WHERE Genre = '" + content + "' limit " + 6 + ";"
                        , Movie.class));

            case ACTOR:
                return new HashSet<>(movieDataHelper.sqlQuery(
                        SQL_HEAD_FOR_MOVIE + " WHERE Actor = '" + content + "' limit " + 6 + ";"
                        , Movie.class));

            case DIRECTOR:
                return new HashSet<>(movieDataHelper.sqlQuery(
                        SQL_HEAD_FOR_MOVIE + " WHERE Director = '" + content + "' limit " + 6 + ";"
                        , Movie.class));

            default:
                return everyDayRecommend(userId);
        }
    }


    /**
     * 得到最新的电影
     *
     * @param number 需要得到的电影数量
     * @return 含所需数量的最新的电影的列表
     */
    private List<Movie> getNewMovie(int number) {
        ArrayList<Movie> rowResult = new ArrayList<>(
                movieDataHelper.sqlQuery(SQL_FOR_LATEST_MOVIE + "limit " + number * 5 + ";", Movie.class));

        //下面生成number个不重复的随机数
        Set<Integer> randomNumbers = new HashSet<>(number);
        Random random = new Random();
        while (randomNumbers.size() < number) {
            randomNumbers.add(random.nextInt(number * 5));
        }
        //
        ArrayList<Movie> result = new ArrayList<>(number);
        for (int i : randomNumbers) {
            result.add(rowResult.get(i));
        }
        return result;
    }

    private List<Movie> getFavoriteGenreMovies(UserPO user, int number) {
        ArrayList<GenreFactor> factors = new ArrayList<>(user.getGenre_factors());
        if (factors.size() == 0) {
            return Collections.emptyList();
        }

        Collections.sort(factors);
        MovieGenre genre = factors.get(0).getMovieGenre();

        return movieDataHelper.sqlQuery(
                SQL_HEAD_FOR_MOVIE + " WHERE Genre = '" + genre.toString() + "' limit " + number + ";"
                , Movie.class);
    }

    private List<Movie> getFavoriteActorMovies(UserPO user, int number) {
        ArrayList<ActorFactor> factors = new ArrayList<>(user.getActor_factors());
        if (factors.size() == 0) {
            return Collections.emptyList();
        }

        Collections.sort(factors);
        String actor = factors.get(0).getName();
        return movieDataHelper.sqlQuery(SQL_HEAD_FOR_MOVIE + " WHERE Actor = '" + actor + "' limit " + number + ";"
                , Movie.class);
    }

    private List<Movie> getFavoriteDirectorMovies(UserPO user, int number) {
        ArrayList<DirectorFactor> factors = new ArrayList<>(user.getDirector_factors());
        if (factors.size() == 0) {
            return Collections.emptyList();
        }

        Collections.sort(factors);
        String director = factors.get(0).getName();
        return movieDataHelper.sqlQuery(SQL_HEAD_FOR_MOVIE + " WHERE Director = '" + director + "' limit " + number + ";"
                , Movie.class);
    }
}

