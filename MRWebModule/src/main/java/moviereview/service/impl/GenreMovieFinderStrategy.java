package moviereview.service.impl;

import moviereview.model.Movie;
import moviereview.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Kray on 2017/6/9.
 */
@Service
public class GenreMovieFinderStrategy implements MovieFinderStrategy {

    @Autowired
    MovieRepository movieRepository;

    public List<Movie> findMovieWithKeyword(Object keyword, String orderBy, String sortType, int size, int page) {
        List<String> genres = (List<String>) keyword;

        System.out.println(movieRepository);

        if (orderBy.toLowerCase().equals("score")) {
            if (sortType.toLowerCase().equals("asc")) {
                return movieRepository.findMovieByGenreScoreAsc(genres, page * size, size);
            } else {
                return movieRepository.findMovieByGenreScoreDesc(genres, page * size, size);
            }
        } else if (orderBy.toLowerCase().equals("date")) {
            if (sortType.toLowerCase().equals("asc")) {
                return movieRepository.findMovieByGenreDateAsc(genres, page * size, size);
            } else {
                return movieRepository.findMovieByGenreDateDesc(genres, page * size, size);
            }
        }
        return null;
    }

}
