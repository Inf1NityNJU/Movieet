package moviereview.util.comparator;

import moviereview.model.Review;

import java.util.Comparator;

/**
 * Created by Kray on 2017/4/3.
 */
public class ReviewHelpfulnessAscComparator implements Comparator<Review> {
    @Override
    public int compare(Review r1, Review r2) {
        String[] h1 = r1.getHelpfulness().split("/");
        String[] h2 = r2.getHelpfulness().split("/");
        if (h1[1].equals("0") && h2[1].equals("0")) {
            return 0;
        }
        if (h1[1].equals("0") && !h2[1].equals("0")) {
            return -1;
        }
        if (!h1[1].equals("0") && h2[1].equals("0")) {
            return 1;
        }
        if (Integer.parseInt(h1[0]) * 1.0 / Integer.parseInt(h1[1]) - Integer.parseInt(h2[0]) * 1.0 / Integer.parseInt(h2[1]) == 0) {
            return 0;
        }
        return Integer.parseInt(h1[0]) * 1.0 / Integer.parseInt(h1[1]) - Integer.parseInt(h2[0]) * 1.0 / Integer.parseInt(h2[1]) > 0 ? 1 : -1;
    }
}
