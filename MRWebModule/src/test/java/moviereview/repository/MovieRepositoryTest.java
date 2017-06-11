package moviereview.repository;

import moviereview.model.Movie;
import moviereview.util.MovieGenre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
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

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    DirectorRepository directorRepository;

    @Test
    public void findByKeyword() throws Exception {
        ArrayList<Movie> movies = (ArrayList<Movie>) movieRepository.findMoviesByTitleScoreDesc("%Furious%", 0, 5);
//        for (Movie movie : movies) {
//            System.out.println(movie.getId() + " , " + movie.getTitle() + " , " + movie.getYear() + " , " + movie.getKind());
//            for (Director director : movie.getDirector()) {
//                System.out.println(director.getIddirector());
//            }
//            for (GenreBean genre : movie.getGenre()) {
//                System.out.println(genre.getIdgenre());
//            }
//            for (Actor actor : movie.getActor()) {
//                System.out.println(actor.getIdactor());
//            }
//            for (ReleaseDate releaseDate : movie.getReleaseDate()) {
//                System.out.println(releaseDate.getIddate());
//            }
//        }
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

    @Test
    public void findCountryByMovie() {
        List<Integer> country = countryRepository.findCountryIdByIdMovie(399686);
        for (Integer i : country) {
            System.out.println(i);
        }
    }

    @Test
    public void findAllScore() {
        List<BigDecimal> doubles = movieRepository.findAllMovieDoubanScore();
        Double d = doubles.get(0).doubleValue();
        System.out.println(doubles.get(0));
    }


    @Test
    public void genreInyear(){
        int size = movieRepository.findMovieInYear(1970);
        System.out.println(size);
    }

    @Test
    public void voteMulScoreAvg() {
//        double m = movieRepository.voteMulScoreAvg();
//        System.out.println(m);
        double xi = movieRepository.voteMulScoreSumForDirector(7);
        System.out.println(xi);
//        List<Movie> movies = movieRepository.findMovieByDirector(directorRepository.findDirectorNameById(7));
//        double sum = 0;
//        System.out.println(movies.size());
//        for (Movie movie : movies) {
//            if (movie.getImdb_score()!=null&&movie.getImdb_score()!=0) {
//                sum = sum + movie.getImdb_score()*movie.getImdb_count();
//            }
//        }
//        System.out.println(sum);
    }
}