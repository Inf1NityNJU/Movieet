package moviereview.service.impl;

import moviereview.dao.MovieDao;
import moviereview.model.Movie;
import moviereview.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Kray on 2017/3/7.
 */
@Service
public class MovieServiceImpl implements MovieService {


    @Autowired
    private MovieDao movieDao;

    public Movie getMovieByID(String id){
        return movieDao.getMovieByID(id);
    }

}
