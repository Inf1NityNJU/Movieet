package moviereview.util.comparator;

import moviereview.model.Movie;

import java.util.Comparator;

/**
 * Created by Kray on 2017/4/8.
 */
public class MovieRatingAscComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        String strRating1 = o1.getRating();
        String strRating2 = o2.getRating();
        if (strRating1.equals(strRating2)) {
            return 0;
        }
        return Double.parseDouble(strRating1) - Double.parseDouble(strRating2) > 0 ? 1 : -1;
    }
}
