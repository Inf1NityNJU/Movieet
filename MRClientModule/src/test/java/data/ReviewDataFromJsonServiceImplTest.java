package data;

import bl.Movie;
import org.junit.Test;
import po.MoviePO;
import po.ReviewPO;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by SilverNarcissus on 2017/3/8.
 */
public class ReviewDataFromJsonServiceImplTest {
    ReviewDataFromJsonServiceImpl jsonService = new ReviewDataFromJsonServiceImpl();

    @Test
    public void findReviewsByMovieId() throws Exception {
        List<ReviewPO> reviewPOs = jsonService.findReviewsByMovieId("B002LII6KA");
        System.out.println(reviewPOs.size());
        for (ReviewPO reviewPO : reviewPOs) {
            System.out.println(reviewPO);
        }
    }

    @Test
    public void findReviewsByUserId() throws Exception {
        List<ReviewPO> reviewPOs = jsonService.findReviewsByUserId("A11YJS79DZD7D9");
        System.out.println(reviewPOs.size());
        reviewPOs.forEach(System.out::println);
    }

    @Test
    public void findMovieByMovieId() {
        MoviePO moviePO = jsonService.findMovieByMovieId("B000ZLFALS");
        assertEquals("B000ZLFALS", moviePO.getId());
    }

}