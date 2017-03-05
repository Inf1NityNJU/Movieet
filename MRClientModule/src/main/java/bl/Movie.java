package bl;

import dataservice.ReviewDataServiceStub;
import po.MoviePO;
import po.ReviewPO;
import vo.MovieVO;

import java.util.Iterator;

/**
 * Created by vivian on 2017/3/4.
 */
public class Movie {
    ReviewDataServiceStub reviewDataServiceStub = new ReviewDataServiceStub();

    public MovieVO findMovieById(String id) {
        MoviePO moviePO = reviewDataServiceStub.findMovieByMovieId(id);
        Iterator<ReviewPO> reviewPOIterator = reviewDataServiceStub.findReviewsByMovieId(id);
//        MovieVO movieVO = new MovieVO()
        return null;
    }
}
