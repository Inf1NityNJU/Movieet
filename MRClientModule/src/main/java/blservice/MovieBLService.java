package blservice;

import vo.*;

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
    public ReviewCountVO[] findYearCountByMovieId(String movieId);

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
     * @return 分词列表VO
     */
    public WordVO findWordsByMovieId(String movieId);
}
