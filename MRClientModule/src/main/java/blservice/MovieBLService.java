package blservice;

import javafx.scene.image.Image;
import util.MovieGenre;
import util.MovieSortType;
import util.ReviewSortType;
import vo.*;

import java.util.EnumSet;
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
     * 根据电影 id 查找评价分布（Amazon电影）
     *
     * @param movieId
     * @return
     */
    public ScoreDistributionVO findScoreDistributionByMovieIdFromAmazon(String movieId);

    /**
     * 根据电影 id 查找评价分布（IMDB电影）
     *
     * @param movieId
     * @return
     */
    public ScoreDistributionVO findScoreDistributionByMovieIdFromIMDB(String movieId);

//    /**
//     * 根据电影 id 查找每年评论数量
//     *
//     * @param movieId
//     * @return
//     */
//    public ReviewCountVO[] findYearCountByMovieId(String movieId, String startYear, String endYear);
//
//    /**
//     * 根据电影 id 查找每月评论数量
//     *
//     * @param movieId
//     * @param startMonth eg. 2017-01
//     * @param endMonth   eg. 2017-03
//     * @return
//     */
//    public ReviewCountVO[] findMonthCountByMovieId(String movieId, String startMonth, String endMonth);
//
//
//    /**
//     * 根据电影 id 和起始时间和结束时间查找每日评论数量
//     *
//     * @param movieId
//     * @param startDate eg. 2017-01-12
//     * @param endDate   eg. 2017-02-03
//     * @return
//     */
//    public ReviewCountVO[] findDayCountByMovieId(String movieId, String startDate, String endDate);

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
     * 根据通过搜索电影名称得到相关电影列表
     *
     * @param keyword 电影关键字
     * @return 如果电影名称存在，返回具有相同名称的movieVO列表
     * 否则返回null
     */
    public PageVO findMoviesByKeywordInPage(String keyword, int page);


    /**
     * 根据通过搜索电影分类tag得到按照时间排序的相关电影列表
     *
     * @param tag           电影分类tag
     * @param movieSortType 决定时间按由近到远还是由远到近排序
     * @return 如果属于该电影分类tag的电影存在，返回该分类按照时间排序的movieVO列表
     * 否则返回null
     */
    public PageVO findMoviesByTagInPage(EnumSet<MovieGenre> tag, MovieSortType movieSortType, int page);

    /**
     * 根据电影id获得电影的均分、评论数量、第一条评论日期和最后一条评论日期等数据
     *
     * @param movieId 电影id
     * @return 如果电影id正确且存在，则返回电影数据VO
     * 否则返回null
     */
    public MovieStatisticsVO findMovieStatisticsVOByMovieId(String movieId);

    /**
     * 根据电影Id得到评论详情（Amazon上的电影），sortType表示电影评论详情的排序方法
     *
     * @param movieId        电影Id
     * @param reviewSortType 电影评论详情的排序方法
     * @return 相应的ReivewVOs
     */
    public PageVO findReviewsByMovieIdInPageFromAmazon(String movieId, ReviewSortType reviewSortType, int page);

    /**
     * 根据电影Id得到评论详情（Imdb上的电影），sortType表示电影评论详情的排序方法
     *
     * @param movieId        电影Id
     * @param reviewSortType 电影评论详情的排序方法
     * @return 相应的ReivewVOs
     */
    public PageVO findReviewsByMovieIdInPageFromIMDB(String movieId, ReviewSortType reviewSortType, int page);

    /**
     * 将所有电影分类，统计各分类里的电影数量
     *
     * @return MovieGenreVO，包括分类的tags和相应的amounts
     */
    public MovieGenreVO findMovieGenre();

    /**
     * 电影评分和电影评论数的关系
     *
     * @param tag 可以根据不同的电影类型展示评分与评论数量的关系
     * @return ScoreAndReviewAmountVO，包括评分列表和评论数量
     */
    public ScoreAndReviewAmountVO findRelationBetweenScoreAndReviewAmount(EnumSet<MovieGenre> tag);

    /**
     * 按照年的粒度来展示综合电影评分变化
     *
     * @param Id        电影Id
     * @param startYear 起始年份
     * @param endYear   结束年份
     * @return ScoreDateVO，包括时间列表和对应的评分列表
     */
    public ScoreDateVO findScoreDateByYear(String Id, String startYear, String endYear);

    /**
     * 按照月的粒度来展示综合电影评分变化
     *
     * @param Id         电影Id
     * @param startMonth 起始月份
     * @param endMonth   结束月份
     * @return ScoreDateVO，包括时间列表和对应的评分列表
     */
    public ScoreDateVO findScoreDateByMonth(String Id, String startMonth, String endMonth);

    /**
     * 按照日的粒度来展示综合电影评分变化
     *
     * @param Id        电影Id
     * @param startDate 起始日期
     * @param endDate   结束日期
     * @return ScoreDateVO，包括时间列表和对应的评分列表
     */
    public ScoreDateVO findScoreDateByDay(String Id, String startDate, String endDate);

    /**
     * 根据电影id获得相应的海报
     *
     * @param Id    电影Id
     * @param width 海报大小
     * @return 海报图片
     */
    public Image findPosterByMovieId(String Id, int width);

    /**
     * 根据电影ID（Amazon）得到评分分布的盒状图数据
     *
     * @param Id 电影ID
     * @return 绘制盒状图所需数据
     */
    public BoxPlotVO getBoxPlotVOFromAmazon(String Id);

    /**
     * 根据电影ID（Imdb）得到评分分布的盒状图数据
     *
     * @param Id 电影ID
     * @return 绘制盒状图所需数据
     */
    public BoxPlotVO getBoxPlotVOFromImdb(String Id);

    /**
     * 根据电影 id 查找每年评论数量 (Imdb)
     *
     * @param movieId
     * @return
     */
    public ReviewCountVO[] findYearCountByMovieIdFromImdb(String movieId, String startYear, String endYear);

    /**
     * 根据电影 id 查找每月评论数量 (Imdb)
     *
     * @param movieId
     * @param startMonth eg. 2017-01
     * @param endMonth   eg. 2017-03
     * @return
     */
    public ReviewCountVO[] findMonthCountByMovieIdFromImdb(String movieId, String startMonth, String endMonth);


    /**
     * 根据电影 id 和起始时间和结束时间查找每日评论数量 (Imdb)
     *
     * @param movieId
     * @param startDate eg. 2017-01-12
     * @param endDate   eg. 2017-02-03
     * @return
     */
    public ReviewCountVO[] findDayCountByMovieIdFromImdb(String movieId, String startDate, String endDate);

    /**
     * 根据电影 id 查找每年评论数量 (Amazon)
     *
     * @param movieId
     * @return
     */
    public ReviewCountVO[] findYearCountByMovieIdFromAmazon(String movieId, String startYear, String endYear);

    /**
     * 根据电影 id 查找每月评论数量 (Amazon)
     *
     * @param movieId
     * @param startMonth eg. 2017-01
     * @param endMonth   eg. 2017-03
     * @return
     */
    public ReviewCountVO[] findMonthCountByMovieIdFromAmazon(String movieId, String startMonth, String endMonth);


    /**
     * 根据电影 id 和起始时间和结束时间查找每日评论数量 (Amazon)
     *
     * @param movieId
     * @param startDate eg. 2017-01-12
     * @param endDate   eg. 2017-02-03
     * @return
     */
    public ReviewCountVO[] findDayCountByMovieIdFromAmazon(String movieId, String startDate, String endDate);

    /**
     * 通过用户曾评价过的电影推荐该用户可能喜欢的电影（通过电影类型和评分来判断）
     *
     * @param movieVO 电影
     * @return 推荐电影的List
     */
    public List<MovieVO> findSimilarMovies(MovieVO movieVO);

    /**
     * 计算分数与评论数量的相关系数
     * @param tag 电影类型
     * @return 相关系数
     */
    public double calCorCoefficientWithScoreAndReviewAmount(EnumSet<MovieGenre> tag);
}
