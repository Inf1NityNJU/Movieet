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
    public ReviewCountYearVO findYearCountByMovieId(String movieId) {
        return null;
    }

    @Override
    public ReviewCountMonthVO findMonthCountByMovieId(String movieId, String startMonth, String endMonth) {
        return null;
    }

    @Override
    public ReviewCountDayVO findDayCountByMovieId(String movieId, String startDate, String endDate) {
        return null;
    }
}
