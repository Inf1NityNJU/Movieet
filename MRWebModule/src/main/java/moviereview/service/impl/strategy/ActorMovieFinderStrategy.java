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
public class ActorMovieFinderStrategy implements MovieFinderStrategy {

    @Autowired
    MovieRepository movieRepository;

    public List<Movie> findMovieWithKeyword(Object keyword, String orderBy, String sortType, int size, int page) {
        String actor = (String) keyword;
        System.out.println(orderBy.toLowerCase());
        System.out.println(sortType.toLowerCase());
        if (orderBy.toLowerCase().equals("score")) {
            if (sortType.toLowerCase().equals("asc")) {
                return movieRepository.findMovieByActorScoreAsc("%" + actor + "%", page * size, size);
            } else {
                return movieRepository.findMovieByActorScoreDesc("%" + actor + "%", page * size, size);
            }
        } else if (orderBy.toLowerCase().equals("date")) {
            if (sortType.toLowerCase().equals("asc")) {
                return movieRepository.findMovieByActorDateAsc("%" + actor + "%", page * size, size);
            } else {
                return movieRepository.findMovieByActorDateDesc("%" + actor + "%", page * size, size);
            }
        }
        return null;
    }

}
