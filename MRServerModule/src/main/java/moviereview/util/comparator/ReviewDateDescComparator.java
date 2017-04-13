package moviereview.util.comparator;

import moviereview.model.Review;

import java.util.Comparator;

/**
 * Created by Kray on 2017/4/3.
 */
public class ReviewDateDescComparator implements Comparator<Review> {
    @Override
    public int compare(Review o1, Review o2) {
        if (o1.getTime() == o2.getTime()) {
            return 0;
        }
        return o1.getTime() - o2.getTime() < 0 ? 1 : -1;
    }
}
