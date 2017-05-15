package moviereview.repository;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import moviereview.model.*;
import moviereview.util.MovieGenre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2017/5/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()

public class MovieRepositoryTest {
    @Autowired
    MovieRepository movieRepository;

    @Test
    public void findByKeyword() throws Exception {
        ArrayList<Movie> movies = (ArrayList<Movie>) movieRepository.findMoviesByTitleLike("%Furious%", 0, 0);
        for (Movie movie : movies) {
            System.out.println(movie.getId() + " , " + movie.getTitle() + " , " + movie.getYear() + " , " + movie.getKind());
            for (Director director : movie.getDirector()) {
                System.out.println(director.getIddirector());
            }
            for (Genre genre : movie.getGenre()) {
                System.out.println(genre.getIdgenre());
            }
            for (Actor actor : movie.getActor()) {
                System.out.println(actor.getIdactor());
            }
            for (ReleaseDate releaseDate : movie.getReleaseDate()) {
                System.out.println(releaseDate.getIddate());
            }
        }
    }

    @Test
    public void findMovieByGenre() throws Exception {
        Long time1 = System.nanoTime();
        //SELECT * FROM movie WHERE idmovie IN (SELECT idmovie FROM is_genre WHERE idgenre = "Action") LIMIT 2;
        List<Movie> movies = movieRepository.findMovieByGenre(MovieGenre.Action.getGenreName(), 0, 2);
        System.out.println("time:" + (System.nanoTime() - time1) / 1000 / 1000);
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

}