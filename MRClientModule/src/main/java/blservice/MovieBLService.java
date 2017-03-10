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
    public ReviewCountYearVO findYearCountByMovieId(String movieId);

    /**
     * 根据电影 id 查找每月评论数量
     *
     * @param movieId
     * @param startMonth eg. 2017-01
     * @param endMonth   eg. 2017-03
     * @return
     */
    public ReviewCountMonthVO findMonthCountByMovieId(String movieId, String startMonth, String endMonth);


    /**
     * 根据电影 id 和起始时间和结束时间查找每日评论数量
     *
     * @param movieId
     * @param startDate eg. 2017-01-12
     * @param endDate   eg. 2017-02-03
     * @return
     */
    public ReviewCountDayVO findDayCountByMovieId(String movieId, String startDate, String endDate);
}
