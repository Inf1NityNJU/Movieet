package moviereview.service.impl.strategy;

import moviereview.model.Movie;

import java.util.List;

/**
 * Created by Kray on 2017/6/9.
 */
public interface MovieFinderStrategy {

    public List<Movie> findMovieWithKeyword(Object keyword, String orderBy, String sortType, int size, int page);

}
