package bl;

import blservice.MovieBLService;
import javafx.scene.image.Image;
import util.MovieGenre;
import util.MovieSortType;
import util.ReviewSortType;
import vo.*;

import java.util.EnumSet;
import java.util.List;

/**
 * Created by vivian on 2017/3/4.
 */
class MovieBLServiceImpl implements MovieBLService {
    private Movie movie;

    public MovieBLServiceImpl(Movie movie) {
        this.movie = movie;
    }

    @Override
    public MovieVO findMovieById(String id) {
        return movie.findMovieById(id);
    }


//    @Override
//    public ReviewCountVO[] findYearCountByMovieId(String movieId, String startYear, String endYear) {
//        return movie.findYearCountByMovieId(movieId, startYear, endYear);
//    }
//
//    @Override
//    public ReviewCountVO[] findMonthCountByMovieId(String movieId, String startMonth, String endMonth) {
//        return movie.findMonthCountByMovieId(movieId, startMonth, endMonth);
//    }
//
//    @Override
//    public ReviewCountVO[] findDayCountByMovieId(String movieId, String startDate, String endDate) {
//        return movie.findDayCountByMovieId(movieId, startDate, endDate);
//    }

    @Override
    public WordVO findWordsByMovieId(String movieId) {
        return movie.findWordsByMovieId(movieId);
    }

    //迭代二

    @Override
    public PageVO findMoviesByKeywordInPage(String keyword, int page) {
        return movie.findMoviesByKeywordInPage(keyword, page);
    }

    @Override
    public PageVO findMoviesByTagInPage(EnumSet<MovieGenre> tag, MovieSortType movieSortType, int page) {
        return movie.findMoviesByTagInPage(tag, movieSortType, page);
    }

    @Override
    public ScoreDistributionVO findScoreDistributionByMovieIdFromAmazon(String movieId) {
        return movie.findScoreDistributionByMovieIdFromAmazon(movieId);
    }

    @Override
    public ScoreDistributionVO findScoreDistributionByMovieIdFromIMDB(String movieId) {
        return movie.findScoreDistributionByMovieIdFromIMDB(movieId);
    }

    @Override
    public MovieStatisticsVO findMovieStatisticsVOByMovieId(String movieId) {
        return movie.findMovieStatisticsVOByMovieId(movieId);
    }

    @Override
    public PageVO findReviewsByMovieIdInPageFromAmazon(String movieId, ReviewSortType reviewSortType, int page) {
        return movie.findReviewsByMovieIdInPageFromAmazon(movieId, reviewSortType, page);
    }

    @Override
    public PageVO findReviewsByMovieIdInPageFromIMDB(String movieId, ReviewSortType reviewSortType, int page) {
        return movie.findReviewsByMovieIdInPageFromIMDB(movieId, reviewSortType, page);
    }

    @Override
    public MovieGenreVO findMovieGenre() {
        return movie.findMovieGenre();
    }

    @Override
    public ScoreAndReviewAmountVO findRelationBetweenScoreAndReviewAmount(EnumSet<MovieGenre> tag) {
        return movie.findRelationBetweenScoreAndReviewAmount(tag);
    }

    @Override
    public ScoreDateVO findScoreDateByYear(String Id, String startYear, String endYear) {
        return movie.findScoreDateByYear(Id, startYear, endYear);
    }

    @Override
    public ScoreDateVO findScoreDateByMonth(String Id, String startMonth, String endMonth) {
        return movie.findScoreDateByMonth(Id, startMonth, endMonth);
    }

    @Override
    public ScoreDateVO findScoreDateByDay(String Id, String startDate, String endDate) {
        return movie.findScoreDateByDay(Id, startDate, endDate);
    }

    @Override
    public Image findPosterByMovieId(String Id, int width) {
        return movie.findPosterByMovieId(Id, width);
    }

    @Override
    public BoxPlotVO getBoxPlotVOFromAmazon(String Id) {
        return movie.getBoxPlotVOFromAmazon(Id);
    }

    @Override
    public BoxPlotVO getBoxPlotVOFromImdb(String Id) {
        return movie.getBoxPlotVOFromImdb(Id);
    }

    @Override
    public ReviewCountVO[] findYearCountByMovieIdFromImdb(String movieId, String startYear, String endYear) {
        return movie.findYearCountByMovieIdFromImdb(movieId, startYear, endYear);
    }

    @Override
    public ReviewCountVO[] findMonthCountByMovieIdFromImdb(String movieId, String startMonth, String endMonth) {
        return movie.findMonthCountByMovieIdFromImdb(movieId, startMonth, endMonth);
    }

    @Override
    public ReviewCountVO[] findDayCountByMovieIdFromImdb(String movieId, String startDate, String endDate) {
        return movie.findDayCountByMovieIdFromImdb(movieId, startDate, endDate);
    }

    @Override
    public ReviewCountVO[] findYearCountByMovieIdFromAmazon(String movieId, String startYear, String endYear) {
        return movie.findYearCountByMovieIdFromAmazon(movieId, startYear, endYear);
    }

    @Override
    public ReviewCountVO[] findMonthCountByMovieIdFromAmazon(String movieId, String startMonth, String endMonth) {
        return movie.findMonthCountByMovieIdFromAmazon(movieId, startMonth, endMonth);
    }

    @Override
    public ReviewCountVO[] findDayCountByMovieIdFromAmazon(String movieId, String startDate, String endDate) {
        return movie.findDayCountByMovieIdFromAmazon(movieId, startDate, endDate);
    }

    @Override
    public List<MovieVO> findSimilarMovies(MovieVO movieVO) {
        return movie.findSimilarMovies(movieVO);
    }

    @Override
    public double calCorCoefficientWithScoreAndReviewAmount(EnumSet<MovieGenre> tag) {
        return movie.calCorCoefficientWithScoreAndReviewAmount(tag);
    }

}
