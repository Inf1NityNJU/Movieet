package moviereview.service;

import moviereview.model.Movie;
import moviereview.model.MovieGenre;
import moviereview.model.Page;
import moviereview.model.ScoreAndReviewAmount;
import moviereview.util.MovieSortType;
import moviereview.util.ReviewSortType;

import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 2017/3/7.
 */
public interface MovieService {

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
     * 根据通过搜索电影名称得到相关电影列表
     *
     * @param keyword  电影关键字
     * @param sortType 排序类型
     * @param asc      是否升序
     * @return 如果电影名称存在，返回具有相同名称的movieVO列表
     * 否则返回null
     */
    public Page<Movie> findMoviesByKeyword(String keyword, int page, String sortType, boolean asc);

    /**
     * 根据通过搜索电影分类tag得到按照时间排序的相关电影列表
     *
     * @param tags          电影分类tag
     * @param movieSortType 决定时间按由近到远还是由远到近排序
     * @return 如果属于该电影分类tag的电影存在，返回该分类按照时间排序的movieVO列表
     * 否则返回null
     */
    public Page<Movie> findMoviesByTags(String[] tags, int page, String movieSortType, boolean asc);

    /**
     * 根据 tags 得到电影评分和评论数量的关系
     *
     * @param tags
     * @return
     */
    public ScoreAndReviewAmount findScoreAndReviewAmountByTags(String[] tags);

    /**
     * 得到电影分类和电影数量的关系
     *
     * @return
     */
    public MovieGenre findMovieGenreCount();
}
