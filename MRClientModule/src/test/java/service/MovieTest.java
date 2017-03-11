package service;

import bl.Movie;
import org.junit.Test;
import vo.MovieVO;
import vo.ReviewCountVO;
import vo.ScoreDistributionVO;

import static org.junit.Assert.assertEquals;

/**
 * Created by vivian on 2017/3/5.
 */
public class MovieTest {
    private Movie movie;

    public MovieTest() {
        this.movie = new Movie();
    }

    @Test
    public void testFindMovieById() {
        MovieVO movieVOExpected = new MovieVO("B000I5XDV1", "test Movie 1", 7, 3.28, 1.63, "2011-05-25", "2016-03-29");
        MovieVO movieVOActual = movie.findMovieById("B000I5XDV1");
//        assertEquals(movieVOExpected, movieVOActual);
//        MovieVO movieVOExpected = new MovieVO("B000I5XDV1", "test Movie1", 3, 4, 0);
//        MovieVO movieVOActual = movie.findMovieById("B000I5XDV1");
//        assertEquals(movieVOExpected, movieVOActual);
        assertEquals(1.63, movieVOActual.getVariance(), 0.01);
        assertEquals(3.28, movieVOActual.getAverageScore(), 0.01);
    }


    @Test
    public void testFindScoreDistributionByMovieId() {
        int[] reviewAmounts = {1, 1, 1, 3, 1};
        ScoreDistributionVO scoreDistributionVOExpected = new ScoreDistributionVO(7, reviewAmounts);
        ScoreDistributionVO scoreDistributionVOActual = movie.findScoreDistributionByMovieId("B000I5XDV1");
        assertEquals(scoreDistributionVOExpected, scoreDistributionVOActual);
    }

    @Test
    public void testFindYearCountByMovieId() {
        String[] keys = {"2011","2012","2015","2016"};
        int[] reviewAmounts = {2,1,3,1};
        ReviewCountVO reviewCountVOExpected = new ReviewCountVO(keys, reviewAmounts);
        ReviewCountVO reviewCountVOActual = movie.findYearCountByMovieId("B000I5XDV1");
        assertEquals(reviewCountVOExpected, reviewCountVOActual);
    }

    @Test
    public void testFindMonthCountByMovieId() {
        String[] keys = {"2011-05","2012-04","2015-04","2015-08","2015-12","2016-03"};
        int[] reviewAmounts = {2,1,1,1,1,1};
        ReviewCountVO reviewCountVOExpected = new ReviewCountVO(keys, reviewAmounts);
        ReviewCountVO reviewCountVOActual = movie.findMonthCountByMovieId("B000I5XDV1", "2011-01", "2017-09");
        assertEquals(reviewCountVOExpected, reviewCountVOActual);
    }

    @Test
    public void testFindDayCountByMovieId() {
        String[] keys = {"2011-05-25","2011-05-29","2012-04-17","2015-04-17","2015-08-10","2015-12-04","2016-03-29"};
        int[] reviewAmounts = {1,1,1,1,1,1,1};
        ReviewCountVO reviewCountVOExpected = new ReviewCountVO(keys, reviewAmounts);
        ReviewCountVO reviewCountVOActual = movie.findDayCountByMovieId("B000I5XDV1", "2011-01-01", "2017-02-01");
        assertEquals(reviewCountVOExpected, reviewCountVOActual);
    }
}
