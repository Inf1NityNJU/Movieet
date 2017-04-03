import moviereview.dao.ReviewDao;
import moviereview.model.Review;
import moviereview.util.comparator.ReviewComparatorFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2017/4/3.
 */
public class SortTest {

    @Autowired
    private ReviewDao reviewDao;

    @Test
    public void sortReviewTest() {
        List<Review> reviews = new ArrayList<>();
        Review review1 = new Review("1", "1", "1", "1/1", 2, 5, "1", "1");
        Review review2 = new Review("2", "1", "1", "1/2", 2, 4, "1", "1");
        Review review3 = new Review("3", "1", "1", "1/3", 2, 3, "1", "1");
        Review review4 = new Review("4", "1", "1", "1/4", 2, 2, "1", "1");
        Review review5 = new Review("5", "1", "1", "1/5", 2, 1, "1", "1");
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        reviews.add(review5);
        reviews.sort(ReviewComparatorFactory.sortReviewsBySortType("DATE_ASC"));
        for(Review review : reviews){
            System.out.println(review.getMovieId());
        }
    }
}
