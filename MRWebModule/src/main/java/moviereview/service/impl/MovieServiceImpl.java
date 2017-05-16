package moviereview.service.impl;

import moviereview.bean.MovieMini;
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
    public Page<MovieMini> findMoviesByKeyword(String keyword, String orderBy, String sortType, int size, int page) {

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
        return transformMiniMovies(tempMovies, page, size, orderBy, sortType);
    }


    public Page<MovieMini> findMoviesByActor(String actor, String orderBy, String sortType, int size, int page) {
        ArrayList<Movie> tempMovies = (ArrayList<Movie>)
                movieRepository.findMovieByActor("%" + actor + "%", page * size, size);
        return transformMiniMovies(tempMovies, page, size, orderBy, sortType);
    }


    public Page<MovieMini> findMoviesByGenre(String Genre, String orderBy, String sortType, int size, int page) {
        ArrayList<Movie> tempMovies = (ArrayList<Movie>)
                movieRepository.findMovieByGenre(Genre, page * size, size);
        return transformMiniMovies(tempMovies, page, size, orderBy, sortType);
    }

    public Page<MovieMini> findMoviesByDirector(String Director, String orderBy, String sortType, int size, int page) {
        ArrayList<Movie> tempMovies = (ArrayList<Movie>)
                movieRepository.findMovieByDirector("%" + Director + "%", page * size, size);
        return transformMiniMovies(tempMovies, page, size, orderBy, sortType);
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

    private Page<MovieMini> transformMiniMovies(ArrayList<Movie> tempMovies, int page, int size, String orderBy, String sortType) {
        ArrayList<MovieMini> movies = new ArrayList<>();
        for (Movie movie : tempMovies) {
            MovieMini movieMini = new MovieMini(movie);

            String jsonString = ShellUtil.getResultOfShellFromCommand("python3 " + FilePath + "MovieIMDBInfoGetter.py " + movie.getTitle() + " " + movie.getYear());
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                Map<String, Object> jsonMap = jsonObject.toMap();
                movieMini.setPoster((String) jsonMap.get("Poster"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            movies.add(movieMini);
        }
        if (movies == null || movies.size() <= 0) {
            return new Page<MovieMini>();
        }
        return new Page<MovieMini>(
                page,
                size,
                orderBy,
                sortType,
                movies.size(),
                movies);
    }

    private Page<MovieFull> transformMovies(ArrayList<Movie> tempMovies, int page, int size, String orderBy, String sortType) {
        ArrayList<MovieFull> movies = new ArrayList<>();
        for (Movie movie : tempMovies) {
            MovieFull movieFull = new MovieFull(movie);

            StringBuilder sb = new StringBuilder();
            for (String s : movie.getTitle().split(" ")) {
                sb.append(s);
                sb.append("+");
            }
            String movieStr = sb.toString().substring(0, sb.toString().length() - 1);

            String jsonString = ShellUtil.getResultOfShellFromCommand("python3 " + FilePath + "MovieIMDBInfoGetter.py " + movieStr + " " + movie.getYear());
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
                movies.size(),
                movies);
    }

    /**
     * 根据 id 查找电影
     *
     * @param movieid
     * @return 完整电影信息
     */
    public MovieFull findMovieByMovieID(String movieid) {
        Movie movie = movieRepository.findMovieByID(movieid);
        MovieFull movieFull = new MovieFull(movie);

        StringBuilder sb = new StringBuilder();
        for (String s : movie.getTitle().split(" ")) {
            sb.append(s);
            sb.append("+");
        }
        String movieStr = sb.toString().substring(0, sb.toString().length() - 1);

        String jsonString = ShellUtil.getResultOfShellFromCommand("python3 " + FilePath + "MovieIMDBInfoGetter.py " + movieStr + " " + movie.getYear());
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Map<String, Object> jsonMap = jsonObject.toMap();
            movieFull.setPlot((String) jsonMap.get("Plot"));
            movieFull.setPoster((String) jsonMap.get("Poster"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieFull;
    }
}
