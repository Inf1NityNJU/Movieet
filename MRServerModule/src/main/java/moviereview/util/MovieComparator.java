package moviereview.util;

import moviereview.model.Movie;
import moviereview.util.SortType;

import java.util.Comparator;

/**
 * Created by Kray on 2017/3/21.
 */
public class MovieComparator {

    public static Comparator<Movie> sortMoviesBySortType(SortType sortType) {
        switch (sortType){
            case SCORESORT_HIGH_TO_LOW:
            case SCORESORT_LOW_TO_HIGH:
            case TIMESORT_LATEST_TO_OLD:
            case TIMESORT_OLD_TO_LATEST:
                //TODO
                return (Movie l1, Movie l2) -> l1.getId().length() - l2.getId().length();
        }
        return null;
    }
}
