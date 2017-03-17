package data;

import org.junit.Test;
import po.MoviePO;
import po.ReviewPO;
import po.WordPO;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by SilverNarcissus on 2017/3/8.
 */
public class ReviewDataFromJsonServiceImplTest {
    ReviewDataFromJsonServiceImpl jsonService = new ReviewDataFromJsonServiceImpl();
    ReviewDataFromFileServiceImpl fileService = new ReviewDataFromFileServiceImpl("");

    @Test
    public void findReviewsByMovieId() throws Exception {
        List<ReviewPO> reviewPOs = fileService.findReviewsByMovieId("");
        //B00005JO1X
        System.out.println(reviewPOs.size());
        reviewPOs.forEach(System.out::println);
    }

    @Test
    public void findReviewsByMovieId2() throws Exception {
        List<ReviewPO> reviewPOs = jsonService.findReviewsByMovieId("");
        //B00005JO1X B004OBQDH0
        assertEquals(0, reviewPOs.size());
        reviewPOs.forEach(System.out::println);
    }

    @Test
    public void findReviewsByMovieId3() throws Exception {
        List<ReviewPO> reviewPOs = jsonService.findReviewsByMovieId("B000ZLFALS");
        //B00005JO1X
        assertEquals(591, reviewPOs.size());
        reviewPOs.forEach(System.out::println);
    }

    @Test
    public void findReviewsByMovieId4() throws Exception {
        List<ReviewPO> reviewPOs = jsonService.findReviewsByMovieId("B004OBQDH0");
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
    public void findReviewsByUserId2() throws Exception {
        List<ReviewPO> reviewPOs = jsonService.findReviewsByUserId("A11YJS79DZD7D9");
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

    @Test
    public void findMovieByMovieId2() {
        MoviePO moviePO = jsonService.findMovieByMovieId("B000ZLFALS11");
        assertEquals("-1", moviePO.getId());
    }

    @Test
    public void findWordByMovieId() {
        WordPO wordPO = jsonService.findWordsByMovieId("B000ZLFALS");
        assertEquals(10, wordPO.getTopWords().size());
    }

    @Test
    public void findWordByMovieI2() {
        WordPO wordPO = jsonService.findWordsByMovieId("2");
        assertEquals(null, wordPO);
    }

    @Test
    public void findWordByMovieI3() {
        WordPO wordPO = jsonService.findWordsByMovieId("");
        assertEquals(null,wordPO);
    }

    @Test
    public void findWordByUserId() {
        WordPO wordPO = jsonService.findWordsByUserId("A11YJS79DZD7D9");
        assertEquals(10, wordPO.getTopWords().size());
    }

    @Test
    public void findWordByUserI2() {
        WordPO wordPO = jsonService.findWordsByUserId("2");
        assertEquals(null, wordPO);
    }

    @Test
    public void findWordByUserI3() {
        WordPO wordPO = jsonService.findWordsByUserId("A11YJS79DZD7D9");
        assertEquals(null,wordPO);
    }
}