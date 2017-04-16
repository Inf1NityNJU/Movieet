package bl;

import blservice.MovieBLService;
import javafx.scene.image.Image;
import util.MovieGenre;
import util.MovieSortType;
import util.ReviewSortType;
import vo.*;

import java.util.EnumSet;

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

    @Override
    public ScoreDistributionVO findScoreDistributionByMovieIdFromAmazon(String movieId) {
        return movie.findScoreDistributionByMovieIdFromAmazon(movieId, "Amazon");
    }

    @Override
    public ScoreDistributionVO findScoreDistributionByMovieIdFromIMDB(String movieId) {
        return movie.findScoreDistributionByMovieIdFromIMDB(movieId, "Imdb");
    }

    @Override
    public ReviewCountVO[] findYearCountByMovieId(String movieId, String startYear, String endYear) {
        return movie.findYearCountByMovieId(movieId, startYear, endYear);
    }

    @Override
    public ReviewCountVO[] findMonthCountByMovieId(String movieId, String startMonth, String endMonth) {
        return movie.findMonthCountByMovieId(movieId, startMonth, endMonth);
    }

    @Override
    public ReviewCountVO[] findDayCountByMovieId(String movieId, String startDate, String endDate) {
        return movie.findDayCountByMovieId(movieId, startDate, endDate);
    }

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

}
