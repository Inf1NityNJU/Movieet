package moviereview.dao;

import moviereview.model.Movie;

/**
 * Created by Kray on 2017/3/7.
 */
public interface MovieDao {

    public Movie getMovieByID(String id);

}
