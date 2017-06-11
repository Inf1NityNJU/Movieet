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
public class TitleMovieFinderStrategy implements MovieFinderStrategy {

    @Autowired
    MovieRepository movieRepository;

    public List<Movie> findMovieWithKeyword(Object keyword, String orderBy, String sortType, int size, int page) {
        String title = (String) keyword;
        if (orderBy.toLowerCase().equals("score")) {
            if (sortType.toLowerCase().equals("asc")) {
                return movieRepository.findMoviesByTitleScoreAsc("%" + title + "%", page * size, size);
            } else {
                return movieRepository.findMoviesByTitleScoreDesc("%" + title + "%", page * size, size);
            }
        } else if (orderBy.toLowerCase().equals("date")) {
            if (sortType.toLowerCase().equals("asc")) {
                return movieRepository.findMoviesByTitleDateAsc("%" + title + "%", page * size, size);
            } else {
                return movieRepository.findMoviesByTitleDateDesc("%" + title + "%", page * size, size);
            }
        }
        return null;
    }

}
