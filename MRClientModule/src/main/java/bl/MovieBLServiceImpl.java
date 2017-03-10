package bl;

import blservice.MovieBLService;
import vo.*;

/**
 * Created by vivian on 2017/3/4.
 */
public class MovieBLServiceImpl implements MovieBLService {
    @Override
    public MovieVO findMovieById(String id) {
        return null;
    }

    @Override
    public ScoreDistributionVO findScoreDistributionByMovieId(String movieId) {
        return null;
    }

    @Override
    public ReviewCountVO findYearCountByMovieId(String movieId) {
        return null;
    }

    @Override
    public ReviewCountVO findMonthCountByMovieId(String movieId, String startMonth, String endMonth) {
        return null;
    }

    @Override
    public ReviewCountVO findDayCountByMovieId(String movieId, String startDate, String endDate) {
        return null;
    }
}
