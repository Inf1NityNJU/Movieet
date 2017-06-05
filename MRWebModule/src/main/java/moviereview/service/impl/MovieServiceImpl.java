package moviereview.service.impl;

import moviereview.bean.*;
import moviereview.model.Actor;
import moviereview.model.Director;
import moviereview.model.Movie;
import moviereview.model.Page;
import moviereview.repository.*;
import moviereview.service.MovieService;
import moviereview.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Kray on 2017/3/7.
 */
@Service
public class MovieServiceImpl implements MovieService {

//    private String FilePath = "/Users/Kray/Desktop/PythonHelper/iteration3/";
//    private String FilePath = "/Users/Sorumi/Developer/MovieReview/MRWebModule/src/main/resources/python-iteration3/";


    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    RecommendService recommendService;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private KeywordRepository keywordRepository;

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

        return transformMiniMoviesPage(tempMovies, page, pageSize, orderBy, sortType);
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

        return transformMiniMoviesPage(tempMovies, page, pageSize, orderBy, sortType);
    }


    public Page<MovieMini> findMoviesByGenre(String Genre, String orderBy, String sortType, int size, int page) {
        //全部分类
        if (Genre.toLowerCase().contains("0")) {
            return findMoviesByKeyword("", orderBy, sortType, size, page);
        }

        page--;

        ArrayList<String> genres = new ArrayList<>();
        String[] genre = Genre.split(",");
        if (genre.length > 1) {
            for (String g : genre) {
                String genreContent = genreRepository.findGenreById(Integer.parseInt(g));
                genres.add(genreContent);
            }
        } else if (genre.length == 1) {
            String genreContent = genreRepository.findGenreById(Integer.parseInt(genre[0]));
            genres.add(genreContent);
        }

        ArrayList<Movie> tempMovies = new ArrayList<>();
        if (orderBy.toLowerCase().equals("score")) {
            if (sortType.toLowerCase().equals("asc")) {
                tempMovies.addAll(movieRepository.findMovieByGenreScoreAsc(genres, page * size, size));
            } else {
                tempMovies.addAll(movieRepository.findMovieByGenreScoreDesc(genres, page * size, size));
            }
        } else if (orderBy.toLowerCase().equals("date")) {
            if (sortType.toLowerCase().equals("asc")) {
                tempMovies.addAll(movieRepository.findMovieByGenreDateAsc(genres, page * size, size));
            } else {
                tempMovies.addAll(movieRepository.findMovieByGenreDateDesc(genres, page * size, size));
            }
        }
        page++;

        Integer pageSize = movieRepository.findMovieCountByGenre(genres);

        return transformMiniMoviesPage(tempMovies, page, pageSize, orderBy, sortType);
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

        return transformMiniMoviesPage(tempMovies, page, pageSize, orderBy, sortType);
    }

    @Override
    public List<MovieMini> findMoviesByDirector(String director) {
        List<Movie> movies = movieRepository.findMovieByDirector(director);
        return this.transformMiniMovies(movies);
    }

    @Override
    public List<MovieMini> findMoviesByActor(String actor) {
        List<Movie> movies = movieRepository.findMovieByActor(actor);
        return this.transformMiniMovies(movies);
    }

    public List<MovieMini> findLatestMovies(int limit) {
        return transformMiniMovies(recommendService.getNewMovie(limit));
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
//    public List<GenreBean> findGenreIdByIdMovie(String idmovie) {
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
    private Page<MovieMini> transformMiniMoviesPage(ArrayList<Movie> tempMovies, int page, int size, String orderBy, String sortType) {
        List<MovieMini> movies = (ArrayList<MovieMini>) transformMiniMovies(tempMovies);
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

    private List<MovieMini> transformMiniMovies(List<Movie> tempMovies) {
        List<MovieMini> movies = new ArrayList<>();
        for (Movie movie : tempMovies) {
            List<Integer> genres = movieRepository.findMovieGenreByMovieId(movie.getId());
            MovieMini movieMini = new MovieMini(movie, this.genreIdToGenreBean(genres));

//            String movieStr = URLStringConverter.convertToURLString(movie.getTitle());

//            String jsonString = ShellUtil.getResultOfShellFromCommand("python3 " + DataConst.FilePath + "MovieIMDBInfoGetter.py " + movieStr + " " + movie.getYear());
//            try {
//                JSONObject jsonObject = new JSONObject(jsonString);
//                Map<String, Object> jsonMap = jsonObject.toMap();
//                movieMini.setPhoto((String) jsonMap.get("Poster"));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            movies.add(movieMini);
        }
        return movies;
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
            MovieFull movieFull = new MovieFull(movie, this.genreIdToGenreBean(genreRepository.findGenreIdByIdMovie(movie.getId())),
                    this.idListToValueString(countryRepository.findCountryIdByIdMovie(movie.getId())),
                    this.idListToPeopleMiniList(directorRepository.findDirectorIdByMovieId(movie.getId()), "d"),
                    this.idListToPeopleMiniList(actorRepository.findActorIdByMovieId(movie.getId()), "a"),
                    this.keywordIdToKeywordBean(keywordRepository.findKeywordIdByMovieId(movie.getId())));

//            String movieStr = URLStringConverter.convertToURLString(movie.getTitle());

//            String jsonString = ShellUtil.getResultOfShellFromCommand("python3 " + DataConst.FilePath + "MovieIMDBInfoGetter.py " + movieStr + " " + movie.getYear());
//            try {
//                JSONObject jsonObject = new JSONObject(jsonString);
//                Map<String, Object> jsonMap = jsonObject.toMap();
//                movieFull.setPlot((String) jsonMap.get("Plot"));
//                movieFull.setPhoto((String) jsonMap.get("Poster"));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

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
    public MovieFull findMovieFullByMovieID(int movieid) {

//        movieid = URLStringConverter.convertToNormalString(movieid);

        Movie movie = movieRepository.findMovieById(movieid);


//        String movieStr = URLStringConverter.convertToURLString(movie.getTmdbtitle());

//        String jsonString = ShellUtil.getResultOfShellFromCommand("python3 " + DataConst.FilePath + "MovieIMDBInfoGetter.py " + movieStr + " " + movie.get);
//        try {
//            JSONObject jsonObject = new JSONObject(jsonString);
//            Map<String, Object> jsonMap = jsonObject.toMap();
//            movieFull.setPlot((String) jsonMap.get("Plot"));
//            movieFull.setPhoto((String) jsonMap.get("Poster"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return new MovieFull(movie, this.genreIdToGenreBean(genreRepository.findGenreIdByIdMovie(movieid)),
                this.idListToValueString(countryRepository.findCountryIdByIdMovie(movieid)),
                this.idListToPeopleMiniList(directorRepository.findDirectorIdByMovieId(movieid), "d"),
                this.idListToPeopleMiniList(actorRepository.findActorIdByMovieId(movieid), "a"),
                this.keywordIdToKeywordBean(keywordRepository.findKeywordIdByMovieId(movieid)));
    }

    @Override
    public MovieMini findMovieMiniByMovieID(int movieid) {
        Movie movie = movieRepository.findMovieById(movieid);
        List<Integer> genre = movieRepository.findMovieGenreByMovieId(movieid);
        return new MovieMini(movie, this.genreIdToGenreBean(genre));
    }

    @Override
    public Page<MovieMini> getMovieRank(int size) {
        List<Movie> movies = movieRepository.findMovieForRank(8.5);
        //进入imdb top250需要的最小票数
        int m = 1250;
        //目前所有电影的平均分
        double c = 6.9;
        //普通方法计算出的电影平均分
        double r = 0;
        //电影的投票人数（只有经常投票者才会被计算在内）
        double v = 0;
        double score = 0;
        Map<Movie, Double> movieAndScore = new HashMap<>();

        for (Movie movie : movies) {
            r = movie.getImdb_score();
            v = movie.getImdb_count();
            score = (v / (v + m)) * r + (m / (v + m)) * c;
            movieAndScore.put(movie, score);
        }

        //排序
        List<Map.Entry<Movie, Double>> list = new ArrayList<Map.Entry<Movie, Double>>(movieAndScore.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Movie, Double>>()

        {
            @Override
            public int compare(Map.Entry<Movie, Double> o1, Map.Entry<Movie, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        Collections.reverse(list);

        List<MovieMini> movieMinis = new ArrayList<>();
        int limit = size;
        for (Map.Entry<Movie, Double> map : list) {
            if (limit > 0) {
                List<GenreBean> genreBeen = this.genreIdToGenreBean(genreRepository.findGenreIdByIdMovie(map.getKey().getId()));
                movieMinis.add(new MovieMini(map.getKey(), genreBeen));
//                System.out.println(map.getKey());
//                System.out.println(map.getValue());
                limit--;
            }
        }
        return new Page<>(1, size, "rank", "desc", movies.size(), movieMinis);
    }

    private List<GenreBean> genreIdToGenreBean(List<Integer> genreIds) {
        List<GenreBean> genreBeanList = new ArrayList<>();
        for (Integer integer : genreIds) {
            String value = genreRepository.findGenreById(integer);
            genreBeanList.add(new GenreBean(integer, value));
        }
        return genreBeanList;
    }

    private String idListToValueString(List<Integer> ids) {
        String result = "";
        if (ids != null && ids.size() != 0) {
            for (Integer id : ids) {
                String value = countryRepository.findCountryByCountryId(id);
                result = result + value + ",";
            }
            return result.substring(0, result.length() - 1);
        }
        return result;
    }

    private List<PeopleMini> idListToPeopleMiniList(List<Integer> ids, String peolple) {
        List<PeopleMini> peopleMinis = new ArrayList<>();
        if (ids != null && ids.size() != 0) {
            String name = "";
            String poster = "";
            for (Integer id : ids) {
                if (peolple.equals("d")) {
                    Director director = directorRepository.findDirectorByDirectorId(id);
                    name = director.getName();
                    poster = director.getProfile();
                } else if (peolple.equals("a")) {
                    Actor actor = actorRepository.findActorByActorId(id);
                    name = actor.getName();
                    poster = actor.getProfile();
                }
                peopleMinis.add(new PeopleMini(id, name, poster));
            }
        }
        return peopleMinis;
    }

    private List<KeywordBean> keywordIdToKeywordBean(List<Integer> keywordIds) {
        List<KeywordBean> keywordBeanList = new ArrayList<>();
        for (Integer id : keywordIds) {
            String value = keywordRepository.findKeywordCNByKeywordId(id);
            keywordBeanList.add(new KeywordBean(id, value));
        }
        return keywordBeanList;
    }
//    /**
//     * 得到类型信息
//     *
//     * @return
//     */
//    public GenreInfo findGenreInfo(String genre, int startYear) {
//        //initial
//        ArrayList<Integer> count = new ArrayList<>();
//        ArrayList<Double> avgScore = new ArrayList<>();
//        ArrayList<Integer> years = new ArrayList<>();
//        GenreInfo genreInfo = new GenreInfo(genre, count, avgScore, years);
//
//        List<String> movieIds = movieRepository.findMovieIdByGenre(genre);
//        //
//        for (int i = startYear; i < 2018; i++) {
//            String year = String.valueOf(i);
//            years.add(i);
//
//            //
//            count.add(movieRepository.countByYears(movieIds, year));
//            avgScore.add(movieRepository.avgByYears(movieIds, year));
//        }
//        return genreInfo;
//    }


}
