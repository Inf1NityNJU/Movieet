package movie;

import bl.Movie;
import datastub.ReviewDataServiceStub;
import org.junit.Test;
import vo.MovieVO;
import vo.ReviewCountYearVO;
import vo.ScoreDistributionVO;

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

//    @Test
//    public void testFindMonthCountByMovieId() {
//        String[] keys = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
//        int[] reviewAmounts = {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        ReviewCountMonthVO reviewCountMonthVOExpected = new ReviewCountMonthVO(keys, reviewAmounts);
//        ReviewCountMonthVO reviewCountMonthVOActual = movie.findMonthCountByMovieId("B000I5XDV1");
//        assertEquals(reviewCountMonthVOExpected, reviewCountMonthVOActual);
//    }
}
