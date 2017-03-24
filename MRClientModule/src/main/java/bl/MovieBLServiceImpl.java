package bl;

import blservice.MovieBLService;
import util.MovieSortType;
import vo.*;

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
    public ScoreDistributionVO findScoreDistributionByMovieId(String movieId) {
        return movie.findScoreDistributionByMovieId(movieId);
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
        return null;
    }

    @Override
    public PageVO findMoviesByTagInPage(String tag, MovieSortType movieSortType, int page) {
        return null;
    }

    @Override
    public MovieStatisticsVO findMovieStatisticsVOByMovieId(String movieId) {
        return null;
    }

    @Override
    public PageVO findReviewsByMovieIdInPage(String movieId, MovieSortType movieSortType, int page) {
        return null;
    }

    @Override
    public MovieGenreVO findMovieGenre() {
        return null;
    }

    @Override
    public ScoreAndReviewAmountVO findRelationBetweenScoreAndReviewAmount() {
        return null;
    }

    @Override
    public ScoreDateVO findScoreDateByMonth(String Id, String startMonth, String endMonth) {
        return null;
    }

    @Override
    public ScoreDateVO findScoreDateByDay(String Id, String startDate, String endDate) {
        return null;
    }
}
