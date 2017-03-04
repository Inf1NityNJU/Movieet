package blstub;

import blservice.MovieBLService;
import vo.MovieVO;
import vo.ReviewCountMonthVO;
import vo.ScoreDistributionVO;

/**
 * Created by vivian on 2017/3/4.
 */
public class MovieBLServiceStub implements MovieBLService{
    @Override
    public MovieVO findMovieById(String id) {
        return new MovieVO("B000I5XDV1", "test Movie1", 12, 5, 0.1f);
    }

    @Override
    public ScoreDistributionVO findScoreDistributionByMovieId(String movieId) {
        int[] reviewAmounts = {10, 20, 30, 40, 50};
        return new ScoreDistributionVO(5, reviewAmounts);
    }

    @Override
    public ReviewCountMonthVO findMonthCountByMovieId(String movieId) {
        String[] keys = {"1","2","3","4","5","6","7","8","9","10","11","12"};
        int[] reviewAmounts = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120};
        return new ReviewCountMonthVO(keys, reviewAmounts);
    }
}
