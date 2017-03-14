package blstub;

import blservice.MovieBLService;
import vo.*;

/**
 * Created by vivian on 2017/3/4.
 */
public class MovieBLServiceStub implements MovieBLService {
    @Override
    public MovieVO findMovieById(String id) {
        return new MovieVO("B000I5XDV1", "test Movie1", 12, 5, 0.1, "2016-01-01", "2017-03-09");
    }

    @Override
    public ScoreDistributionVO findScoreDistributionByMovieId(String movieId) {
        int[] reviewAmounts = {10, 20, 30, 40, 50};
        return new ScoreDistributionVO(150, reviewAmounts);
    }

    @Override
    public ReviewCountVO[] findYearCountByMovieId(String movieId) {
        String[] keys = {"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017"};
        int[] reviewAmounts = {10, 22, 30, 45, 50, 60, 70, 83};
        ReviewCountVO reviewCountVO = new ReviewCountVO(keys, reviewAmounts);
        int[] reviewAmounts1 = {2, 5, 6, 5, 10, 20, 20, 13};
        ReviewCountVO reviewCountVO1 = new ReviewCountVO(keys, reviewAmounts1);
        int[] reviewAmounts2 = {2, 5, 6, 8, 10, 10, 10, 10};
        ReviewCountVO reviewCountVO2 = new ReviewCountVO(keys, reviewAmounts2);
        int[] reviewAmounts3 = {2, 5, 6, 6, 10, 20, 20, 30};
        ReviewCountVO reviewCountVO3 = new ReviewCountVO(keys, reviewAmounts3);
        int[] reviewAmounts4 = {2, 5, 6, 1, 10, 5, 10, 20};
        ReviewCountVO reviewCountVO4 = new ReviewCountVO(keys, reviewAmounts4);
        int[] reviewAmounts5 = {2, 2, 6, 25, 10, 5, 10, 10};
        ReviewCountVO reviewCountVO5 = new ReviewCountVO(keys, reviewAmounts5);
        ReviewCountVO[] reviewCountVOs = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
        return reviewCountVOs;
    }

    @Override
    public ReviewCountVO[] findMonthCountByMovieId(String movieId, String startMonth, String endMonth) {
        String[] keys = {"2017-01", "2017-02", "2017-03", "2017-04", "2017-05", "2017-06"};
        int[] reviewAmounts = {10, 22, 30, 45, 50, 60};
        ReviewCountVO reviewCountVO = new ReviewCountVO(keys, reviewAmounts);
        int[] reviewAmounts1 = {2, 5, 6, 5, 10, 20};
        ReviewCountVO reviewCountVO1 = new ReviewCountVO(keys, reviewAmounts1);
        int[] reviewAmounts2 = {2, 5, 6, 8, 10, 10};
        ReviewCountVO reviewCountVO2 = new ReviewCountVO(keys, reviewAmounts2);
        int[] reviewAmounts3 = {2, 5, 6, 6, 10, 20};
        ReviewCountVO reviewCountVO3 = new ReviewCountVO(keys, reviewAmounts3);
        int[] reviewAmounts4 = {2, 5, 6, 1, 10, 5};
        ReviewCountVO reviewCountVO4 = new ReviewCountVO(keys, reviewAmounts4);
        int[] reviewAmounts5 = {2, 2, 6, 25, 10, 5};
        ReviewCountVO reviewCountVO5 = new ReviewCountVO(keys, reviewAmounts5);
        ReviewCountVO[] reviewCountVOs = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
        return reviewCountVOs;
    }


    @Override
    public ReviewCountVO[] findDayCountByMovieId(String movieId, String startDate, String endDate) {
        String[] keys = {"2017-01-01", "2017-01-02", "2017-01-03", "2017-01-04", "2017-01-05", "2017-01-06", "2017-01-07"};
        int[] reviewAmounts = {10, 22, 30, 45, 50, 60};
        ReviewCountVO reviewCountVO = new ReviewCountVO(keys, reviewAmounts);
        int[] reviewAmounts1 = {2, 5, 6, 5, 10, 20};
        ReviewCountVO reviewCountVO1 = new ReviewCountVO(keys, reviewAmounts1);
        int[] reviewAmounts2 = {2, 5, 6, 8, 10, 10};
        ReviewCountVO reviewCountVO2 = new ReviewCountVO(keys, reviewAmounts2);
        int[] reviewAmounts3 = {2, 5, 6, 6, 10, 20};
        ReviewCountVO reviewCountVO3 = new ReviewCountVO(keys, reviewAmounts3);
        int[] reviewAmounts4 = {2, 5, 6, 1, 10, 5};
        ReviewCountVO reviewCountVO4 = new ReviewCountVO(keys, reviewAmounts4);
        int[] reviewAmounts5 = {2, 2, 6, 25, 10, 5};
        ReviewCountVO reviewCountVO5 = new ReviewCountVO(keys, reviewAmounts5);
        ReviewCountVO[] reviewCountVOs = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
        return reviewCountVOs;
    }

}
