package moviereview.service.impl;

import moviereview.model.Movie;
import moviereview.bean.MovieFull;
import moviereview.model.Page;
import moviereview.repository.MovieRepository;
import moviereview.service.MovieService;
import moviereview.util.ShellUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kray on 2017/3/7.
 */
@Service
public class MovieServiceImpl implements MovieService {

    private String FilePath = "/Users/Kray/Desktop/PythonHelper/iteration3/";

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

        //如果要 page - 1：
        page--;

        ArrayList<Movie> tempMovies = new ArrayList<>();
        if (orderBy.toLowerCase().equals("score")) {
            if (sortType.toLowerCase().equals("asc")) {
                tempMovies.addAll(movieRepository.findMoviesByTitleScoreAsc("%" + keyword + "%", page * size, size));
            } else {
                tempMovies.addAll(movieRepository.findMoviesByTitleScoreDesc("%" + keyword + "%", page * size, size));
            }
        } else if (orderBy.toLowerCase().equals("date")) {
            if (sortType.toLowerCase().equals("asc")) {
                tempMovies.addAll(movieRepository.findMoviesByTitleDateAsc("%" + keyword + "%", page * size, size));
            } else {
                tempMovies.addAll(movieRepository.findMoviesByTitleDateDesc("%" + keyword + "%", page * size, size));
            }
        }
        return transformMovies(tempMovies, page, size, orderBy, sortType);
    }


    public Page<MovieFull> findMoviesByActor(String actor, String orderBy, String sortType, int size, int page) {
        ArrayList<Movie> tempMovies = (ArrayList<Movie>)
                movieRepository.findMovieByActor("%" + actor + "%", page * size, size);
        return transformMovies(tempMovies, page, size, orderBy, sortType);
    }


    public Page<MovieFull> findMoviesByGenre(String Genre, String orderBy, String sortType, int size, int page) {
        ArrayList<Movie> tempMovies = (ArrayList<Movie>)
                movieRepository.findMovieByGenre(Genre, page * size, size);
        return transformMovies(tempMovies, page, size, orderBy, sortType);
    }

    public Page<MovieFull> findMoviesByDirector(String Director, String orderBy, String sortType, int size, int page) {
        ArrayList<Movie> tempMovies = (ArrayList<Movie>)
                movieRepository.findMovieByDirector("%" + Director + "%", page * size, size);
        return transformMovies(tempMovies, page, size, orderBy, sortType);
    }

    public List<MovieFull> findLatestMovies(int limit) {
        ArrayList<Movie> tempMovies = (ArrayList<Movie>)
                movieRepository.findLatestMovies(0, limit, LocalDate.now().toString());
        ArrayList<MovieFull> movies = new ArrayList<>();
        for (Movie movie : tempMovies) {
            movies.add(new MovieFull(movie));
        }
        return movies;
    }

    private Page<MovieFull> transformMovies(ArrayList<Movie> tempMovies, int page, int size, String orderBy, String sortType) {
        ArrayList<MovieFull> movies = new ArrayList<>();
        for (Movie movie : tempMovies) {
            MovieFull movieFull = new MovieFull(movie);

            String jsonString = ShellUtil.getResultOfShellFromCommand("python3 " + FilePath + "MovieIMDBInfoGetter.py " + movie.getTitle() + " " + movie.getYear());
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                Map<String, Object> jsonMap = jsonObject.toMap();
//                movieFull.setPlot((String) jsonMap.get("Plot"));
//                movieFull.setPoster((String) jsonMap.get("Poster"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            movies.add(movieFull);
        }
        if (movies == null || movies.size() <= 0) {
            return new Page<MovieFull>();
        }
        return new Page<MovieFull>(
                page,
                size,
                orderBy,
                sortType,
                movies.size() + "",
                movies);
    }
}
