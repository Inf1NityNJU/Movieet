package dataservice;

import po.MoviePO;
import po.ReviewPO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by SilverNarcissus on 2017/3/3.
 */
public class ReviewDataServiceStub {
    ReviewPO reviewPO_1 = ReviewPO.getBuilder().setMovieId("B000I5XDV0").setUserId("A2582KMXLK2P06").setProfileName("B. E Jackson").setHelpfulness("0/0").setScore(4).setTime(1306627200).setSummary("very good show").setText("he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ").build();
    ReviewPO reviewPO_2 = new ReviewPO("B000I5XDV1", "A2582KMXLI2P06", "B. C Jackson", " 3/5", 4, 1306327200, "very good show", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    ReviewPO reviewPO_3 = new ReviewPO("B000I5XDV2", "A2582KCXLK2P06", "B. E Jackson", " 5/6", 4, 1334627200, "very good", "he main reason I'm giving Alice Cooper's Live at Montreux a fairly high rating is because I'm totally shocked that a guy approaching his 60's is not only able to maintain the correct set of notes without losing his touch or straining his voice in an embarrassing manner ");
    MoviePO moviePO_1 = MoviePO.getBuilder().setId("B000I5XDV1").setName("test Movie1").build();
    MoviePO moviePO_2 = MoviePO.getBuilder().setId("B000I5XDV2").setName("test Movie2").build();

    List<ReviewPO> reviewPOs = new ArrayList<ReviewPO>();
    List<MoviePO> moviePOs = new ArrayList<MoviePO>();

    public ReviewDataServiceStub() {
        reviewPOs.add(reviewPO_1);
        reviewPOs.add(reviewPO_2);
        reviewPOs.add(reviewPO_3);

        moviePOs.add(moviePO_1);
        moviePOs.add(moviePO_2);
    }

    public List<ReviewPO> findReviewsByMovieId(String movieId) {
        return reviewPOs;
    }

    public MoviePO findMovieByMovieId(String movieId) {
        return moviePO_1;
    }

    public List<ReviewPO> findReviewsByUserId(String userId) {
        return reviewPOs;
    }
}
