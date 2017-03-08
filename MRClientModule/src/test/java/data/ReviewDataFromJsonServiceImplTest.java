package data;

import org.junit.Test;
import po.ReviewPO;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by SilverNarcissus on 2017/3/8.
 */
public class ReviewDataFromJsonServiceImplTest {
    ReviewDataFromJsonServiceImpl jsonService = new ReviewDataFromJsonServiceImpl();

    @Test
    public void findReviewByMovieId() throws Exception {
        List<ReviewPO> reviewPOs = jsonService.findReviewByMovieId("B0001G6PZC");
        System.out.println(reviewPOs.size());
        for (ReviewPO reviewPO : reviewPOs) {
            System.out.println(reviewPO);
        }
    }

}