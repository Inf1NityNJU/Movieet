package moviereview.util.comparator;

import moviereview.model.Movie;
import moviereview.util.DateUtil;

import java.util.Comparator;

/**
 * Created by Kray on 2017/4/8.
 */
public class MovieDateAscComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        if (o1.getReleaseDate().equals("-1")) {
            return -1;
        } else if (o2.getReleaseDate().equals("-1")) {
            return 1;
        } else {
            return DateUtil.transformDate(o1.getReleaseDate()) - DateUtil.transformDate(o2.getReleaseDate()) > 0 ? 1 : -1;
        }
    }
}