package bl;

import data.DataServiceFactory;
import dataservice.ReviewDataService;
import javafx.scene.image.Image;
import po.*;
import util.LimitedHashMap;
import util.MovieGenre;
import util.MovieSortType;
import util.ReviewSortType;
import vo.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by vivian on 2017/3/4.
 */
class Movie {
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMapForAmazon = new LimitedHashMap<>(10);
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMapForImdb = new LimitedHashMap<>(10);
    private ReviewDataService reviewDataService = DataServiceFactory.getJsonService();
    //                private ReviewDataService reviewDataService = new ReviewDataServiceStub();
    private List<ReviewPO> reviewPOList;

    //电影和用户公用的获得ReviewCountVO的方法类
    private CommonReviewCountVOGetter commonReviewCountVOGetter;
    //根据不同时间（月或日）获得得分与日期关系V0（ScoreDateVO)的方法类
    private CommonScoreDateVOGetter commonScoreDateVOGetter;

    /**
     * 根据图片的URL返回一个Image
     *
     * @param imageUrl-图片源地址
     * @return Image
     */
    private static Image getImage(String imageUrl) {
        // 从服务器获得一个输入流(本例是指从服务器获得一个image输入流)

        if (imageUrl == null) {
            return null;
        }

        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        Image result;

        try {
            URL url = new URL(imageUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置网络连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            // 设置应用程序要从网络连接读取数据
            httpURLConnection.setDoInput(true);

            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                // 从服务器返回一个输入流
                inputStream = httpURLConnection.getInputStream();

            }

        } catch (MalformedURLException e) {
//            try {
//                inputStream = new FileInputStream(Constant.localDataPath+"img/index.jpg");
//            } catch (FileNotFoundException e1) {
//                e1.printStackTrace();
//            }
//            result = new Image(inputStream);
//            return result;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result = new Image(inputStream);
        return result;
    }

    /**
     * 根据 movieId 查找电影
     *
     * @param movieId 电影ID
     * @return MovieVO
     */
    public MovieVO findMovieById(String movieId) {
        MoviePO moviePO = reviewDataService.findMovieByMovieId(movieId);
        if (moviePO.getId().equals("-1")) return null;
        return new MovieVO(moviePO);
    }

    /**
     * 根据电影 movieId 查找评价分布 (Amazon)
     *
     * @param movieId 电影ID
     * @return ScoreDistributionVO
     */
    public ScoreDistributionVO findScoreDistributionByMovieIdFromAmazon(String movieId) {
        getReviewPOList(movieId, "Amazon");
        return findScoreDistributionByMovieId(5);
    }

    /**
     * 根据电影 movieId 查找评价分布 (Imdb)
     *
     * @param movieId 电影ID
     * @return ScoreDistributionVO
     */
    public ScoreDistributionVO findScoreDistributionByMovieIdFromIMDB(String movieId) {
        getReviewPOList(movieId, "Imdb");
        return findScoreDistributionByMovieId(10);
    }

    private ScoreDistributionVO findScoreDistributionByMovieId(int count) {
        if (reviewPOList.size() == 0) {
            return null;
        }

        List<Integer> reviewAmounts = new ArrayList<>(Collections.nCopies(count, 0));

//        List.fill(reviewAmounts, 0);

        for (int i = 0; i < reviewPOList.size(); i++) {
            if (reviewPOList.get(i).getScore() != 0) {
                int score = reviewPOList.get(i).getScore();
                if (count == 5) {
                    score = score / 2;
                }
                reviewAmounts.set(score - 1, reviewAmounts.get(score - 1) + 1);
            }
        }
        return new ScoreDistributionVO(reviewPOList.size(), reviewAmounts);
    }

    //迭代二

    public WordVO findWordsByMovieId(String movieId) {
        WordPO wordPO = reviewDataService.findWordsByMovieId(movieId);
        //如果是错误的movie id
        if (wordPO == null) {
            return null;
        }
        return new WordVO(wordPO.getTopWords());
    }

    public PageVO<MovieVO> findMoviesByKeywordInPage(String keyword, int page) {
        keyword = keyword.replace(" ", "+");
        PagePO<MoviePO> pagePO = reviewDataService.findMoviesByKeywordInPage(keyword, page);
        List<MovieVO> results = moviePoListToVOList(pagePO.getResult());
        if (results.size() == 0) {
            return new PageVO<MovieVO>(pagePO.getPageNo(), 0, results);
        }
        return new PageVO<MovieVO>(pagePO.getPageNo(), (pagePO.getTotalCount() + pagePO.getPageSize() - 1) / pagePO.getPageSize(), results);
    }

    public PageVO<MovieVO> findMoviesByTagInPage(EnumSet<MovieGenre> tag, MovieSortType movieSortType, int page) {
        PagePO<MoviePO> pagePO = reviewDataService.findMoviesByTagInPage(tag, movieSortType, page);
        List<MovieVO> results = moviePoListToVOList(pagePO.getResult());
        if (results.size() == 0) {
            return new PageVO<MovieVO>(pagePO.getPageNo(), 0, results);
        }
        return new PageVO<MovieVO>(pagePO.getPageNo(), (pagePO.getTotalCount() + pagePO.getPageSize() - 1) / pagePO.getPageSize(), results);
    }

    /**
     * 由moviePO列表转换为movieVO列表
     *
     * @param poList po列表
     * @return VO列表
     */
    @SuppressWarnings("unchecked")
    private List<MovieVO> moviePoListToVOList(List<MoviePO> poList) {
        List<MovieVO> newResults = new ArrayList<>();
        if (poList == null) {
            newResults = Collections.EMPTY_LIST;
        } else {
            for (MoviePO moviePO : poList) {
                String releaseDate = moviePO.getReleaseDate();
                if (releaseDate.equals("-1")) {
                    releaseDate = "";
                }
                MovieVO movieVO = new MovieVO(moviePO);
                newResults.add(movieVO);
            }
        }
        return newResults;
    }

    public MovieStatisticsVO findMovieStatisticsVOByMovieId(String movieId) {
        double scoreSum = 0;
        //评分平方和
        double scoreSquareSum = 0;
        TreeSet<LocalDate> dates = new TreeSet<>();

        getAllReviewPOList(movieId);
        if (reviewPOList.size() == 0) {
            return null;
        }

        for (int i = 0; i < reviewPOList.size(); i++) {
            scoreSum = scoreSum + reviewPOList.get(i).getScore();
            scoreSquareSum = scoreSquareSum + Math.pow(reviewPOList.get(i).getScore(), 2);
        }
        for (ReviewPO reviewPO : reviewPOList) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate();
            dates.add(date);
        }

        //计算评分均值
        double averageScore = scoreSum / reviewPOList.size();

        //计算评分平方
        //scoreSquareAverage,平方的均值
        double scoreSquareAverage = scoreSquareSum / reviewPOList.size();
        //averageScoreSquare,均值的平方
        double averageScoreSquare = Math.pow(averageScore, 2);
        double variance = scoreSquareAverage - averageScoreSquare;

        //第一条评论日期和最后一条评论日期
        String firstReviewDate = dates.first().toString();
        String lastReviewDate = dates.last().toString();

        int amazonSize = getReviewPOList(movieId, "Amazon").size();
        int imdbSize = getReviewPOList(movieId, "Imdb").size();
        return new MovieStatisticsVO(amazonSize, imdbSize, averageScore, variance, firstReviewDate, lastReviewDate);
    }

    /**
     * 根据电影 id 查找每年评论数量 (Amazon)
     *
     * @param movieId   电影ID
     * @param startYear eg. 2017
     * @param endYear   eg. 2017
     * @return
     */
    public ReviewCountVO[] findYearCountByMovieIdFromAmazon(String movieId, String startYear, String endYear) {
        getReviewPOList(movieId, "Amazon");

        bl.date.DateUtil dateUtil = new bl.date.YearDateUtil();
        bl.date.DateChecker dateChecker = new bl.date.YearDateChecker(startYear, endYear);
        bl.date.DateFormatter dateFormatter = new bl.date.YearDateFormatter();
        commonReviewCountVOGetter = new CommonReviewCountVOGetter(reviewPOList, startYear, endYear, dateUtil, dateChecker, dateFormatter, 5);

        return commonReviewCountVOGetter.getReviewCountVOs();
    }

    /**
     * 根据电影 id 查找每月评论数量 (Amazon)
     *
     * @param movieId    电影ID
     * @param startMonth eg. 2017-01
     * @param endMonth   eg. 2017-03
     * @return
     */
    public ReviewCountVO[] findMonthCountByMovieIdFromAmazon(String movieId, String startMonth, String endMonth) {
        getReviewPOList(movieId, "Amazon");

        bl.date.DateUtil dateUtil = new bl.date.MonthDateUtil();
        bl.date.DateChecker dateChecker = new bl.date.MonthDateChecker(startMonth, endMonth);
        bl.date.DateFormatter dateFormatter = new bl.date.MonthDateFormatter();
        commonReviewCountVOGetter = new CommonReviewCountVOGetter(reviewPOList, startMonth, endMonth, dateUtil, dateChecker, dateFormatter, 5);

        return commonReviewCountVOGetter.getReviewCountVOs();
    }

    /**
     * 根据电影 id 和起始时间和结束时间查找每日评论数量 (Amazon)
     *
     * @param movieId   电影ID
     * @param startDate eg. 2017-01-12
     * @param endDate   eg. 2017-02-03
     * @return
     */
    public ReviewCountVO[] findDayCountByMovieIdFromAmazon(String movieId, String startDate, String endDate) {
        getReviewPOList(movieId, "Amazon");

        bl.date.DateUtil dateUtil = new bl.date.DayDateUtil();
        bl.date.DateChecker dateChecker = new bl.date.DayDateChecker(startDate, endDate);
        bl.date.DateFormatter dateFormatter = new bl.date.DayDateFormatter();
        commonReviewCountVOGetter = new CommonReviewCountVOGetter(reviewPOList, startDate, endDate, dateUtil, dateChecker, dateFormatter, 5);

        return commonReviewCountVOGetter.getReviewCountVOs();
    }

    /**
     * 根据电影 id 查找每年评论数量 (Imdb)
     *
     * @param movieId   电影ID
     * @param startYear eg. 2017
     * @param endYear   eg. 2017
     * @return
     */
    public ReviewCountVO[] findYearCountByMovieIdFromImdb(String movieId, String startYear, String endYear) {
        getReviewPOList(movieId, "Imdb");

        bl.date.DateUtil dateUtil = new bl.date.YearDateUtil();
        bl.date.DateChecker dateChecker = new bl.date.YearDateChecker(startYear, endYear);
        bl.date.DateFormatter dateFormatter = new bl.date.YearDateFormatter();
        commonReviewCountVOGetter = new CommonReviewCountVOGetter(reviewPOList, startYear, endYear, dateUtil, dateChecker, dateFormatter, 10);

        return commonReviewCountVOGetter.getReviewCountVOs();
    }

    /**
     * 根据电影 id 查找每月评论数量 (Imdb)
     *
     * @param movieId    电影ID
     * @param startMonth eg. 2017-01
     * @param endMonth   eg. 2017-03
     * @return
     */
    public ReviewCountVO[] findMonthCountByMovieIdFromImdb(String movieId, String startMonth, String endMonth) {
        getReviewPOList(movieId, "Imdb");

        bl.date.DateUtil dateUtil = new bl.date.MonthDateUtil();
        bl.date.DateChecker dateChecker = new bl.date.MonthDateChecker(startMonth, endMonth);
        bl.date.DateFormatter dateFormatter = new bl.date.MonthDateFormatter();
        commonReviewCountVOGetter = new CommonReviewCountVOGetter(reviewPOList, startMonth, endMonth, dateUtil, dateChecker, dateFormatter, 10);

        return commonReviewCountVOGetter.getReviewCountVOs();
    }

    /**
     * 根据电影 id 和起始时间和结束时间查找每日评论数量 (Imdb)
     *
     * @param movieId   电影ID
     * @param startDate eg. 2017-01-12
     * @param endDate   eg. 2017-02-03
     * @return
     */
    public ReviewCountVO[] findDayCountByMovieIdFromImdb(String movieId, String startDate, String endDate) {
        getReviewPOList(movieId, "Imdb");

        bl.date.DateUtil dateUtil = new bl.date.DayDateUtil();
        bl.date.DateChecker dateChecker = new bl.date.DayDateChecker(startDate, endDate);
        bl.date.DateFormatter dateFormatter = new bl.date.DayDateFormatter();
        commonReviewCountVOGetter = new CommonReviewCountVOGetter(reviewPOList, startDate, endDate, dateUtil, dateChecker, dateFormatter, 10);

        return commonReviewCountVOGetter.getReviewCountVOs();
    }

    public PageVO<ReviewVO> findReviewsByMovieIdInPageFromAmazon(String movieId, ReviewSortType reviewSortType, int page) {
        PagePO<ReviewPO> pagePO = reviewDataService.findReviewsByMovieIdInPageFromAmazon(movieId, reviewSortType, page);
        return findReviewsByMovieIdInPage(pagePO);
    }

    public PageVO<ReviewVO> findReviewsByMovieIdInPageFromIMDB(String movieId, ReviewSortType reviewSortType, int page) {
        PagePO<ReviewPO> pagePO = reviewDataService.findReviewsByMovieIdInPageFromImdb(movieId, reviewSortType, page);
        return findReviewsByMovieIdInPage(pagePO);
    }

    private PageVO<ReviewVO> findReviewsByMovieIdInPage(PagePO<ReviewPO> pagePO) {
        List<ReviewPO> results = pagePO.getResult();
        List<ReviewVO> newResults = new ArrayList<>();
        if (results == null) {
            newResults = Collections.EMPTY_LIST;
        } else {
            for (int i = 0; i < results.size(); i++) {
                ReviewPO reviewPO = results.get(i);
                ReviewVO reviewVO = new ReviewVO(reviewPO, getImage(reviewPO.getAvatar()));
                newResults.add(reviewVO);
            }
        }
        return new PageVO<ReviewVO>(pagePO.getPageNo(), (pagePO.getTotalCount() + pagePO.getPageSize() - 1) / pagePO.getPageSize(), newResults);
    }

    //分类统计
    public MovieGenreVO findMovieGenre() {
        MovieGenrePO movieGenrePO = reviewDataService.findMovieGenre();
        return new MovieGenreVO(movieGenrePO.getTags(), movieGenrePO.getAmounts());
    }

    public ScoreAndReviewAmountVO findRelationBetweenScoreAndReviewAmount(EnumSet<MovieGenre> tag) {
        ScoreAndReviewAmountPO scoreAndReviewAmountPO = reviewDataService.findRelationBetweenScoreAndReviewAmount(tag);
        return new ScoreAndReviewAmountVO(scoreAndReviewAmountPO.getNames(), scoreAndReviewAmountPO.getScores(), scoreAndReviewAmountPO.getReviewAmounts());
    }

    public ScoreDateVO findScoreDateByYear(String Id, String startYear, String endYear) {
        getAllReviewPOList(Id);

        bl.date.DateChecker dateChecker = new bl.date.YearDateChecker(startYear, endYear);
        bl.date.DateFormatter dateFormatter = new bl.date.YearDateFormatter();
        bl.date.DateUtil dateUtil = new bl.date.YearDateUtil();

        commonScoreDateVOGetter = new CommonScoreDateVOGetter(reviewPOList, startYear, endYear, dateChecker, dateFormatter, dateUtil);
        return commonScoreDateVOGetter.getScoreDateVO();
    }

    public ScoreDateVO findScoreDateByMonth(String Id, String startMonth, String endMonth) {
        getAllReviewPOList(Id);

        bl.date.DateChecker dateChecker = new bl.date.MonthDateChecker(startMonth, endMonth);
        bl.date.DateFormatter dateFormatter = new bl.date.MonthDateFormatter();
        bl.date.DateUtil dateUtil = new bl.date.MonthDateUtil();

        commonScoreDateVOGetter = new CommonScoreDateVOGetter(reviewPOList, startMonth, endMonth, dateChecker, dateFormatter, dateUtil);
        return commonScoreDateVOGetter.getScoreDateVO();
    }

    public ScoreDateVO findScoreDateByDay(String Id, String startDate, String endDate) {
        getAllReviewPOList(Id);

        bl.date.DateChecker dateChecker = new bl.date.DayDateChecker(startDate, endDate);
        bl.date.DateFormatter dateFormatter = new bl.date.DayDateFormatter();
        bl.date.DateUtil dateUtil = new bl.date.DayDateUtil();

        commonScoreDateVOGetter = new CommonScoreDateVOGetter(reviewPOList, startDate, endDate, dateChecker, dateFormatter, dateUtil);
        return commonScoreDateVOGetter.getScoreDateVO();
    }

    public Image findPosterByMovieId(String Id, int width) {
        MoviePO moviePO = reviewDataService.findMovieByMovieId(Id);
        String imageUrl = moviePO.getImageURL();

        //将图片指定为要求大小
        int first = imageUrl.lastIndexOf("X");
        first = first + 1;
        int last = imageUrl.lastIndexOf(".");
        String subStr = imageUrl.substring(first, last);
        imageUrl = imageUrl.replace(subStr, Integer.toString(width));

        return getImage(imageUrl);
    }

    public BoxPlotVO getBoxPlotVOFromAmazon(String movieId) {
        getReviewPOList(movieId, "Amazon");
        return getBoxPlotVO(5);
    }

    public BoxPlotVO getBoxPlotVOFromImdb(String movieId) {
        getReviewPOList(movieId, "Imdb");
        return getBoxPlotVO(10);
    }

    public double calCorCoefficientWithScoreAndReviewAmount(EnumSet<MovieGenre> tag) {
        ScoreAndReviewAmountVO scoreAndReviewAmountVO = this.findRelationBetweenScoreAndReviewAmount(tag);
        if (scoreAndReviewAmountVO.scores.size() == 0) {
            return 0;
        }
        double scoreAverage = 0;
        double scoreSum = 0;
        double scoreSquareSum = 0;
        double amountSum = 0;
        double amountAverage = 0;
        double amountSquareSum = 0;
        for (double score : scoreAndReviewAmountVO.scores) {
            scoreSum = scoreSum + score;
            scoreSquareSum += Math.pow(score, 2);
        }
        for (int amount : scoreAndReviewAmountVO.reviewAmounts) {
            amountSum = amountSum + amount;
            amountSquareSum += Math.pow(amount, 2);
        }
        scoreAverage = scoreSum / scoreAndReviewAmountVO.scores.size();
        amountAverage = amountSum / scoreAndReviewAmountVO.reviewAmounts.size();

        double covSum = 0;
        for (int i = 0; i < scoreAndReviewAmountVO.reviewAmounts.size(); i++) {
            covSum += (scoreAndReviewAmountVO.scores.get(i) - scoreAverage)
                    * (scoreAndReviewAmountVO.reviewAmounts.get(i) - amountAverage);
        }
        double cov = covSum / scoreAndReviewAmountVO.scores.size();
        double varScore = scoreSquareSum - Math.pow(scoreAverage, 2);
        double varAmount = amountSquareSum - Math.pow(amountAverage, 2);
        double denominator = Math.sqrt(varScore * varAmount);
        return cov / denominator;
    }

    public List<MovieVO> findSimilarMovies(MovieVO movieVO) {
        EnumSet<MovieGenre> tags = EnumSet.of(MovieGenre.All);

        for (String genre : movieVO.genre) {
            MovieGenre movieGenre = MovieGenre.getMovieGenreByName(genre);
            if (movieGenre != null) {
                tags.add(movieGenre);
            }
        }
        tags.remove(MovieGenre.All);
        double score = movieVO.score;

        PagePO<MoviePO> moviePOPagePO = reviewDataService.findMoviesByTagInPage(tags, MovieSortType.SCORE_DESC, 0);
        int totalPage = (moviePOPagePO.getTotalCount() + moviePOPagePO.getPageSize() - 1) / moviePOPagePO.getPageSize();
        int currentPage = 0;
        List<MovieVO> movieVOs = new ArrayList<>();
        List<MoviePO> moviePOs = new ArrayList<>();
        List<MovieScoreCompare> movieScoreCompareList = new ArrayList<>();
        while (currentPage < totalPage) {
            List<MoviePO> moviePOsTemp = moviePOPagePO.getResult();
            if (moviePOsTemp != null) {
                for (MoviePO moviePO : moviePOsTemp) {
                    if (!moviePO.getId().equals(movieVO.id)) {
                        moviePOs.add(moviePO);
                        MovieScoreCompare movieScoreCompare = new MovieScoreCompare(moviePO.getId(), Math.abs(score - moviePO.getRating()));
                        movieScoreCompareList.add(movieScoreCompare);
                    }
                }
            }
            currentPage++;
            if (currentPage < totalPage) {
                moviePOPagePO = reviewDataService.findMoviesByTagInPage(tags, MovieSortType.SCORE_DESC, currentPage);
            }
        }
        Collections.sort(movieScoreCompareList);
        int size = 5;
        if (movieScoreCompareList.size() < size) {
            size = movieScoreCompareList.size();
        }
        for (int i = 0; i < size; i++) {
            String movieId = movieScoreCompareList.get(i).movieId;
            for (MoviePO moviePO : moviePOs) {
                if (movieId.equals(moviePO.getId())) {
                    movieVOs.add(new MovieVO(moviePO));
                    break;
                }
            }
        }
        return movieVOs;
    }

    private BoxPlotVO getBoxPlotVO(int maxScore) {
        if (reviewPOList.size() == 0) {
            return null;
        }
        List<Integer> allScores = new ArrayList<>();
        for (ReviewPO reviewPO : reviewPOList) {
            int score = reviewPO.getScore();
            if (maxScore == 5) {
                score = score / 2;
            }
            allScores.add(score);
        }

        Collections.sort(allScores);
        int size = allScores.size();

        //计算Q1,Q2,Q3,下边缘和上边缘
        double Q1 = calNum((size + 1) * 1.0 / 4, allScores);
        double Q2 = calNum((size + 1) * 2.0 / 4, allScores);
        double Q3 = calNum((size + 1) * 3.0 / 4, allScores);
        double IQR = Q3 - Q1;
        double upper = Q3 + 1.5 * IQR;
        double lower = Q1 - 1.5 * IQR;
        List<Double> quartiles = new ArrayList<>();
        quartiles.addAll(Arrays.asList(lower, Q1, Q2, Q3, upper));

        //计算离群点
        List<Double> outerliers = new ArrayList<>();
        for (int score : allScores) {
            if (score < lower || score > upper) {
                outerliers.add(score + 0.0);
            }
        }

        int minScore = 0;
        if (minScore >= lower) {
            minScore = (int) lower - 1;
        }
        if (maxScore <= upper) {
            maxScore = (int) upper + 1;
        }

        return new BoxPlotVO(maxScore, minScore, quartiles, outerliers);
    }

    private double calNum(Double d, List<Integer> scores) {
        if (d - Math.floor(d) == Math.ceil(d) - d) {
            //小数位是0.5的情况
            double low = scores.get((int) Math.floor(d));
            double high = scores.get((int) Math.ceil(d));
            return (low + high) / 2;
        } else {
            return scores.get((int) Math.round(d));
        }
    }

    private List<ReviewPO> getReviewPOList(String movieId, String source) {
        if (source.equals("Amazon")) {
            if (!reviewPOLinkedHashMapForAmazon.containsKey(movieId)) {
                reviewPOList = reviewDataService.findAllReviewsByMovieIdFromAmazon(movieId);
                if (reviewPOList.size() != 0) {
                    reviewPOLinkedHashMapForAmazon.put(movieId, reviewPOList);
                } else {
                    System.out.println("There is no reviews matching the movieId.");
                    return Collections.emptyList();
                }
            } else {
                reviewPOList = reviewPOLinkedHashMapForAmazon.get(movieId);
            }
        } else {
            if (!reviewPOLinkedHashMapForImdb.containsKey(movieId)) {
                reviewPOList = reviewDataService.findAllReviewsByMovieIdFromImdb(movieId);
                if (reviewPOList != null && reviewPOList.size() != 0) {
                    reviewPOLinkedHashMapForImdb.put(movieId, reviewPOList);
                } else {
                    System.out.println("There is no reviews matching the movieId.");
                    return Collections.emptyList();
                }
            } else {
                reviewPOList = reviewPOLinkedHashMapForImdb.get(movieId);
            }
        }

        return reviewPOList;
    }

    private List<ReviewPO> getAllReviewPOList(String movieId) {
        List<ReviewPO> allList = new ArrayList<>();
        List<ReviewPO> listAmazon = getReviewPOList(movieId, "Amazon");
        List<ReviewPO> listImdb = getReviewPOList(movieId, "Imdb");
        allList.addAll(listAmazon);
        allList.addAll(listImdb);
        reviewPOList = allList;
        return reviewPOList;
    }
}