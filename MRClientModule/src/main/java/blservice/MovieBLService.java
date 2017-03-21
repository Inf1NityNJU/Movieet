package blservice;

import util.SortType;
import vo.*;

import java.util.List;

/**
 * Created by Sorumi on 17/3/4.
 */
public interface MovieBLService {

    /**
     * 根据 id 查找电影
     *
     * @param movieId
     * @return
     */
    public MovieVO findMovieById(String movieId);


    /**
     * 根据电影 id 查找评价分布
     *
     * @param movieId
     * @return
     */
    public ScoreDistributionVO findScoreDistributionByMovieId(String movieId);

    /**
     * 根据电影 id 查找每年评论数量
     *
     * @param movieId
     * @return
     */
    public ReviewCountVO[] findYearCountByMovieId(String movieId, String startYear, String endYear);

    /**
     * 根据电影 id 查找每月评论数量
     *
     * @param movieId
     * @param startMonth eg. 2017-01
     * @param endMonth   eg. 2017-03
     * @return
     */
    public ReviewCountVO[] findMonthCountByMovieId(String movieId, String startMonth, String endMonth);


    /**
     * 根据电影 id 和起始时间和结束时间查找每日评论数量
     *
     * @param movieId
     * @param startDate eg. 2017-01-12
     * @param endDate   eg. 2017-02-03
     * @return
     */
    public ReviewCountVO[] findDayCountByMovieId(String movieId, String startDate, String endDate);

    /**
     * 根据电影 id 寻找关于该电影的最高分词
     *
     * @param movieId 电影 id
     * @return 如果电影id正确且存在，则返回分词列表VO<br>
     * 否则返回null
     */
    public WordVO findWordsByMovieId(String movieId);

///迭代二
    /**
     *根据通过搜索电影名称得到相关电影列表
     * @param movieName 电影名称
     * @return 如果电影名称存在，返回具有相同名称的movieVO列表
     * 否则返回null
     */
    public List<MovieVO> findMoviesByName(String movieName);

    /**
     * 根据通过搜索电影分类tag得到按照时间排序的相关电影列表
     * @param tag 电影分类tag
     * @param sortType 决定时间按由近到远还是由远到近排序
     * @return 如果属于该电影分类tag的电影存在，返回该分类按照时间排序的movieVO列表
     *  否则返回null
     */
    public List<MovieVO> findMoviesByTag(String tag, SortType sortType);

    /**
     * 根据电影Id得到电影详情，sortType表示电影评论详情的排序方法
     * @param Id 电影Id
     * @param sortType 电影评论详情的排序方法
     * @return 相应的MovieVO
     */
    public MovieVO findMovieDetail(String Id, SortType sortType);

    /**
     * 将所有电影分类，统计各分类里的电影数量
     * @return MovieGenreVO，包括分类的tags和相应的amounts
     */
    public MovieGenreVO findMovieGenre();

    /**
     * 电影评分和电影评论数的关系
     * @return ScoreAndReviewAmountVO，包括评分列表和评论数量
     */
    public ScoreAndReviewAmountVO findRelationBetweenScoreAndReviewAmount();

    /**
     * 按照月的粒度来展示综合电影评分变化
     * @param startMonth 起始月份
     * @param endMonth 结束月份
     * @return ScoreChangeVO，包括时间列表和对应的评分列表
     */
    public ScoreChangeVO findScoreChangeByMonth(String startMonth, String endMonth);

    /**
     * 按照日的粒度来展示综合电影评分变化
     * @param startDate 起始日期
     * @param endDate 结束日期
     * @return ScoreChangeVO，包括时间列表和对应的评分列表
     */
    public ScoreChangeVO findScoreChangeByDay(String startDate, String endDate);
}
