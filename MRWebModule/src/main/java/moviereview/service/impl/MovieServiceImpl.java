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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by Kray on 2017/3/7.
 */
@Service
public class MovieServiceImpl implements MovieService {

//    private String FilePath = "/Users/Kray/Desktop/PythonHelper/iteration3/";
//    private String FilePath = "/Users/Sorumi/Developer/MovieReview/MRWebModule/src/main/resources/python-iteration3/";


    @Autowired
    RecommendService recommendService;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    private static final double IMDB_AVERAGE_SCORE = 4.94;

    private static final double DOUBAN_AVERAGE_SCORE = 3.50;

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
    public Page<MovieMini> getMovieRankCN(int size) {
        List<Movie> movies = movieRepository.findMovieForRankCN(9);
        List<MovieMini> movieMinis = getMovieRank(movies, size, "CN");
        return new Page<>(1, size, "rankCN", "desc", movies.size(), movieMinis);
    }

    @Override
    public Page<MovieMini> getMovieRankFR(int size) {
        List<Movie> movies = movieRepository.findMovieForRankFR(8.6);
        List<MovieMini> movieMinis = getMovieRank(movies, size, "FR");
        return new Page<>(1, size, "rankFR", "desc", movies.size(), movieMinis);
    }

    @Override
    public List<GenreCountBean> genreCount() {
        List<GenreCountBean> result = new ArrayList<>();
        List<Integer> allGenreId = genreRepository.findAllGenreId();
        for (Integer genreId : allGenreId) {
            List<BigDecimal> imdbScores = movieRepository.findMovieImdbScoreByGenre(genreId);
            GenreCountBean foreign = createGenreCountBean(imdbScores, genreId, "foreign", 0, 0);
            result.add(foreign);
            List<BigDecimal> doubanScores = movieRepository.findMovieDoubanScoreByGenre(genreId);
            GenreCountBean domestic = createGenreCountBean(doubanScores, genreId, "domestic", 0, 0);
            result.add(domestic);
        }
        return result;
    }

    private GenreCountBean createGenreCountBean(List<BigDecimal> scores, int genreId, String type, int more, int less) {
        for (BigDecimal b : scores) {
            if (b != null) {
                Double d = b.doubleValue();
                if (type.equals("foreign")) {
                    if (d >= IMDB_AVERAGE_SCORE) {
                        more++;
                    } else {
                        less++;
                    }
                } else if (type.equals("domestic")) {
                    if (d >= DOUBAN_AVERAGE_SCORE) {
                        more++;
                    } else {
                        less++;
                    }
                }
            }
        }
        return new GenreCountBean(genreId, type, more, less);
    }

    private List<MovieMini> getMovieRank(List<Movie> movies, int size, String type) {
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

        DecimalFormat df = new DecimalFormat("#.##");
        for (Movie movie : movies) {
            if (type.equals("CN")) {
                r = movie.getDouban_score();
                v = movie.getDouban_count();
            } else if (type.equals("FR")) {
                r = movie.getImdb_score();
                v = movie.getImdb_count();
            }
            score = (v / (v + m)) * r + (m / (v + m)) * c;
            score = Double.parseDouble(df.format(score));
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
        for (Map.Entry<Movie, Double> map : list) {
            if (size > 0) {
                List<GenreBean> genreBeen = this.genreIdToGenreBean(genreRepository.findGenreIdByIdMovie(map.getKey().getId()));
                movieMinis.add(new MovieMini(map.getKey(), map.getValue(), genreBeen));
                size--;
            }
        }
        return movieMinis;
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
            double popularity = 0;
            for (Integer id : ids) {
                if (peolple.equals("d")) {
                    Director director = directorRepository.findDirectorByDirectorId(id);
                    name = director.getName();
                    poster = director.getProfile();
                    popularity = director.getPopularity();
                } else if (peolple.equals("a")) {
                    Actor actor = actorRepository.findActorByActorId(id);
                    name = actor.getName();
                    poster = actor.getProfile();
                    popularity = actor.getPopularity();
                }
                peopleMinis.add(new PeopleMini(id, name, popularity, poster));
            }
        }
        return peopleMinis;
    }

    private List<KeywordBean> keywordIdToKeywordBean(List<Integer> keywordIds) {
        List<KeywordBean> keywordBeanList = new ArrayList<>();
        for (Integer id : keywordIds) {
            String chinese = keywordRepository.findKeywordCNByKeywordId(id);
            String english = keywordRepository.findKeywordENByKeywordId(id);
            keywordBeanList.add(new KeywordBean(id, chinese, english));
        }
        return keywordBeanList;
    }


    @Override
    public List<GenreYearBean> genreInYear(int genreId) {
        List<GenreYearBean> genreYearBeens = new ArrayList<>();
        for (int i = 1970; i <= 2017; i++) {
            int allMovieSize = movieRepository.findMovieInYear(i);
            List<BigDecimal> rightMovieScore = movieRepository.findMovieScoreByGenreInYear(genreId, i);
            if (rightMovieScore!=null && rightMovieScore.size()!=0) {
                int rightSize = rightMovieScore.size();
                Double score = 0.0;
                for (BigDecimal b : rightMovieScore) {
                    if (b!=null) {
                        score = score + b.doubleValue();
                    }
                }
                double count = rightSize * 1.0 / allMovieSize;
                score = score / rightSize;
                DecimalFormat df = new DecimalFormat("#.##");
                score = Double.parseDouble(df.format(score));
                count = Double.parseDouble(df.format(count));
                GenreYearBean genreYearBean = new GenreYearBean(i, count, score);
                genreYearBeens.add(genreYearBean);
            }
        }
        return genreYearBeens;
    }

    public List<ScorePyramid> getScorePyramid() {
        List<ScorePyramid> results = new ArrayList<>(48);
        String baseLabel = "More than ";
        for (int year = 1970; year < 2018; year++) {
            List<SubScorePyramid> values = new ArrayList<>(7);
            values.add(new SubScorePyramid(baseLabel + "3", movieRepository.findYearScoreCount3(year)));
            values.add(new SubScorePyramid(baseLabel + "4", movieRepository.findYearScoreCount4(year)));
            values.add(new SubScorePyramid(baseLabel + "5", movieRepository.findYearScoreCount5(year)));
            values.add(new SubScorePyramid(baseLabel + "6", movieRepository.findYearScoreCount6(year)));
            values.add(new SubScorePyramid(baseLabel + "7", movieRepository.findYearScoreCount7(year)));
            values.add(new SubScorePyramid(baseLabel + "8", movieRepository.findYearScoreCount8(year)));
            values.add(new SubScorePyramid(baseLabel + "9", movieRepository.findYearScoreCount9(year)));
            results.add(new ScorePyramid(year, values));
        }

        return results;
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


    private List<Double> calculate() {
        List<BigDecimal> doubanScore = movieRepository.findAllMovieDoubanScore();
        List<BigDecimal> imdbScore = movieRepository.findAllMovieImdbScore();
        int sizeFR = imdbScore.size();
        int sizeCN = doubanScore.size();
        System.out.println("scoreCN SIZE origin " + sizeCN);
        System.out.println("scoreFR SIZE origin " + sizeFR);
        List<Double> scores = new ArrayList<>();
        double scoreFR = 0.0;
        double scoreCN = 0.0;
        for (BigDecimal bigDecimal : imdbScore) {
            if (bigDecimal != null) {
                Double d = bigDecimal.doubleValue();
                if (d != 0) {
                    scoreFR = scoreFR + d;
                } else {
                    sizeFR--;
                }
            }
        }
        for (BigDecimal bigDecimal : doubanScore) {
            if (bigDecimal != null) {
                Double d = bigDecimal.doubleValue();
                if (d != null && d != 0) {
                    scoreCN = scoreCN + d;
                } else {
                    sizeCN--;
                }
            }
        }
        scoreFR = scoreFR / sizeFR;
        scoreCN = scoreCN / sizeCN;
        scores.add(scoreFR);
        scores.add(scoreCN);
        System.out.println("scoreCN SIZE " + sizeCN);
        System.out.println("scoreFR SIZE " + sizeFR);
        return scores;
    }
}
