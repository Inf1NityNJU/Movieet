package service;

import bl.Movie;
import org.junit.Test;
import vo.MovieVO;
import vo.ReviewCountVO;
import vo.ScoreDistributionVO;

import java.util.Arrays;

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
        MovieVO movieVOExpected1 = new MovieVO("B000I5XDV1", "test Movie 1", 3, 3, 2, "2011-05-25", "2015-04-17");
        MovieVO movieVOActual1 = movie.findMovieById("B000I5XDV1");
        assertEquals(movieVOExpected1, movieVOActual1);
        MovieVO movieVOExpected2 = new MovieVO("B000I5XDV2", "test Movie 2", 3, 3.33, 1.5556, "2015-08-10", "2016-03-29");
        MovieVO movieVOActual2 = movie.findMovieById("B000I5XDV2");
        assertEquals(movieVOExpected2, movieVOActual2);
//        MovieVO movieVOExpected = new MovieVO("B000I5XDV1", "test Movie1", 3, 4, 0);
//        MovieVO movieVOActual = movie.findMovieById("B000I5XDV1");
//        assertEquals(movieVOExpected, movieVOActual);
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
        String[] keys = {"2011", "2012", "2013", "2014", "2015"};
        Integer[] reviewAmounts = {3, 0, 0, 0, 1};
        Integer[] reviewAmounts1 = {1, 0, 0, 0, 0};
        Integer[] reviewAmounts2 = {0, 0, 0, 0, 0};
        Integer[] reviewAmounts3 = {0, 0, 0, 0, 0};
        Integer[] reviewAmounts4 = {2, 0, 0, 0, 1};
        Integer[] reviewAmounts5 = {0, 0, 0, 0, 0};
        ReviewCountVO reviewCountVO = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts));
        ReviewCountVO reviewCountVO1 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts1));
        ReviewCountVO reviewCountVO2 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts2));
        ReviewCountVO reviewCountVO3 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts3));
        ReviewCountVO reviewCountVO4 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts4));
        ReviewCountVO reviewCountVO5 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts5));
        ReviewCountVO[] reviewCountVOsExpected = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
        ReviewCountVO[] reviewCountVOsActual = movie.findYearCountByMovieId("B000I5XDV1");
        for (int i = 0; i < reviewCountVOsActual.length; i++) {
            System.out.println(reviewCountVOsExpected[i].toString());
            System.out.println(reviewCountVOsActual[i].toString());
            assertEquals(reviewCountVOsExpected[i], reviewCountVOsActual[i]);
        }
//        assertEquals(reviewCountVOsExpected, reviewCountVOsActual);
    }

    @Test
    public void testFindMonthCountByMovieId() {
        String[] keys = {"2011-05", "2011-06", "2011-07", "2011-08", "2011-09", "2011-10", "2011-11","2011-12", "2012-01"};
        Integer[] reviewAmounts = {2, 1, 0, 0, 0, 0, 0, 0, 1};
        Integer[] reviewAmounts1 = {1, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts2 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts3 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts4 = {1, 1, 0, 0, 0, 0, 0, 0, 1};
        Integer[] reviewAmounts5 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        ReviewCountVO reviewCountVO = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts));
        ReviewCountVO reviewCountVO1 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts1));
        ReviewCountVO reviewCountVO2 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts2));
        ReviewCountVO reviewCountVO3 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts3));
        ReviewCountVO reviewCountVO4 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts4));
        ReviewCountVO reviewCountVO5 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts5));
        ReviewCountVO[] reviewCountVOsExpected = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
        ReviewCountVO[] reviewCountVOsActual = movie.findMonthCountByMovieId("B000I5XDV1", "2011-05", "2012-03");
        for (int i = 0; i < reviewCountVOsActual.length; i++) {
            System.out.println(reviewCountVOsExpected[i].toString());
            System.out.println(reviewCountVOsActual[i].toString());
            assertEquals(reviewCountVOsExpected[i], reviewCountVOsActual[i]);
        }
    }

    @Test
    public void testFindDayCountByMovieId() {
        String[] keys = {"2011-05-25", "2011-05-26", "2011-05-27", "2011-05-28", "2011-05-29", "2011-05-30", "2011-05-31", "2011-06-01"};
        Integer[] reviewAmounts = {1, 1, 1, 2, 0, 0, 0, 1};
        Integer[] reviewAmounts1 = {0, 1, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts2 = {0, 0, 0, 0, 0, 0, 0, 1};
        Integer[] reviewAmounts3 = {0, 0, 0, 1, 0, 0, 0, 0};
        Integer[] reviewAmounts4 = {1, 0, 1, 1, 0, 0, 0, 0};
        Integer[] reviewAmounts5 = {0, 0, 0, 0, 0, 0, 0, 0};
        ReviewCountVO reviewCountVO = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts));
        ReviewCountVO reviewCountVO1 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts1));
        ReviewCountVO reviewCountVO2 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts2));
        ReviewCountVO reviewCountVO3 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts3));
        ReviewCountVO reviewCountVO4 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts4));
        ReviewCountVO reviewCountVO5 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts5));
        ReviewCountVO[] reviewCountVOsExpected = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
        ReviewCountVO[] reviewCountVOsActual = movie.findDayCountByMovieId("B000I5XDV1", "2011-05-25", "2016-03-29");
        for (int i = 0; i < reviewCountVOsActual.length; i++) {
            assertEquals(reviewCountVOsExpected[i], reviewCountVOsActual[i]);
        }
    }
}
