package moviereview.service.impl;


import moviereview.bean.GenreBean;
import moviereview.bean.MovieMini;
import moviereview.model.*;
import moviereview.repository.*;
import moviereview.service.RecommendService;
import moviereview.util.RecommendType;
import moviereview.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by SilverNarcissus on 2017/5/12.
 */
@Service
public class RecommendServiceImpl implements RecommendService {
    /**
     * 用户点选喜爱时增加因子
     */
    private static final double FAVORITE_FACTOR = 5.0;

    /**
     * 用户看过时增加因子
     */
    private static final double VIEWED_FACTOR = 1.0;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ActorFactorRepository actorFactorRepository;

    @Autowired
    private DirectorFactorRepository directorFactorRepository;

    @Autowired
    private GenreFactorRepository genreFactorRepository;

    /**
     * 每日推荐
     *
     * @param userId 用户ID
     * @return 每日推荐的6部电影
     */
    @Override
    public List<Movie> everyDayRecommend(int userId, int limit) {
        System.out.println(userRepository);
        User user = userRepository.findUserById(userId);

        List<Integer> exception = new ArrayList<>(userRepository.getUserCollect(userId));
        exception.addAll(userRepository.getUserEvaluate(userId));

        TreeMap<Double, Integer> integratedFactorSort = new TreeMap<>();
        Map<Integer, Double> integratedFactor = new HashMap<>();

        Map<Integer, Double> userGenreFactor = getFavoriteGenreMovies(user);
        Map<Integer, Double> userActorFactor = getFavoriteActorMovies(user);
        Map<Integer, Double> userDirectorFactor = getFavoriteDirectorMovies(user);

        //得到类型因子
        for (int userGenre : userGenreFactor.keySet()) {
            double genreFactor = userGenreFactor.get(userGenre);
            for (int genreMovieId : movieRepository.findMovieIdByGenre(userGenre)) {
                double factor = 0;
                if (integratedFactor.containsKey(genreMovieId)) {
                    factor = integratedFactor.get(genreMovieId);
                }
                factor += genreFactor;
                integratedFactor.put(genreMovieId, factor);
            }
        }
        //得到演员因子
        for (int userActor : userActorFactor.keySet()) {
            double actorFactor = userActorFactor.get(userActor);
            for (int actorMovieId : movieRepository.findMovieIdByActorId(userActor)) {
                double factor = 0;
                if (integratedFactor.containsKey(actorMovieId)) {
                    factor = integratedFactor.get(actorMovieId);
                }
                factor += actorFactor;
                integratedFactor.put(actorMovieId, factor);
            }
        }
        //得到导演因子
        for (int userDirector : userDirectorFactor.keySet()) {
            double directorFactor = userDirectorFactor.get(userDirector);
            for (int directorMovieId : movieRepository.findMovieIdByDirectorId(userDirector)) {
                double factor = 0;
                if (integratedFactor.containsKey(directorMovieId)) {
                    factor = integratedFactor.get(directorMovieId);
                }
                factor += directorFactor;
                integratedFactor.put(directorMovieId, factor);
            }
        }


        for (Map.Entry<Integer, Double> entry : integratedFactor.entrySet()) {
            integratedFactorSort.put(entry.getValue(), entry.getKey());
        }

        List<Movie> result = new ArrayList<>(limit);
        for (int i = 0; i < limit; i++) {
            if (integratedFactorSort.size() == 0) {
                result.addAll(getNewMovie(limit - result.size()));
                return result;
            }
            int movieId = integratedFactorSort.pollLastEntry().getValue();
            if (exception.contains(movieId)) {
                i--;
                continue;
            }
            result.add(movieRepository.findMovieById(movieId));
        }

        return result;
    }

    /**
     * 看完某部电影之后的推荐
     *
     * @param userId  观看用户ID
     * @param type    用户选择的喜好类型
     * @param content 喜好内容
     * @return 含有最多6部电影的电影集合
     */
    public List<Movie> finishSeeingRecommend(int userId, RecommendType type, String content, int limit) {
        try {
            switch (type) {

                case GENRE:
                    return movieRepository.findMovieByGenre(content, 0, limit + 1);

                case ACTOR:
                    return movieRepository.findMovieByActor(content, 0, limit + 1);

                case DIRECTOR:
                    return movieRepository.findMovieByDirector(content, 0, limit + 1);

                default:
                    return new ArrayList<>(everyDayRecommend(userId, limit));
            }
        } catch (Exception e) {
            return new ArrayList<>(everyDayRecommend(userId, limit));
        }
    }


    /**
     * 得到最新的电影
     *
     * @param limit 需要得到的电影数量
     * @return 含所需数量的最新的电影的列表
     */
    public List<Movie> getNewMovie(int limit) {
        List<Movie> rowResult = movieRepository.findLatestMovies(limit * 2, LocalDate.now().toString());

        System.out.println(rowResult.size());
        //下面生成number个不重复的随机数
        Set<Integer> randomNumbers = new HashSet<>(limit);
        Random random = new Random();
        while (randomNumbers.size() < limit) {
            randomNumbers.add(random.nextInt(limit * 2 - 1));
        }
        //
        ArrayList<Movie> result = new ArrayList<>(limit);
        for (int i : randomNumbers) {
            result.add(rowResult.get(i));
        }
        return result;
    }


    @Override
    public ResultMessage addGenreFactorWhenViewed(int userId, int genre) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }


        try {
            addGenreFactor(genre, user, VIEWED_FACTOR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultMessage.SUCCESS;
    }


    @Override
    public ResultMessage addGenreFactorWhenFavored(int userId, int movieGenre) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }


        try {
            addGenreFactor(movieGenre, user, FAVORITE_FACTOR);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage addActorFactorWhenViewed(int userId, int actor) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }


        try {
            addActorFactor(actor, user, VIEWED_FACTOR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage addActorFactorWhenFavored(int userId, int actor) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        try {
            addActorFactor(actor, user, FAVORITE_FACTOR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage addDirectorFactorWhenViewed(int userId, int director) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        try {
            addDirectorFactor(director, user, VIEWED_FACTOR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage addDirectorFactorWhenFavored(int userId, int director) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return ResultMessage.FAILED;
        }

        try {
            addDirectorFactor(director, user, FAVORITE_FACTOR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultMessage.SUCCESS;
    }

    /******************************************************************************
     ************************************private************************************
     ******************************************************************************/

    /*
     * 按类型寻找最喜爱的电影
     */
    private Map<Integer, Double> getFavoriteGenreMovies(User user) {
        ArrayList<GenreFactor> factors = new ArrayList<>(user.getGenreFactors());
        Map<Integer, Double> result = new HashMap<>(factors.size());
        for (GenreFactor factor : factors) {
            result.put(factor.getMovieGenre(), factor.getFactor());
        }
        return result;
    }

    /*
     * 按演员寻找最喜爱的电影
     */
    private Map<Integer, Double> getFavoriteActorMovies(User user) {
        ArrayList<ActorFactor> factors = new ArrayList<>(user.getActorFactors());
        Map<Integer, Double> result = new HashMap<>(factors.size());
        for (ActorFactor factor : factors) {
            result.put(factor.getName(), factor.getFactor());
        }
        return result;
    }

    /*
     * 按导演寻找最喜爱的电影
     */
    private Map<Integer, Double> getFavoriteDirectorMovies(User user) {
        ArrayList<DirectorFactor> factors = new ArrayList<>(user.getDirectorFactors());
        Map<Integer, Double> result = new HashMap<>(factors.size());
        for (DirectorFactor factor : factors) {
            result.put(factor.getName(), factor.getFactor());
        }
        return result;
    }

    /**
     * 增加类型因子
     */
    private void addGenreFactor(int movieGenre, User user, double quantity) {
        //寻找存在的记录
        for (GenreFactor genreFactor : user.getGenreFactors()) {
            if (genreFactor.getMovieGenre() == movieGenre) {
                genreFactorRepository.updateGenre(genreFactor.getId(), genreFactor.getFactor() + quantity);
                return;
            }
        }
        //如果没找到，则增加一条新纪录
        System.out.println("!!!!" + quantity + " " + movieGenre + " " + user.getId());

        GenreFactor genreFactor = new GenreFactor(quantity, movieGenre, user);
        genreFactorRepository.save(genreFactor);
    }

    /**
     * 增加演员因子
     */
    private void addActorFactor(int actor, User user, double quantity) {
        //寻找存在的记录
        for (ActorFactor actorFactor : user.getActorFactors()) {
            if (actorFactor.getName() == (actor)) {
                actorFactorRepository.updateActor(actorFactor.getId(), actorFactor.getFactor() + quantity);
                return;
            }
        }
        //如果没找到，则增加一条新纪录
        ActorFactor actorFactor = new ActorFactor(quantity, actor, user);
        actorFactorRepository.save(actorFactor);
    }

    /**
     * 增加导演因子
     */
    private void addDirectorFactor(int director, User user, double quantity) {
        //寻找存在的记录
        for (DirectorFactor directorFactor : user.getDirectorFactors()) {
            if (directorFactor.getName() == (director)) {
                directorFactorRepository.updateDirector(directorFactor.getId(), directorFactor.getFactor() + quantity);
                return;
            }
        }
        //如果没找到，则增加一条新纪录
        DirectorFactor directorFactor = new DirectorFactor(quantity, director, user);
        directorFactorRepository.save(directorFactor);
    }

    public List<MovieMini> findSimilarMovie(int idmovie, int limit) {
        List<Integer> aimCountryId = countryRepository.findCountryIdByIdMovie(idmovie);
        List<Integer> tryMovieId = new ArrayList<>();
        List<Integer> tryMovieIdByCountry = new ArrayList<>();
        List<Integer> tryMovieIdByDirector = new ArrayList<>();
        List<Integer> tryMovieIdByGenre = new ArrayList<>();
        List<Integer> firstChoice = new ArrayList<>();
        List<Integer> secondChoice = new ArrayList<>();
        List<Integer> thirdChoice = new ArrayList<>();
        List<Integer> lastChoice = new ArrayList<>();
        List<Integer> aimDirectorId = directorRepository.findDirectorIdByMovieId(idmovie);
        List<Integer> aimGenreId = genreRepository.findGenreIdByIdMovie(idmovie);

        //根据国家筛选相似电影
        for (Integer id : aimCountryId) {
            List<Integer> tempMovieId = movieRepository.findMovieIdByCountry(id);
            tryMovieIdByCountry.addAll(tempMovieId);
        }

        //根据类型筛选
        for (Integer id : aimGenreId) {
            List<Integer> tempMovieId = movieRepository.findMovieIdByGenre(id);
            tryMovieIdByGenre.addAll(tempMovieId);
        }

        //根据导演筛选
        for (Integer id : aimDirectorId) {
            List<Integer> tempMovieId = movieRepository.findMovieIdByDirectorId(id);
            tryMovieIdByDirector.addAll(tempMovieId);
        }

        tryMovieIdByCountry.remove((Object) idmovie);
        for (Integer id : tryMovieIdByCountry) {
            if (tryMovieIdByGenre.contains(id) && tryMovieIdByDirector.contains(id) && !firstChoice.contains(id) && id != idmovie) {
                firstChoice.add(id);
            } else if (tryMovieIdByGenre.contains(id) && !tryMovieIdByDirector.contains(id) && !secondChoice.contains(id) && id != idmovie) {
                secondChoice.add(id);
            } else if (!tryMovieIdByGenre.contains(id) && tryMovieIdByDirector.contains(id) && !thirdChoice.contains(id) && id != idmovie) {
                thirdChoice.add(id);
            } else {
                if (id != idmovie) {
                    lastChoice.add(id);
                }
            }
        }

        if (firstChoice.size() >= 4) {
            tryMovieId = firstChoice;
        } else if (firstChoice.size() + secondChoice.size() >= 4) {
            tryMovieId.addAll(firstChoice);
            tryMovieId.addAll(secondChoice);
        } else if (firstChoice.size() + secondChoice.size() + thirdChoice.size() >= 4) {
            tryMovieId.addAll(firstChoice);
            tryMovieId.addAll(secondChoice);
            tryMovieId.addAll(thirdChoice);
        } else {
            int total = firstChoice.size() + secondChoice.size() + thirdChoice.size() + lastChoice.size();
            tryMovieId.addAll(firstChoice);
            tryMovieId.addAll(secondChoice);
            tryMovieId.addAll(thirdChoice);
            tryMovieId.addAll(lastChoice);
            if (total < 4) {
                int gap = limit - total;
                int count = gap;
                int i = 0;
                do {
                    int id = moreMovies(aimGenreId, count).get(i);
                    if (!tryMovieId.contains(id)) {
                        tryMovieId.add(id);
                        gap--;
                    }
                    count++;
                    i++;
                } while (gap > 0);
            }
        }

        tryMovieId = orderMovie(tryMovieId.subList(0, Math.max(limit, firstChoice.size())), limit);
        return movieIdToMovieMini(tryMovieId, limit);
    }

    private List<Integer> orderMovie(List<Integer> movie, int limit) {
        Map<Integer, Double> movieAndScore = new TreeMap<Integer, Double>();
        for (int id : movie) {
            double score = 0;
            if (movieRepository.findScoreByMovieId(id) != null) {
                score = movieRepository.findScoreByMovieId(id);
            }
            movieAndScore.put(id, score);
        }
        //根据评分对备选电影进行排序
        List<Map.Entry<Integer, Double>> list = new ArrayList<Map.Entry<Integer, Double>>(movieAndScore.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>()

        {
            @Override
            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        Collections.reverse(list);

        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Double> map : list) {
            if (limit > 0) {
                result.add(map.getKey());
                limit--;
            }
        }
        return result;
    }

    private List<GenreBean> genreIdToGenreBean(List<Integer> genreIds) {
        List<GenreBean> genreBeanList = new ArrayList<>();
        for (Integer integer : genreIds) {
            String value = genreRepository.findGenreById(integer);
            genreBeanList.add(new GenreBean(integer, value));
        }
        return genreBeanList;
    }

    private List<MovieMini> movieIdToMovieMini(List<Integer> movieId, int limit) {
        List<MovieMini> movieMinis = new ArrayList<>();
        if (movieId != null) {
            for (int i = 0; i < limit; i++) {
                int id = movieId.get(i);
                Movie movie = movieRepository.findMovieById(id);
                movieMinis.add(new MovieMini(movie, this.genreIdToGenreBean(genreRepository.findGenreIdByIdMovie(id))));
            }
        }
        return movieMinis;
    }

    private List<Integer> moreMovies(List<Integer> genreId, int limit) {
        List<String> genre = new ArrayList<>();
        for (Integer i : genreId) {
            genre.add(genreRepository.findGenreById(i));
        }
        return movieRepository.findMovieIdByGenreScoreDesc(genre, 0, limit);
    }

    public double getSimilarValue(int user1, int user2) {
        double actorFactors = 0;
        double relativeActorFactors = 0;
        double directorFactors = 0;
        double relativeDirectorFactors = 0;
        double genreFactors = 0;
        double relativeGenreFactors = 0;
        //actor
        if (userRepository.getActorFactor(user1) != null && (actorFactors = userRepository.getActorFactor(user1)) != 0) {
            for (double i : userRepository.getSimilarActorFactor(user1, user2)) {
                relativeActorFactors += i;
            }
        }
        //director
        if (directorRepository.getDirectorFactor(user1) != null && (directorFactors = directorRepository.getDirectorFactor(user1)) != 0) {
            for (double i : directorRepository.getSimilarDirectorFactor(user1, user2)) {
                relativeDirectorFactors += i;
            }
        }
        //genre
        if (genreRepository.getGenreFactor(user1) != null && (genreFactors = genreRepository.getGenreFactor(user1)) != 0) {
            for (double i : genreRepository.getSimilarGenreFactor(user1, user2)) {
                relativeGenreFactors += i;
            }
        }
        double allFactor = actorFactors + directorFactors + genreFactors;
        double allSimilarFactor = relativeActorFactors + relativeDirectorFactors + relativeGenreFactors;
        if (allFactor == 0) {
            return -1;
        } else {
            return allSimilarFactor / allFactor;
        }
    }

    public List<Movie> getSimilarMovie(int userId, int limit) {
        TreeMap<Double, Integer> similarUser = new TreeMap<Double, Integer>();
        for (int i : userRepository.getAllId()) {
            similarUser.put(getSimilarValue(userId, i), i);
        }

        //为了SQL正确，创建一个空set
        HashSet<Integer> nullSet = new HashSet<>();
        nullSet.add(-1);
        //该用户已经收藏或评价的电影
        HashSet<Integer> exception = getUserMovies(userId, nullSet);
        //如果上述集合是空的，则使用空集
        if (exception.size() == 0) {
            exception = nullSet;
        }
        //找到的相似电影集合
        HashSet<Integer> movieIds = new HashSet<>();

        while (similarUser.size() != 0 && movieIds.size() < limit) {
            Map.Entry<Double, Integer> entry = similarUser.pollLastEntry();
            System.err.println(entry.getValue());
            movieIds.addAll(getUserMovies(entry.getValue(), exception));
        }

        int count = 0;
        ArrayList<Movie> result = new ArrayList<>(limit);
        for (int id : movieIds) {
            result.add(movieRepository.findMovieById(id));
            count++;
            if (count > limit) {
                break;
            }
        }

        return result;
    }

    /**
     * 得到该用户收藏过或评价过的所有电影
     *
     * @param userId    用户Id
     * @param exception 不需要的电影Id
     * @return 所有电影Id集合
     */
    private HashSet<Integer> getUserMovies(int userId, Set<Integer> exception) {
        HashSet<Integer> movieIds = new HashSet<>();
        movieIds.addAll(userRepository.getUserCollectWithException(userId, exception));
        movieIds.addAll(userRepository.getUserEvaluateWithException(userId, exception));
        return movieIds;
    }
}

