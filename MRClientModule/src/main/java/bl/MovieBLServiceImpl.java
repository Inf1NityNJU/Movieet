package bl;

import blservice.MovieBLService;
import vo.*;

/**
 * Created by vivian on 2017/3/4.
 */
public class MovieBLServiceImpl implements MovieBLService {
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
    public ReviewCountVO[] findYearCountByMovieId(String movieId) {
        return null;
//        return movie.findYearCountByMovieId(movieId);
    }

    @Override
    public ReviewCountVO[] findMonthCountByMovieId(String movieId, String startMonth, String endMonth) {
        return null;
//        return movie.findMonthCountByMovieId(movieId, startMonth, endMonth);
    }

    @Override
    public ReviewCountVO[] findDayCountByMovieId(String movieId, String startDate, String endDate) {
        return null;
//        return movie.findDayCountByMovieId(movieId, startDate, endDate);
    }
}
