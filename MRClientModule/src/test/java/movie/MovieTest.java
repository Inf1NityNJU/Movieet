package movie;

import bl.Movie;
import datastub.ReviewDataServiceStub;
import org.junit.Test;
import vo.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by vivian on 2017/3/5.
 */
public class MovieTest {
    private Movie movie;

    public MovieTest() {
        this.movie = new Movie(new ReviewDataServiceStub());
    }

    @Test
    public void testFindMovieById() {
        MovieVO movieVOExpected = new MovieVO("B000I5XDV1", "test Movie1", 3, 4, 0);
        MovieVO movieVOActual = movie.findMovieById("B000I5XDV1");
        assertEquals(movieVOExpected, movieVOActual);
//        assertEquals(1.55f, movieVOActual.getVariance(), 0.01);
//        assertEquals(2.33f, movieVOActual.getAverageScore(), 0.01);
    }


    @Test
    public void testFindScoreDistributionByMovieId() {
        int[] reviewAmounts = {0, 0, 0, 3, 0};
        ScoreDistributionVO scoreDistributionVOExpected = new ScoreDistributionVO(3, reviewAmounts);
        ScoreDistributionVO scoreDistributionVOActual = movie.findScoreDistributionByMovieId("B000I5XDV1");
        assertEquals(scoreDistributionVOExpected, scoreDistributionVOActual);
    }

    @Test
    public void testFindYearCountByMovieId() {
        String[] keys = {"1970"};
        int[] reviewAmounts = {3};
        ReviewCountYearVO reviewCountYearVOExpected = new ReviewCountYearVO(keys, reviewAmounts);
        ReviewCountYearVO reviewCountYearVOActual = movie.findYearCountByMovieId("B000I5XDV1");
        assertEquals(reviewCountYearVOExpected, reviewCountYearVOActual);
    }

    @Test
    public void testFindMonthCountByMovieId() {
        String[] keys = {"1970-01"};
        int[] reviewAmounts = {3};
        ReviewCountMonthVO reviewCountMonthVOExpected = new ReviewCountMonthVO(keys, reviewAmounts);
        ReviewCountMonthVO reviewCountMonthVOActual = movie.findMonthCountByMovieId("B000I5XDV1", "1970-01", "1970-02");
        assertEquals(reviewCountMonthVOExpected, reviewCountMonthVOActual);
    }

    @Test
    public void testFindDayCountByMovieId() {
        String[] keys = {"1970-01-16"};
        int[] reviewAmounts = {3};
        ReviewCountDayVO reviewCountDayVOExpected = new ReviewCountDayVO(keys, reviewAmounts);
        ReviewCountDayVO reviewCountDayVOActual = movie.findDayCountByMovieId("B000I5XDV1", "1970-01-13", "1970-02-01");
        assertEquals(reviewCountDayVOExpected, reviewCountDayVOActual);
    }
}
