package datastub;

import dataservice.ReviewDataService;
import po.*;
import util.MovieGenre;
import util.MovieSortType;
import util.ReviewSortType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by SilverNarcissus on 2017/3/3.
 */
public class ReviewDataServiceStub implements ReviewDataService {
    //2011-06-01
//    ReviewPO reviewPO_1 = ReviewPO.getBuilder().setMovieId("B000I5XDV0").setUserId("A2582KMXLK2P06").setProfileName("B. E Jackson").setHelpfulness("0/0").setScore(4).setTime(1306886400).setSummary("very good show").setText("he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ").build();
    //2011-5-21
    ReviewPO reviewPO_2 = new ReviewPO("B000I5XDV1", "A2582KMXLI2P06", "B. C Jackson", " 3/5", 1, 1306368000, "very good show", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    //2011-5-27
    ReviewPO reviewPO_3 = new ReviewPO("B000I5XDV2", "A2582KCXLK2P06", "B. E Jackson", " 5/6", 4, 1306454400, "very good", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    ReviewPO reviewPO_9 = new ReviewPO("B000I5XDV2", "A2582KCXLK2P06", "B. E Jackson", " 5/6", 3, 1306454400, "very good", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    //2011-5-28
    ReviewPO reviewPO_4 = new ReviewPO("B000I5XDV1", "A2582KMXLI2P06", "B. C Jackson", " 3/5", 4, 1306540800, "very good show", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    ReviewPO reviewPO_10 = new ReviewPO("B000I5XDV1", "A2582KMXLI2P06", "B. C Jackson", " 3/5", 2, 1306540800, "very good show", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    //2011-04-01
    ReviewPO reviewPO_5 = new ReviewPO("B000I5XDV1", "A2582KMXLI2P06", "B. C Jackson", " 3/5", 3, 1301616000, "very good show", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    //2011-04-20
    ReviewPO reviewPO_6 = new ReviewPO("B000I5XDV1", "A2582KMXLI2P06", "B. C Jackson", " 3/5", 2, 1303257600, "very good show", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    //2011-05-25
    ReviewPO reviewPO_7 = new ReviewPO("B000I5XDV1", "A2582KMXLI2P06", "B. C Jackson", " 3/5", 5, 1306281600, "very good show", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    ReviewPO reviewPO_8 = new ReviewPO("B000I5XDV1", "A2582KMXLI2P06", "B. C Jackson", " 3/5", 4, 1306281600, "very good show", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    MoviePO moviePO_1 = MoviePO.getBuilder().setId("B000I5XDV1").setName("test Movie 1").setDirector("aa").setActors("bb")
    .setCountry("China").setDuration(2).setGenre("all").setImageURL(null).setImdbId("123").setLanguage("2")
    .setPlot("1").setPlot("2").setReleaseDate("2011").setWriters("23").build();
//    MoviePO moviePO_2 = MoviePO.getBuilder().setId("B000I5XDV2").setName("test Movie 2").build();

    List<ReviewPO> reviewPOs1 = new ArrayList<ReviewPO>();
    List<ReviewPO> reviewPOs2 = new ArrayList<ReviewPO>();
    List<MoviePO> moviePOs = new ArrayList<MoviePO>();

    public ReviewDataServiceStub() {
//        reviewPOs1.add(reviewPO_1);
        reviewPOs1.add(reviewPO_2);
        reviewPOs1.add(reviewPO_3);
        reviewPOs1.add(reviewPO_4);
        reviewPOs1.add(reviewPO_5);
        reviewPOs1.add(reviewPO_6);

        reviewPOs2.add(reviewPO_3);
        reviewPOs2.add(reviewPO_4);
        reviewPOs2.add(reviewPO_7);
        reviewPOs2.add(reviewPO_8);
        reviewPOs2.add(reviewPO_9);
        reviewPOs2.add(reviewPO_10);

        moviePOs.add(moviePO_1);
//        moviePOs.add(moviePO_2);
    }

    public List<ReviewPO> findReviewsByMovieId(String movieId) {
        if (movieId == "B000I5XDV1") {
            return reviewPOs1;
        } else {
            return reviewPOs2;
        }
    }

    public MoviePO findMovieByMovieId(String movieId) {
//        if (movieId == "B000I5XDV1") {
            return moviePO_1;
//        } else {
//            return moviePO_2;
//        }
    }

    @Override
    public WordPO findWordsByMovieId(String movieId) {
        return null;
    }

    @Override
    public WordPO findWordsByUserId(String userId) {
        return null;
    }

    @Override
    public PagePO<MoviePO> findMoviesByKeywordInPage(String movieName, int page) {
        return null;
    }

    @Override
    public PagePO<ReviewPO> findReviewsByMovieIdInPage(String productId, ReviewSortType reviewSortType, int page) {
        return null;
    }

    @Override
    public PagePO<MoviePO> findMoviesByTagInPage(EnumSet<MovieGenre> tag, MovieSortType movieSortType, int page) {
        return null;
    }

    @Override
    public MovieGenrePO findMovieGenre() {
        return null;
    }

    @Override
    public ScoreAndReviewAmountPO findRelationBetweenScoreAndReviewAmount() {
        return null;
    }

    public List<ReviewPO> findReviewsByUserId(String userId) {
        if (userId.equals("01")){
            return Collections.emptyList();
        }
        return reviewPOs1;
    }
}
