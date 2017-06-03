package moviereview.service;

import moviereview.bean.MovieMini;
import moviereview.model.Movie;
import moviereview.util.RecommendType;
import moviereview.util.ResultMessage;

import java.util.List;

/**
 * Created by SilverNarcissus on 2017/5/15.
 */
public interface RecommendService {
    /**
     * 每日推荐
     *
     * @param userId 用户ID
     * @param limit 推荐数量
     * @return 每日推荐的limit部电影
     */
    public List<Movie> everyDayRecommend(int userId, int limit);

    /**
     * 看完某部电影之后的推荐
     *
     * @param userId  观看用户ID
     * @param type    用户选择的喜好类型
     * @param content 喜好内容
     * @param limit 推荐数量
     * @return 含有最多limit部电影的电影集合
     */
    public List<Movie> finishSeeingRecommend(int userId, RecommendType type, String content, int limit);

    /**
     * 得到最新的电影
     *
     * @param limit 需要得到的电影数量
     * @return 含所需数量的最新的电影的列表
     */
    public List<Movie> getNewMovie(int limit);

    /**
     * 当用户看完某类型的电影时，增加其因子
     *
     * @param userId     用户Id
     * @param movieGenreId 电影类型Id
     * @return ResultMessage.FAILED----用户不存在
     * ResultMessage.SUCCESS----添加成功
     */
    public ResultMessage addGenreFactorWhenViewed(int userId, int movieGenreId);

    /**
     * 当用户喜欢某类型的电影时，增加其因子
     *
     * @param userId     用户Id
     * @param movieGenreId 电影类型Id
     * @return ResultMessage.FAILED----用户不存在
     * ResultMessage.SUCCESS----添加成功
     */
    public ResultMessage addGenreFactorWhenFavored(int userId, int movieGenreId);

    /**
     * 当用户看完某演员的电影时，增加其因子
     *
     * @param userId 用户Id
     * @param actorId  演员Id
     * @return ResultMessage.FAILED----用户不存在
     * ResultMessage.SUCCESS----添加成功
     */
    public ResultMessage addActorFactorWhenViewed(int userId, int actorId);

    /**
     * 当用户喜欢某演员的电影时，增加其因子
     *
     * @param userId 用户Id
     * @param actorId  演员Id
     * @return ResultMessage.FAILED----用户不存在
     * ResultMessage.SUCCESS----添加成功
     */
    public ResultMessage addActorFactorWhenFavored(int userId, int actorId);

    /**
     * 当用户看过某导演的电影时，增加其因子
     *
     * @param userId   用户Id
     * @param directorId 导演Id
     * @return ResultMessage.FAILED----用户不存在
     * ResultMessage.SUCCESS----添加成功
     */
    public ResultMessage addDirectorFactorWhenViewed(int userId, int directorId);

    /**
     * 当用户喜欢某导演的电影时，增加其因子
     *
     * @param userId   用户Id
     * @param directorId 导演Id
     * @return ResultMessage.FAILED----用户不存在
     * ResultMessage.SUCCESS----添加成功
     */
    public ResultMessage addDirectorFactorWhenFavored(int userId, int directorId);

    /**
     * 寻找相似电影
     *
     * @param idmovie 电影id
     * @param limit 需要的个数
     * @return 相似电影列表
     */
    public List<MovieMini> findSimilarMovie(int idmovie, int limit);
}
