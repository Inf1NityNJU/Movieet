package bl;

import org.junit.Test;
import util.MovieGenre;
import util.MovieSortType;
import util.ReviewSortType;
import vo.*;

import java.util.ArrayList;
import java.util.EnumSet;

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
//        MovieVO movieVOExpected1 = new MovieVO("B000I5XDV1", "test Movie 1", 6, 3, 1.33, "2011-05-25", "2011-06-01");
//        MovieVO movieVOActual1 = movie.findMovieById("B000I5XDV1");
//        assertEquals(movieVOExpected1, movieVOActual1);
//        MovieVO movieVOExpected2 = new MovieVO("B000I5XDV2", "test Movie 2", 3, 3.33, 1.5556, "2015-08-10", "2016-03-29");
//        MovieVO movieVOActual2 = movie.findMovieById("B000I5XDV2");
//        assertEquals(movieVOExpected2, movieVOActual2);
//        MovieVO movieVOExpected = new MovieVO("B000I5XDV1", "test Movie1", 3, 4, 0);
//        MovieVO movieVOActual = movie.findMovieById("B000I5XDV1");
//        assertEquals(movieVOExpected, movieVOActual);
    }

    @Test
    public void testFindMovieById2() {
        MovieVO movieVOActual1 = movie.findMovieById("B004OBQDH0");
        System.out.println(movieVOActual1.id);
//        MovieVO movieVOExpected2 = new MovieVO("B000I5XDV2", "test Movie 2", 3, 3.33, 1.5556, "2015-08-10", "2016-03-29");
//        MovieVO movieVOActual2 = movie.findMovieById("B000I5XDV2");
//        assertEquals(movieVOExpected2, movieVOActual2);
//        MovieVO movieVOExpected = new MovieVO("B000I5XDV1", "test Movie1", 3, 4, 0);
//        MovieVO movieVOActual = movie.findMovieById("B000I5XDV1");
//        assertEquals(movieVOExpected, movieVOActual);
    }


    @Test
    public void testFindScoreDistributionByMovieId() {
//        int[] reviewAmounts = {1, 1, 1, 3, 0};
//        ScoreDistributionVO scoreDistributionVOExpected = new ScoreDistributionVO(6, reviewAmounts);
        ScoreDistributionVO scoreDistributionVOActual = movie.findScoreDistributionByMovieIdFromIMDB("B005K23S20");
        System.out.println(scoreDistributionVOActual);
//        assertEquals(scoreDistributionVOExpected, scoreDistributionVOActual);
    }

    @Test
    public void testFindYearCountByMovieId() {
//        String[] keys = {"2011", "2012"};
//        Integer[] reviewAmounts = {6, 0};
//        Integer[] reviewAmounts1 = {1, 0};
//        Integer[] reviewAmounts2 = {1, 0};
//        Integer[] reviewAmounts3 = {1, 0};
//        Integer[] reviewAmounts4 = {3, 0};
//        Integer[] reviewAmounts5 = {0, 0};
//        ReviewCountVO reviewCountVO = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts));
//        ReviewCountVO reviewCountVO1 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts1));
//        ReviewCountVO reviewCountVO2 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts2));
//        ReviewCountVO reviewCountVO3 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts3));
//        ReviewCountVO reviewCountVO4 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts4));
//        ReviewCountVO reviewCountVO5 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts5));
//        ReviewCountVO[] reviewCountVOsExpected = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
//        ReviewCountVO[] reviewCountVOsActual = movie.findYearCountByMovieId("B000I5XDV1", "2011", "2012");
//        for (int i = 0; i < reviewCountVOsActual.length; i++) {
//            System.out.println(reviewCountVOsExpected[i].toString());
//            System.out.println(reviewCountVOsActual[i].toString());
//            assertEquals(reviewCountVOsExpected[i], reviewCountVOsActual[i]);
//        }
        ReviewCountVO[] reviewCountVOs = movie.findYearCountByMovieId("B00005JO1X", "2015", "2017");
        System.out.println(reviewCountVOs.length);
        for (ReviewCountVO reviewCountVO: reviewCountVOs){
            System.out.println(reviewCountVO.getKeys());
            for (int i: reviewCountVO.getReviewAmounts()) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    @Test
    public void testFindMonthCountByMovieId() {
//        String[] keys = {"2011-05", "2011-06"};
//        Integer[] reviewAmounts = {5, 1};
//        Integer[] reviewAmounts1 = {1, 0};
//        Integer[] reviewAmounts2 = {0, 1};
//        Integer[] reviewAmounts3 = {1, 0};
//        Integer[] reviewAmounts4 = {3, 0};
//        Integer[] reviewAmounts5 = {0, 0};
//        ReviewCountVO reviewCountVO = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts));
//        ReviewCountVO reviewCountVO1 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts1));
//        ReviewCountVO reviewCountVO2 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts2));
//        ReviewCountVO reviewCountVO3 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts3));
//        ReviewCountVO reviewCountVO4 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts4));
//        ReviewCountVO reviewCountVO5 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts5));
//        ReviewCountVO[] reviewCountVOsExpected = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
//        ReviewCountVO[] reviewCountVOsActual = movie.findMonthCountByMovieId("B000I5XDV1", "2011-05", "2012-03");
//        for (int i = 0; i < reviewCountVOsActual.length; i++) {
//            System.out.println(reviewCountVOsExpected[i].toString());
//            System.out.println(reviewCountVOsActual[i].toString());
//            assertEquals(reviewCountVOsExpected[i], reviewCountVOsActual[i]);
//        }
        ReviewCountVO[] reviewCountVOs = movie.findMonthCountByMovieId("B00005JO1X", "2016-03", "2016-07");
        System.out.println(reviewCountVOs.length);
        for (ReviewCountVO reviewCountVO: reviewCountVOs){
            System.out.println(reviewCountVO.getKeys());
            for (int i: reviewCountVO.getReviewAmounts()) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

    }

    @Test
    public void testFindDayCountByMovieId() {
//        String[] keys = {"2011-05-25", "2011-05-26", "2011-05-27", "2011-05-28", "2011-05-29", "2011-05-30", "2011-05-31", "2011-06-01"};
//        Integer[] reviewAmounts = {1, 1, 1, 2, 0, 0, 0, 1};
//        Integer[] reviewAmounts1 = {0, 1, 0, 0, 0, 0, 0, 0};
//        Integer[] reviewAmounts2 = {0, 0, 0, 0, 0, 0, 0, 1};
//        Integer[] reviewAmounts3 = {0, 0, 0, 1, 0, 0, 0, 0};
//        Integer[] reviewAmounts4 = {1, 0, 1, 1, 0, 0, 0, 0};
//        Integer[] reviewAmounts5 = {0, 0, 0, 0, 0, 0, 0, 0};
//        ReviewCountVO reviewCountVO = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts));
//        ReviewCountVO reviewCountVO1 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts1));
//        ReviewCountVO reviewCountVO2 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts2));
//        ReviewCountVO reviewCountVO3 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts3));
//        ReviewCountVO reviewCountVO4 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts4));
//        ReviewCountVO reviewCountVO5 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts5));
//        ReviewCountVO[] reviewCountVOsExpected = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
//        ReviewCountVO[] reviewCountVOsActual = movie.findDayCountByMovieId("B000I5XDV1", "2011-05-25", "2016-03-29");
//        for (int i = 0; i < reviewCountVOsActual.length; i++) {
//            assertEquals(reviewCountVOsExpected[i], reviewCountVOsActual[i]);
//        }
        ReviewCountVO[] reviewCountVOs = movie.findDayCountByMovieId("B00005JO1X", "2016-03-03", "2016-03-09");
        System.out.println(reviewCountVOs.length);

        for (ReviewCountVO reviewCountVO: reviewCountVOs){
            System.out.println(reviewCountVO.getKeys());
            for (int i: reviewCountVO.getReviewAmounts()) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

    }

    @Test
    public void findWordByMovieId() {
        WordVO wordVO = movie.findWordsByMovieId("B000ZLFALS");
        assertEquals(10, wordVO.getTopWords().size());
    }

    @Test
    public void findWordByMovieId2() {
        WordVO wordVO = movie.findWordsByMovieId("B000ZLFAL");
        assertEquals(null, wordVO);
    }

    @Test
    public void findWordByMovieId3() {
        WordVO wordVO = movie.findWordsByMovieId("");
        assertEquals(null, wordVO);
    }


    //迭代二
    @Test
    public void testFindMoviesByKeywordInPage1() {
        PageVO<MovieVO> pageVO = movie.findMoviesByKeywordInPage("Snow+White", 1);
        System.out.println(pageVO.list.size());
        System.out.println(pageVO.totalPage);
        pageVO.list.forEach(System.out::println);
    }

    @Test
    public void testFindMoviesByKeywordInPage2() {
        PageVO<MovieVO> pageVO = movie.findMoviesByKeywordInPage("notexists", 1);
        assertEquals(0, pageVO.list.size());
    }

    @Test
    public void testFindMoviesByTagInPage1() {
        EnumSet<MovieGenre> tag = EnumSet.of(MovieGenre.Action);
        PageVO<MovieVO> pageVO = movie.findMoviesByTagInPage(tag, MovieSortType.DATE_ASC, 2);
        System.out.println(pageVO.list.size());
        System.out.println(pageVO.totalPage);
    }

    @Test
    public void testFindMovieStatisticsVOByMovieId() {
        MovieStatisticsVO movieStatisticsVO = movie.findMovieStatisticsVOByMovieId("B00005JO1X");
        System.out.println(movieStatisticsVO.amountOfReviewFromAmazon);
        System.out.println(movieStatisticsVO.amountOfReviewFromImdb);
        System.out.println(movieStatisticsVO.averageScore);
        System.out.println(movieStatisticsVO.firstReviewDate);
        System.out.println(movieStatisticsVO.lastReviewDate);
    }

    @Test
    public void testFindReviewsByMovieIdInPage() {
        PageVO<ReviewVO> pageVO = movie.findReviewsByMovieIdInPageFromAmazon("B00005JO1X", ReviewSortType.DATE_ASC, 2);
        System.out.println(pageVO.currentPage);
        System.out.println(pageVO.list.size());
        System.out.println(pageVO.totalPage);
    }

    @Test
    public void testFindMovieGenre() {
        MovieGenreVO movieGenreVO = movie.findMovieGenre();
        assertEquals(movieGenreVO.amounts.size(), movieGenreVO.tags.size());
    }

    @Test
    public void testFindRelationBetweenScoreAndReviewAmount() {
        EnumSet<MovieGenre> tag = EnumSet.of(MovieGenre.Action);
        ScoreAndReviewAmountVO scoreAndReviewAmountVO = movie.findRelationBetweenScoreAndReviewAmount(tag);
        System.out.println(scoreAndReviewAmountVO.names.size());
        System.out.println(scoreAndReviewAmountVO.reviewAmounts.size());
        System.out.println(scoreAndReviewAmountVO.scores.size());
    }

    @Test
    public void testfindScoreDateByYear() {
        ScoreDateVO scoreDateVOActual = movie.findScoreDateByYear("B005K23S20", "2011", "2012");
        System.out.println(scoreDateVOActual.dates.size());
        System.out.println(scoreDateVOActual.scores.size());
        System.out.println(scoreDateVOActual.dates.get(0));
        System.out.println(scoreDateVOActual.scores.get(0));
    }

    @Test
    public void testfindScoreDateByMonth() {
        ArrayList<String> dates = new ArrayList<>();
        dates.add("2011-03");
        dates.add("2011-04");
        dates.add("2011-05");
        dates.add("2011-06");
        ArrayList<Double> scores = new ArrayList<>();
        scores.add(null);
        scores.add(2.5);
        scores.add(3.0);
        scores.add(null);
        ScoreDateVO scoreDateVOExpected = new ScoreDateVO(dates, scores);
        ScoreDateVO scoreDateVOActual = movie.findScoreDateByMonth("B000I5XDV1", "2011-03", "2011-06");
        assertEquals(scoreDateVOExpected, scoreDateVOActual);
    }

    @Test
    public void testfindScoreDateByMonth1() {
        ScoreDateVO scoreDateVOActual = movie.findScoreDateByMonth("B00005JO1X", "2005-03", "2005-06");
        System.out.println(scoreDateVOActual.dates.get(0));
        System.out.println(scoreDateVOActual.scores.get(0));
    }

    @Test
    public void testfindScoreDateByDay() {
        ArrayList<String> dates = new ArrayList<>();
        dates.add("2011-05-25");
        dates.add("2011-05-26");
        dates.add("2011-05-27");
        dates.add("2011-05-28");
        ArrayList<Double> scores = new ArrayList<>();
        scores.add(4.5);
        scores.add(null);
        scores.add(3.5);
        scores.add(3.0);
        ScoreDateVO scoreDateVOExpected = new ScoreDateVO(dates, scores);
        ScoreDateVO scoreDateVOActual = movie.findScoreDateByDay("1", "2011-05-25", "2011-05-28");
        assertEquals(scoreDateVOExpected, scoreDateVOActual);
    }

    @Test
    public void testfindScoreDateByDay1() {
        ScoreDateVO scoreDateVOActual = movie.findScoreDateByDay("B00005JO1X", "2012-09-21", "2012-09-22");
        System.out.println(scoreDateVOActual.dates.get(1));
        System.out.println(scoreDateVOActual.scores.get(1));
    }

    @Test
    public void testScoreDistributionFromAmazon() {
        ScoreDistributionVO scoreDistributionVO = movie.findScoreDistributionByMovieIdFromAmazon("B00000F168");
        System.out.println(scoreDistributionVO.getReviewAmounts().size());
        for (int i : scoreDistributionVO.getReviewAmounts()){
            System.out.print(i+" ");
        }
        System.out.println();
        System.out.println(scoreDistributionVO.getTotalAmount());


    }

    @Test
    public void testScoreDistributionFromImdb() {
        ScoreDistributionVO scoreDistributionVO = movie.findScoreDistributionByMovieIdFromIMDB("B005K23S20");
        System.out.println(scoreDistributionVO.getReviewAmounts().size());
        for (int i : scoreDistributionVO.getReviewAmounts()){
            System.out.print(i+" ");
        }
        System.out.println();
        System.out.println(scoreDistributionVO.getTotalAmount());
    }
    @Test
    public void testGetList() {
//        List<ReviewPO> reviewPOList = movie.getReviewPOList("B00000F168", "All");
//        System.out.println(reviewPOList.size());
//        reviewPOList = movie.getReviewPOList("B00000F168", "Amazon");
//        System.out.println(reviewPOList.size());
//        reviewPOList = movie.getReviewPOList("B00000F168", "Imdb");
//        System.out.println(reviewPOList.size());
    }
    @Test
    public void testBoxPlot(){
        BoxPlotVO boxPlotVO = movie.getBoxPlotVOFromAmazon("B00000F168");
        System.out.println(boxPlotVO.maxScore);
        System.out.println(boxPlotVO.minScore);
        System.out.println(boxPlotVO.outerliers.size());
        System.out.println(boxPlotVO.quartiles.size());
        for (double d : boxPlotVO.quartiles){
            System.out.println(d);
        }
        for (double d: boxPlotVO.outerliers) {
            System.out.println(d);
        }
    }

}
