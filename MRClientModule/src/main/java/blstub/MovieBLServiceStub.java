package blstub;

import blservice.MovieBLService;
import vo.*;

/**
 * Created by vivian on 2017/3/4.
 */
public class MovieBLServiceStub implements MovieBLService {
    @Override
    public MovieVO findMovieById(String id) {
        return new MovieVO("B000I5XDV1", "test Movie1", 12, 5, 0.1f);
    }

    @Override
    public ScoreDistributionVO findScoreDistributionByMovieId(String movieId) {
        int[] reviewAmounts = {10, 20, 30, 40, 50};
        return new ScoreDistributionVO(150, reviewAmounts);
    }

    @Override
    public ReviewCountYearVO findYearCountByMovieId(String movieId) {
        String[] keys = {"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017"};
        int[] reviewAmounts = {10, 22, 30, 45, 50, 60, 70, 83};
        return new ReviewCountYearVO(keys, reviewAmounts);
    }

    @Override
    public ReviewCountMonthVO findMonthCountByMovieId(String movieId, String startMonth, String endMonth) {
        String[] keys = {"2017-01", "2017-02", "2017-03", "2017-04", "2017-05", "2017-06"};
        int[] reviewAmounts = {12, 34, 56, 23, 34, 45};
        return new ReviewCountMonthVO(keys, reviewAmounts);
    }


    @Override
    public ReviewCountDayVO findDayCountByMovieId(String movieId, String startDate, String endDate) {
        String[] keys = {"2017-01-01", "2017-01-02", "2017-01-03", "2017-01-04", "2017-01-05", "2017-01-06", "2017-01-07"};
        int[] reviewAmounts = {12, 34, 56, 23, 34, 45, 24};
        return new ReviewCountDayVO(keys, reviewAmounts);
    }

}
