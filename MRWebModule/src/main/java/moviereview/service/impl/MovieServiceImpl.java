package moviereview.service.impl;

import moviereview.model.Movie;
import moviereview.bean.MovieFull;
import moviereview.model.Page;
import moviereview.repository.MovieRepository;
import moviereview.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2017/3/7.
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    /**
     * @param keyword  关键字
     * @param orderBy  按什么排序
     * @param sortType asc 还是 desc
     * @param size     每页大小
     * @param page     第几页
     * @return Movie 分页列表
     */
    public Page<MovieFull> findMoviesByKeyword(String keyword, String orderBy, String sortType, int size, int page) {
        ArrayList<Movie> tempMovies = (ArrayList<Movie>) movieRepository.findMoviesByTitleLike("%" + keyword + "%", page * size, (page + 1) * size);
        return transformMovies(tempMovies, page, size, orderBy, sortType);
    }


    public Page<MovieFull> findMoviesByActor(String actor, String orderBy, String sortType, int size, int page) {
        ArrayList<Movie> tempMovies = (ArrayList<Movie>) movieRepository.findMovieByActor("%" + actor + "%", page * size, (page + 1) * size);
        return transformMovies(tempMovies, page, size, orderBy, sortType);
    }


    public Page<MovieFull> findMoviesByGenre(String Genre, String orderBy, String sortType, int size, int page) {
        ArrayList<Movie> tempMovies = (ArrayList<Movie>) movieRepository.findMovieByGenre(Genre, page * size, (page + 1) * size);
        return transformMovies(tempMovies, page, size, orderBy, sortType);
    }

    public Page<MovieFull> findMoviesByDirector(String Director, String orderBy, String sortType, int size, int page) {
        ArrayList<Movie> tempMovies = (ArrayList<Movie>) movieRepository.findMovieByDirector("%" + Director + "%", page * size, (page + 1) * size);
        return transformMovies(tempMovies, page, size, orderBy, sortType);
    }

    public List<Movie> findLatestMovies(int limit) {
        ArrayList<Movie> movies = (ArrayList<Movie>) movieRepository.findLatestMovies(0, limit);
        return movies;
    }

    private Page<MovieFull> transformMovies(ArrayList<Movie> tempMovies, int page, int size, String orderBy, String sortType) {
        ArrayList<MovieFull> movies = new ArrayList<>();
        for (Movie movie : tempMovies) {
            movies.add(new MovieFull(movie, "", ""));
        }
        if (movies == null || movies.size() <= 0) {
            return new Page<MovieFull>();
        }

        if (page * size > movies.size()) {
            return new Page<MovieFull>();
        } else {
            return new Page<MovieFull>(
                    page,
                    size,
                    orderBy,
                    sortType,
                    movies.size() + "",
                    movies);
        }
    }
}
