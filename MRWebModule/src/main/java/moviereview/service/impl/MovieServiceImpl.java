package moviereview.service.impl;

import moviereview.bean.GenreInfo;
import moviereview.bean.MovieFull;
import moviereview.bean.MovieMini;
import moviereview.model.Movie;
import moviereview.model.Page;
import moviereview.repository.GenreRepository;
import moviereview.repository.MovieRepository;
import moviereview.service.MovieService;
import moviereview.util.ShellUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private GenreRepository genreRepository;

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

        page++;

        Integer pageSize = movieRepository.findMovieCountByTitle("%" + keyword + "%");

        return transformMiniMovies(tempMovies, page, pageSize, orderBy, sortType);
    }


    public Page<MovieMini> findMoviesByActor(String actor, String orderBy, String sortType, int size, int page) {
        page--;
        ArrayList<Movie> tempMovies = new ArrayList<>();
        if (orderBy.toLowerCase().equals("score")) {
            if (sortType.toLowerCase().equals("asc")) {
                tempMovies.addAll(movieRepository.findMovieByActorScoreAsc("%" + actor + "%", page * size, size));
            } else {
                tempMovies.addAll(movieRepository.findMovieByActorScoreDesc("%" + actor + "%", page * size, size));
            }
        } else if (orderBy.toLowerCase().equals("date")) {
            if (sortType.toLowerCase().equals("asc")) {
                tempMovies.addAll(movieRepository.findMovieByActorDateAsc("%" + actor + "%", page * size, size));
            } else {
                tempMovies.addAll(movieRepository.findMovieByActorDateDesc("%" + actor + "%", page * size, size));
            }
        }
        page++;

        Integer pageSize = movieRepository.findMovieCountByActor("%" + actor + "%");

        return transformMiniMovies(tempMovies, page, pageSize, orderBy, sortType);
    }


    public Page<MovieMini> findMoviesByGenre(String Genre, String orderBy, String sortType, int size, int page) {
        //全部分类
        if (Genre.toLowerCase().equals("all")) {
            return findMoviesByKeyword("", orderBy, sortType, size, page);
        }

        page--;
        ArrayList<Movie> tempMovies = new ArrayList<>();
        if (orderBy.toLowerCase().equals("score")) {
            if (sortType.toLowerCase().equals("asc")) {
                tempMovies.addAll(movieRepository.findMovieByGenreScoreAsc(Genre, page * size, size));
            } else {
                tempMovies.addAll(movieRepository.findMovieByGenreScoreDesc(Genre, page * size, size));
            }
        } else if (orderBy.toLowerCase().equals("date")) {
            if (sortType.toLowerCase().equals("asc")) {
                tempMovies.addAll(movieRepository.findMovieByGenreDateAsc(Genre, page * size, size));
            } else {
                tempMovies.addAll(movieRepository.findMovieByGenreDateDesc(Genre, page * size, size));
            }
        }
        page++;

        Integer pageSize = movieRepository.findMovieCountByGenre(Genre);

        return transformMiniMovies(tempMovies, page, pageSize, orderBy, sortType);
    }

    public Page<MovieMini> findMoviesByDirector(String Director, String orderBy, String sortType, int size, int page) {
        page--;
        ArrayList<Movie> tempMovies = new ArrayList<>();
        if (orderBy.toLowerCase().equals("score")) {
            if (sortType.toLowerCase().equals("asc")) {
                tempMovies.addAll(movieRepository.findMovieByDirectorScoreAsc("%" + Director + "%", page * size, size));
            } else {
                tempMovies.addAll(movieRepository.findMovieByDirectorScoreDesc("%" + Director + "%", page * size, size));
            }
        } else if (orderBy.toLowerCase().equals("date")) {
            if (sortType.toLowerCase().equals("asc")) {
                tempMovies.addAll(movieRepository.findMovieByDirectorDateAsc("%" + Director + "%", page * size, size));
            } else {
                tempMovies.addAll(movieRepository.findMovieByDirectorDateDesc("%" + Director + "%", page * size, size));
            }
        }
        page++;

        Integer pageSize = movieRepository.findMovieCountByDirector("%" + Director + "%");

        return transformMiniMovies(tempMovies, page, pageSize, orderBy, sortType);
    }

    public List<MovieFull> findLatestMovies(int limit) {
        //ArrayList<Movie> tempMovies = (ArrayList<Movie>)
        //      movieRepository.findLatestMovies(0, limit, LocalDate.now().toString());
        ArrayList<MovieFull> movies = new ArrayList<>();
//        for (Movie movie : tempMovies) {
//            movies.add(new MovieFull(movie));
//        }
        return movies;
    }

//    @Override
//    public List<Actor> findActorsByIdMovie(String idmovie) {
//        return null;
//    }
//
//    @Override
//    public List<Director> findDirectorsByIdMovie(String idmovie) {
//        return null;
//    }
//
//    @Override
//    public List<Genre> findGenreByIdMovie(String idmovie) {
//        return null;
//    }

    /**
     * @param tempMovies Movies 列表
     * @param page       第几页
     * @param size       总条目数
     * @param orderBy    根据什么排序
     * @param sortType   升序降序
     * @return
     */
    private Page<MovieMini> transformMiniMovies(ArrayList<Movie> tempMovies, int page, int size, String orderBy, String sortType) {
        ArrayList<MovieMini> movies = new ArrayList<>();
        for (Movie movie : tempMovies) {
            MovieMini movieMini = new MovieMini(movie);

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
                movies.size(),
                orderBy,
                sortType,
                size,
                movies);
    }

    /**
     * @param tempMovies Movies 列表
     * @param page       第几页
     * @param size       总条目数
     * @param orderBy    根据什么排序
     * @param sortType   升序降序
     * @return
     */
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
                movies.size(),
                orderBy,
                sortType,
                size,
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

    /**
     * 得到类型信息
     *
     * @return
     */
    public List<GenreInfo> findGenreInfo() {
//        List<GenreInfo> genreInfos = new ArrayList<>();
//        for (Genre genre : genreRepository.findGenre()) {
//            GenreInfo genreInfo = new GenreInfo();
//            genreInfo.setGenre(genre.getIdgenre());
//
//            Map<String, Integer> yearAndCount = new HashMap<>();
//            Map<String, Double> yearAndSum = new HashMap<>();
//            for (Movie movie : genre.getMovies()) {
//                String year = movie.getYear();
//                if (yearAndCount.get(year) == null) {
//                    yearAndCount.put(year, 1);
//                } else {
//                    yearAndCount.replace(year, yearAndCount.get(year), yearAndCount.get(year) + 1);
//                }
//
//                if (yearAndSum.get(year) == null) {
//                    yearAndSum.put(year, movie.getRank());
//                } else {
//                    yearAndSum.replace(year, yearAndSum.get(year), yearAndSum.get(year) + movie.getRank());
//                }
//            }
//
//            List<Integer> years = new ArrayList<>();
//            List<Integer> counts = new ArrayList<>();
//            List<Double> scores = new ArrayList<>();
//
//            for (String year : yearAndCount.keySet()) {
//                years.add(Integer.parseInt(year));
//                counts.add(yearAndCount.get(year));
//                scores.add(yearAndSum.get(year) / yearAndCount.get(year));
//            }
//
//            genreInfo.setCount(counts);
//            genreInfo.setYear(years);
//            genreInfo.setScore(scores);
//
//
//            genreInfos.add(genreInfo);
//        }
//        return genreInfos;
        return null;
    }
}
