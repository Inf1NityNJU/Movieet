package moviereview.util.comparator;

import moviereview.model.Review;

import java.util.Comparator;

/**
 * Created by Kray on 2017/3/24.
 */
public class ReviewComparatorFactory {

    public static Comparator<Review> sortReviewsBySortType(String s) {
        switch (s) {
            case "HELPFULNESS_ASC":
                return new ReviewHelpfulnessAscComparator();
            case "HELPFULNESS_DESC":
                return new ReviewHelpfulnessDescComparator();
            case "DATE_ASC":
                return new ReviewDateAscComparator();
            case "DATE_DESC":
            default:
                return new ReviewDateDescComparator();
        }
    }
}
