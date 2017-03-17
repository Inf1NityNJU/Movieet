package datastub;

import dataservice.ReviewDataService;
import po.MoviePO;
import po.ReviewPO;
import po.WordPO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by SilverNarcissus on 2017/3/3.
 */
public class ReviewDataServiceStub implements ReviewDataService {
    ReviewPO reviewPO_1 = ReviewPO.getBuilder().setMovieId("B000I5XDV0").setUserId("A2582KMXLK2P06").setProfileName("B. E Jackson").setHelpfulness("0/0").setScore(4).setTime(1306281600).setSummary("very good show").setText("he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ").build();
    ReviewPO reviewPO_2 = new ReviewPO("B000I5XDV1", "A2582KMXLI2P06", "B. C Jackson", " 3/5", 1, 1306368000, "very good show", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    ReviewPO reviewPO_3 = new ReviewPO("B000I5XDV2", "A2582KCXLK2P06", "B. E Jackson", " 5/6", 4, 1306454400, "very good", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    ReviewPO reviewPO_4 = new ReviewPO("B000I5XDV1", "A2582KMXLI2P06", "B. C Jackson", " 3/5", 4, 1306540800, "very good show", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    ReviewPO reviewPO_5 = new ReviewPO("B000I5XDV1", "A2582KMXLI2P06", "B. C Jackson", " 3/5", 3, 1306540800, "very good show", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    ReviewPO reviewPO_6 = new ReviewPO("B000I5XDV1", "A2582KMXLI2P06", "B. C Jackson", " 3/5", 2, 1306886400, "very good show", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    //    ReviewPO reviewPO_7 = new ReviewPO("B000I5XDV1", "A2582KMXLI2P06", "B. C Jackson", " 3/5", 5, 1459221654, "very good show", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    MoviePO moviePO_1 = MoviePO.getBuilder().setId("B000I5XDV1").setName("test Movie 1").build();
    MoviePO moviePO_2 = MoviePO.getBuilder().setId("B000I5XDV2").setName("test Movie 2").build();

    List<ReviewPO> reviewPOs1 = new ArrayList<ReviewPO>();
    List<ReviewPO> reviewPOs2 = new ArrayList<ReviewPO>();
    List<MoviePO> moviePOs = new ArrayList<MoviePO>();

    public ReviewDataServiceStub() {
        reviewPOs1.add(reviewPO_1);
        reviewPOs1.add(reviewPO_2);
        reviewPOs1.add(reviewPO_3);
        reviewPOs1.add(reviewPO_4);

        reviewPOs1.add(reviewPO_5);
        reviewPOs1.add(reviewPO_6);
//        reviewPOs2.add(reviewPO_7);

        moviePOs.add(moviePO_1);
        moviePOs.add(moviePO_2);
    }

    public List<ReviewPO> findReviewsByMovieId(String movieId) {
        if (movieId == "B000I5XDV1") {
            return reviewPOs1;
        } else {
            return reviewPOs2;
        }

    }

    public MoviePO findMovieByMovieId(String movieId) {
        if (movieId == "B000I5XDV1") {
            return moviePO_1;
        } else {
            return moviePO_2;
        }
    }

    @Override
    public WordPO findWordsByMovieId(String movieId) {
        return null;
    }

    @Override
    public WordPO findWordsByUserId(String userId) {
        return null;
    }

    public List<ReviewPO> findReviewsByUserId(String userId) {
        if (userId == "01"){
            return Collections.emptyList();
        }
        return reviewPOs1;
    }
}
