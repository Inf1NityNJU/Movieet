package moviereview.service.impl;

import moviereview.model.GenreInYear;
import moviereview.repository.GenreInYearRepository;
import moviereview.repository.GenreRepository;
import moviereview.repository.MovieRepository;
import moviereview.service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by SilverNarcissus on 2017/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class MovieServiceImplTest {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieService movieService;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    GenreInYearRepository genreInYearRepository;

    @Test
    public void findMoviesByKeyword() throws Exception {

    }

    @Test
    public void findMoviesByActor() throws Exception {

    }

    @Test
    public void findMoviesByGenre() throws Exception {

    }

    @Test
    public void findMoviesByDirector() throws Exception {

    }

    @Test
    public void findLatestMovies() throws Exception {

    }

    @Test
    public void findMovieByMovieID() throws Exception {
//        System.out.println("FR"+movieService.calculate().get(0));
//        System.out.println("CN"+movieService.calculate().get(1));
    }

    @Test
    public void getScorePyramid() throws Exception {
        System.out.println(movieService.getScorePyramid());
    }

    @Test
    public void findGenreInfo() throws Exception {
//        System.out.println(movieService.findGenreInfo("Short", 2010));
    }

    @Test
    public void genreCount() {
        movieService.genreCount();
    }

    @Test
    public void genreInYear() {
        List<Integer> integers = genreRepository.findAllGenreId();
        System.out.println(integers.size());
        int count1 = 0;
        for (int genreId : integers) {
            for (int i = 1970; i <= 2017; i++) {
                int allMovieSize = movieRepository.findMovieInYear(i);
                int rightSize = movieRepository.findMovieByGenreInYear(genreId, i);
                double count = rightSize * 1.0 / allMovieSize;
                Double score = movieRepository.findAverageScoreByGenreInYear(i, genreId);
                DecimalFormat df = new DecimalFormat("#.##");

                if (score != null) {
                    score = Double.parseDouble(df.format(score));
                }
                df = new DecimalFormat("#.####");
                count = Double.parseDouble(df.format(count));
                GenreInYear genreInYear = new GenreInYear(genreId, i, count, score);
                genreInYearRepository.save(genreInYear);
                System.err.println(count1);
                count1++;
            }
        }
    }
}