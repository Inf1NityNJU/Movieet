package moviereview.service.impl;

import moviereview.dao.DataHelper;
import moviereview.model.*;
import moviereview.util.MovieGenre;
import moviereview.util.RecommendType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by SilverNarcissus on 2017/5/12.
 */
public class RecommendServiceImpl {
    private static final String SQL_FOR_LATEST_MOVIE = "SELECT * FROM MoviePO ORDER BY date";
    private static final String SQL_HEAD_FOR_MOVIE = "SELECT * FROM MoviePO";
    @Autowired
    private DataHelper<UserPO> userDataHelper;

    private DataHelper<MoviePO> movieDataHelper;

    public Set<MoviePO> everyDayRecommend(String userId) {
        UserPO user = userDataHelper.exactlyQuery("userId", userId, UserPO.class);

        Set<MoviePO> result = new HashSet<>(6);
        result.addAll(getFavoriteGenreMovies(user, 2));
        result.addAll(getFavoriteActorMovies(user, 2));
        result.addAll(getFavoriteDirectorMovies(user, 2));

        //如果电影不够则加入最新的电影
        while (result.size() < 6) {
            result.addAll(getNewMovie(6 - result.size()));
        }

        return result;
    }

    public Set<MoviePO> finishSeeingRecommend(String userId, RecommendType type, String content) {
        switch (type) {

            case GENRE:
                return new HashSet<>(movieDataHelper.sqlQuery(
                        SQL_HEAD_FOR_MOVIE + " WHERE Genre = '" + content + "' limit " + 6 + ";"
                        , MoviePO.class));

            case ACTOR:
                return new HashSet<>(movieDataHelper.sqlQuery(
                        SQL_HEAD_FOR_MOVIE + " WHERE Actor = '" + content + "' limit " + 6 + ";"
                        , MoviePO.class));

            case DIRECTOR:
                return new HashSet<>(movieDataHelper.sqlQuery(
                        SQL_HEAD_FOR_MOVIE + " WHERE Director = '" + content + "' limit " + 6 + ";"
                        , MoviePO.class));

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
    private List<MoviePO> getNewMovie(int number) {
        ArrayList<MoviePO> rowResult = new ArrayList<>(
                movieDataHelper.sqlQuery(SQL_FOR_LATEST_MOVIE + "limit " + number * 5 + ";", MoviePO.class));

        //下面生成number个不重复的随机数
        Set<Integer> randomNumbers = new HashSet<>(number);
        Random random = new Random();
        while (randomNumbers.size() < number) {
            randomNumbers.add(random.nextInt(number * 5));
        }
        //
        ArrayList<MoviePO> result = new ArrayList<>(number);
        for (int i : randomNumbers) {
            result.add(rowResult.get(i));
        }
        return result;
    }

    private List<MoviePO> getFavoriteGenreMovies(UserPO user, int number) {
        ArrayList<GenreFactor> factors = new ArrayList<>(user.getGenre_factors());
        if (factors.size() == 0) {
            return Collections.emptyList();
        }

        Collections.sort(factors);
        MovieGenre genre = factors.get(0).getMovieGenre();

        return movieDataHelper.sqlQuery(
                SQL_HEAD_FOR_MOVIE + " WHERE Genre = '" + genre.toString() + "' limit " + number + ";"
                , MoviePO.class);
    }

    private List<MoviePO> getFavoriteActorMovies(UserPO user, int number) {
        ArrayList<ActorFactor> factors = new ArrayList<>(user.getActor_factors());
        if (factors.size() == 0) {
            return Collections.emptyList();
        }

        Collections.sort(factors);
        String actor = factors.get(0).getName();
        return movieDataHelper.sqlQuery(SQL_HEAD_FOR_MOVIE + " WHERE Actor = '" + actor + "' limit " + number + ";"
                , MoviePO.class);
    }

    private List<MoviePO> getFavoriteDirectorMovies(UserPO user, int number) {
        ArrayList<DirectorFactor> factors = new ArrayList<>(user.getDirector_factors());
        if (factors.size() == 0) {
            return Collections.emptyList();
        }

        Collections.sort(factors);
        String director = factors.get(0).getName();
        return movieDataHelper.sqlQuery(SQL_HEAD_FOR_MOVIE + " WHERE Director = '" + director + "' limit " + number + ";"
                , MoviePO.class);
    }
}
