package moviereview.dao.impl;

import moviereview.dao.MovieDao;
import moviereview.model.Movie;
import org.springframework.stereotype.Repository;

/**
 * Created by Kray on 2017/3/7.
 */

@Repository
public class MovieDaoImpl implements MovieDao {

    public Movie getMovieByID(String id){
        System.out.println("id " + id);
        return new Movie("0" , "name");
    }

}
