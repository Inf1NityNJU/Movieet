package moviereview.dao;

import moviereview.model.Movie;
import moviereview.model.MovieGenre;
import moviereview.model.Review;
import moviereview.model.ScoreAndReviewAmount;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 2017/3/7.
 */
public interface MovieDao {

    /**
     * 通过电影ID寻找指定的电影
     *
     * @param productId 电影ID
     * @return 指定的电影
     */
    public Movie findMovieByMovieId(String productId);

    /**
     * 通过电影 ID 寻找该电影在 IMDB 上的 JSON 串
     *
     * @param productId 电影 ID
     * @return JSON 形式的 String
     */
    public Map<String, Object> findIMDBJsonStringByMovieId(String productId);

    /**
     * 根据关键词找电影
     *
     * @param keyword 关键词
     * @return 电影 list
     */
    public List<Movie> findMoviesByKeyword(String keyword);

    /**
     * 根据分类找电影
     *
     * @param tag 电影分类
     * @return 电影 list
     */
    public List<Movie> findMoviesByTag(String tag);

    /**
     * 得到电影分类和电影数量的关系
     *
     * @return
     */
    public MovieGenre findMovieGenreCount();

    /**
     * 根据 tags 得到电影评分和评论数量的关系
     *
     * @param tags
     * @return
     */
    public ScoreAndReviewAmount findScoreAndReviewAmountByTags(String[] tags);
}
