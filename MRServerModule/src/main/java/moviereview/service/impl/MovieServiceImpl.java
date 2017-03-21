package moviereview.service.impl;

import moviereview.dao.MovieDao;
import moviereview.model.Movie;
import moviereview.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Kray on 2017/3/7.
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    /**
     * 通过电影ID寻找指定的电影
     *
     * @param productId 电影ID
     * @return 指定的电影
     */
    public Movie findMovieByMovieId(String productId) {
        return movieDao.findMovieByMovieId(productId);
    }


    /**
     * 通过电影 ID 寻找该电影在 IMDB 上的 JSON 串
     *
     * @param productId 电影 ID
     * @return  JSON 形式的 String
     */
    public Map<String, Object> findIMDBJsonStringByMovieId(String productId){
        return movieDao.findIMDBJsonStringByMovieId(productId);
    }
}
