package moviereview.util.comparator;

import moviereview.model.Movie;

import java.util.Comparator;

/**
 * Created by Kray on 2017/3/21.
 */
public class MovieComparatorFactory {

    public static Comparator<Movie> sortMoviesBySortType(String sortType) {
        switch (sortType) {
            case "DATE_ASC":
            case "DATE_DESC":
            case "SCORE_ASC":
            case "SCORE_DESC":
                //TODO
                return (Movie l1, Movie l2) -> l1.getId().length() - l2.getId().length() > 0 ? 1 : -1;
        }
        return null;
    }
}
