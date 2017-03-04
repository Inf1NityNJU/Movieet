package blservice;

import vo.MovieVO;
import vo.ReviewCountMonthVO;
import vo.ScoreDistributionVO;

/**
 * Created by Sorumi on 17/3/4.
 */
public interface MovieBLService {

    /**
     * 根据 d 查找电影
     * @param id
     * @return
     */
    public MovieVO findMovieById(String id);

    /**
     * 根据电影 id 查找评价分布
     * @param movieId
     * @return
     */
    public ScoreDistributionVO findScoreDistributionByMovieId(String movieId);

    /**
     * 根据电影 id 查找每月评论数量
     * @param movieId
     * @return
     */
    public ReviewCountMonthVO  findMonthCountByMovieId(String movieId);


}
