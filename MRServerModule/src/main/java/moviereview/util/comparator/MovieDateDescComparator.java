package moviereview.util.comparator;

import moviereview.model.Movie;
import moviereview.util.DateUtil;

import java.util.Comparator;

/**
 * Created by Kray on 2017/4/8.
 */
public class MovieDateDescComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie o1, Movie o2) {
        String date1 = o1.getReleaseDate();
        String date2 = o2.getReleaseDate();
        if(date1.equals(date2)){
            return 0;
        }
        if (date1.equals("-1")) {
            return 1;
        } else if (date2.equals("-1")) {
            return -1;
        } else {
            return DateUtil.transformDate(date1) - DateUtil.transformDate(date2) < 0 ? 1 : -1;
        }
    }
}
