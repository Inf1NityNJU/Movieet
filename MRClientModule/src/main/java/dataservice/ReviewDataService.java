package dataservice;

import po.*;
import util.MovieGenre;
import util.MovieSortType;
import util.ReviewSortType;

import java.util.EnumSet;
import java.util.List;

/**
 * Created by SilverNarcissus on 2017/3/5.
 */
public interface ReviewDataService {
    /**
     * 通过电影ID寻找该电影的所有评论
     *
     * @param productId 电影ID
     * @return 所有评论集合的迭代器
     */
    public List<ReviewPO> findAllReviewsByMovieIdFromAmazon(String productId);

    /**
     * 通过电影ID寻找该电影的所有评论
     *
     * @param productId 电影ID
     * @return 所有评论集合的迭代器
     */
    public List<ReviewPO> findAllReviewsByMovieIdFromImdb(String productId);

    /**
     * 通过用户ID寻找该用户的所有评论
     *
     * @param userId 用户ID
     * @return 所有评论集合的迭代器
     */
    public PagePO<ReviewPO> findReviewsByUserId(String userId);

    /**
     * 通过用户ID寻找该用户的所有评论，用分页形式返回
     *
     * @param userId 用户ID
     * @return 所有评论集合的迭代器
     */
    public PagePO<ReviewPO> findReviewsByUserIdInPage(String userId, ReviewSortType sortType, int page);

    /**
     * 通过电影ID寻找指定的电影
     *
     * @param productId 电影ID
     * @return 指定的电影
     */
    public MoviePO findMovieByMovieId(String productId);

    /**
     * 根据电影 id 寻找关于该电影的最高分词
     *
     * @param movieId 电影 id
     * @return 如果电影id正确且存在，则返回分词列表PO<br>
     * 否则返回null
     */
    public WordPO findWordsByMovieId(String movieId);

    /**
     * 根据用户 id 寻找关于该用户的最高分词
     *
     * @param userId 用户 id
     * @return 如果用户id正确且存在，则返回分词列表VO<br>
     * 否则返回null
     */
    public WordPO findWordsByUserId(String userId);

    //迭代二

    /**
     * 根据通过搜索电影名称得到相关电影列表
     *
     * @param movieName 电影名称
     * @param page      当前页面
     * @return 如果电影名称存在，返回具有相同名称的movieVO列表
     * 否则返回null
     */
    public PagePO<MoviePO> findMoviesByKeywordInPage(String movieName, int page);

    /**
     * 通过电影ID寻找亚马逊上关于该电影的所有评论,以分页形式提供
     *
     * @param productId      电影ID
     * @param reviewSortType 评论排序方法
     * @param page           当前页面  @return 所有评论集合的迭代器
     */
    public PagePO<ReviewPO> findReviewsByMovieIdInPageFromAmazon(String productId, ReviewSortType reviewSortType, int page);

    /**
     * 通过电影ID寻找imdb上关于该电影的所有评论,以分页形式提供
     *
     * @param productId      电影ID
     * @param reviewSortType 评论排序方法
     * @param page           当前页面  @return 所有评论集合的迭代器
     */
    public PagePO<ReviewPO> findReviewsByMovieIdInPageFromImdb(String productId, ReviewSortType reviewSortType, int page);

    /**
     * 根据通过搜索电影分类tag得到按照时间排序的相关电影列表
     *
     * @param tag           电影分类tag
     * @param movieSortType 电影排序方法
     * @return 如果属于该电影分类tag的电影存在，返回该分类按照时间排序的movieVO列表<br>
     * 否则返回null
     * @see MovieSortType
     */
    public PagePO<MoviePO> findMoviesByTagInPage(EnumSet<MovieGenre> tag, MovieSortType movieSortType, int page);

    /**
     * 将所有电影分类，统计各分类里的电影数量
     *
     * @return MovieGenreVO，包括分类的tags和相应的amounts
     */
    public MovieGenrePO findMovieGenre();

    /**
     * 电影评分和电影评论数的关系
     *
     * @return ScoreAndReviewAmountVO，包括评分列表和评论数量
     */
    public ScoreAndReviewAmountPO findRelationBetweenScoreAndReviewAmount(EnumSet<MovieGenre> tag);

    /**
     * 检查网络通信是否正常
     *
     * @return 网络通信正常与否
     */
    public boolean checkNetWork();
}
