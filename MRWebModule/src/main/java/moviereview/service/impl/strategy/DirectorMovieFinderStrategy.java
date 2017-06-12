package moviereview.service.impl.strategy;

import moviereview.model.Movie;
import moviereview.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Kray on 2017/6/9.
 */
@Service
public class DirectorMovieFinderStrategy implements MovieFinderStrategy {

    @Autowired
    MovieRepository movieRepository;

    public List<Movie> findMovieWithKeyword(Object keyword, String orderBy, String sortType, int size, int page) {
        String Director = (String) keyword;
        if (orderBy.toLowerCase().equals("score")) {
            if (sortType.toLowerCase().equals("asc")) {
                return movieRepository.findMovieByDirectorScoreAsc("%" + Director + "%", page * size, size);
            } else {
                return movieRepository.findMovieByDirectorScoreDesc("%" + Director + "%", page * size, size);
            }
        } else if (orderBy.toLowerCase().equals("date")) {
            if (sortType.toLowerCase().equals("asc")) {
                return movieRepository.findMovieByDirectorDateAsc("%" + Director + "%", page * size, size);
            } else {
                return movieRepository.findMovieByDirectorDateDesc("%" + Director + "%", page * size, size);
            }
        }
        return null;
    }
    
}
