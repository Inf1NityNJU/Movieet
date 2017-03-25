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
//                return (Review r1, Review r2) -> r2.getTime() - r1.getTime();
            case DATE_DESC:
//                return (Review r1, Review r2) -> r1.getTime() - r2.getTime();
            case HELPFULNESS_ASC:
//                return (Review r1, Review r2) -> r1.getHelpfulness() - r2.getScore();
            case HELPFULNESS_DESC:
                return (Review r1, Review r2) -> r1.getScore() - r2.getScore();
        }
        return null;
    }
}
