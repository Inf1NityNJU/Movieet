package moviereview.util.comparator;

import moviereview.model.Movie;

import java.util.Comparator;

/**
 * Created by Kray on 2017/3/21.
 */
public class MovieComparatorFactory {

    public static Comparator<Movie> sortMoviesBySortType(String sortType) {
        switch (sortType) {
            case "SCORE_ASC":
                return new MovieScoreAscComparator();
            case "SCORE_DESC":
                return new MovieScoreDescComparator();
            case "DATE_ASC":
                return new MovieDateAscComparator();
            case "DATE_DESC":
            default:
                return new MovieDateDescComparator();
        }
    }
}
