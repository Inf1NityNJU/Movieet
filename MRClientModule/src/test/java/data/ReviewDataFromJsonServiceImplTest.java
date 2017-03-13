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
    ReviewDataFromFileServiceImpl fileService =new ReviewDataFromFileServiceImpl("123");
    @Test
    public void findReviewsByMovieId() throws Exception {
        List<ReviewPO> reviewPOs = fileService.findReviewsByMovieId("");
        //B00005JO1X
        System.out.println(reviewPOs.size());
        reviewPOs.forEach(System.out::println);
    }

    @Test
    public void findReviewsByUserId() throws Exception {
        List<ReviewPO> reviewPOs = fileService.findReviewsByUserId("A11YJS79DZD7D9");
        System.out.println(reviewPOs.size());
        reviewPOs.forEach(System.out::println);
    }

    @Test
    public void findMovieByMovieId() {
        MoviePO moviePO = jsonService.findMovieByMovieId("B000ZLFALS");
        System.out.println(moviePO.getId());
        System.out.println(moviePO.getName());
        assertEquals("B000ZLFALS", moviePO.getId());
    }

}