package movie;

import bl.Movie;
import dataservice.ReviewDataServiceStub;
import org.junit.Test;
import vo.MovieVO;
import static org.junit.Assert.assertEquals;

/**
 * Created by vivian on 2017/3/5.
 */
public class MovieTest {
    private Movie movie;

    public MovieTest() {
        this.movie = new Movie(new ReviewDataServiceStub());
    }

    @Test
    public void testFindMovieById(){
        MovieVO movieVOExpected = new MovieVO("B000I5XDV1","test Movie1",2,4,0);
        MovieVO movieVOActual = movie.findMovieById("B000I5XDV1");
        assertEquals(movieVOExpected, movieVOActual);
    }

//    @Test
//    public void testFindScoreDistributionByMovieId(){
//
//    }
}
