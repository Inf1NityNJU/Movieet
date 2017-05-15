package moviereview.service;

import moviereview.model.Movie;
import moviereview.util.RecommendType;

import java.util.List;
import java.util.Set;

/**
 * Created by SilverNarcissus on 2017/5/15.
 */
public interface RecommendService {
    /**
     * 每日推荐
     *
     * @param userId 用户ID
     * @return 每日推荐的6部电影
     */
    public Set<Movie> everyDayRecommend(int userId);

    /**
     * 看完某部电影之后的推荐
     *
     * @param userId  观看用户ID
     * @param type    用户选择的喜好类型
     * @param content 喜好内容
     * @return 含有最多6部电影的电影集合
     */
    public List<Movie> finishSeeingRecommend(int userId, RecommendType type, String content);

}
