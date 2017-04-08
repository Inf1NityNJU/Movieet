package moviereview.util.comparator;

import moviereview.model.Movie;

import java.util.Comparator;

/**
 * Created by Kray on 2017/4/8.
 */
public class MovieDateAscComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        return 1;
//        return o1.getReleaseDate() - o2.getReleaseDate() > 0 ? 1 : -1;
    }
}
