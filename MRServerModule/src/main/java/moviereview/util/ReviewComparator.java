package moviereview.util;

import moviereview.model.Review;

import java.util.Comparator;
import java.util.StringJoiner;

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
//                return (Review r1, Review r2) -> {
//                    String[] h1 = r1.getHelpfulness().split("/");
//                    String[] h2 = r2.getHelpfulness().split("/");
//                    Integer.parseInt(h1[0]) / Integer.parseInt(h1[1]) - Integer.parseInt(h2[0]) / Integer.parseInt(h2[1]) > 0;
//                };
            case HELPFULNESS_DESC:
//                return (Review r1, Review r2) -> r1.getScore() - r2.getScore();
        }
        return null;
    }
}
