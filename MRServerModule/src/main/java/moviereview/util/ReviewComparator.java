package moviereview.util;

import moviereview.model.Review;

import java.util.Comparator;

/**
 * Created by Kray on 2017/3/24.
 */
public class ReviewComparator {

    public static Comparator<Review> sortReviewsBySortType(ReviewSortType sortType) {
        switch (sortType){
            case DATE_ASC:
            case DATE_DESC:
            case HELPFULNESS_ASC:
            case HELPFULNESS_DESC:
                //TODO
                return (Review r1, Review r2) -> r1.getScore() - r2.getScore();
        }
        return null;
    }
}
