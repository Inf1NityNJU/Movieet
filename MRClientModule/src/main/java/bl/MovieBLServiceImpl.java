package bl;

import blservice.MovieBLService;
import vo.MovieVO;
import vo.ReviewCountMonthVO;
import vo.ScoreDistributionVO;

/**
 * Created by vivian on 2017/3/4.
 */
public class MovieBLServiceImpl implements MovieBLService{
    @Override
    public MovieVO findMovieById(String id) {
        return null;
    }

    @Override
    public ScoreDistributionVO findScoreDistributionByMovieId(String movieId) {
        return null;
    }

    @Override
    public ReviewCountMonthVO findMonthCountByMovieId(String movieId) {
        return null;
    }
}
