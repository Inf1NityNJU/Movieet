package bl;

import bl.date.*;
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
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMapForAll = new LimitedHashMap<>(10);
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMapForAmazon = new LimitedHashMap<>(10);
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMapForImdb = new LimitedHashMap<>(10);
    private ReviewDataService reviewDataService = DataServiceFactory.getJsonService();
    //        private ReviewDataService reviewDataService = new ReviewDataServiceStub();
    private List<ReviewPO> reviewPOList;

    //电影和用户公用的获得ReviewCountVO的方法类
    private CommonReviewCountVOGetter commonReviewCountVOGetter;
    //根据不同时间（月或日）获得得分与日期关系V0（ScoreDateVO)的方法类
    private CommonScoreDateVOGetter commonScoreDateVOGetter;


    /**
     * 根据 movieId 查找电影
     *
     * @param movieId 电影ID
     * @return MovieVO
     */
    public MovieVO findMovieById(String movieId) {
        MoviePO moviePO = reviewDataService.findMovieByMovieId(movieId);

        return new MovieVO(movieId, moviePO.getName(), "2017-03-01", null);
    }

    /**
     * 根据电影 movieId 查找评价分布 (Amazon)
     *
     * @param movieId 电影ID
     * @return ScoreDistributionVO
     */
    public ScoreDistributionVO findScoreDistributionByMovieIdFromAmazon(String movieId, String source) {
        getReviewPOList(movieId, source);
        return findScoreDistributionByMovieId(5);
    }

    /**
     * 根据电影 movieId 查找评价分布 (Imdb)
     *
     * @param movieId 电影ID
     * @return ScoreDistributionVO
     */
    public ScoreDistributionVO findScoreDistributionByMovieIdFromIMDB(String movieId, String source) {
        getReviewPOList(movieId, source);
        return findScoreDistributionByMovieId(10);
    }

    private ScoreDistributionVO findScoreDistributionByMovieId(int count) {
        if (reviewPOList.size() == 0) {
            return null;
        }

        int[] reviewAmounts = new int[count];
        Arrays.fill(reviewAmounts, 0);

        for (int i = 0; i < reviewPOList.size(); i++) {
            if (reviewPOList.get(i).getScore() != 0) {
                reviewAmounts[(int) Math.floor(reviewPOList.get(i).getScore()) - 1]++;
            }
        }
        ScoreDistributionVO scoreDistributionVO = new ScoreDistributionVO(reviewPOList.size(), reviewAmounts);
        return scoreDistributionVO;
    }


    /**
     * 根据电影 movieId 查找每年评论数量
     *
     * @param movieId 电影ID
     * @return ReviewCountYearVO
     */
    public ReviewCountVO[] findYearCountByMovieId(String movieId, String startYear, String endYear) {
        getReviewPOList(movieId, "All");

        DateUtil dateUtil = new YearDateUtil();
        DateChecker dateChecker = new YearDateChecker(startYear, endYear);
        DateFormatter dateFormatter = new YearDateFormatter();
        commonReviewCountVOGetter = new CommonReviewCountVOGetter(reviewPOList, startYear, endYear, dateUtil, dateChecker, dateFormatter);

        return commonReviewCountVOGetter.getReviewCountVOs();
    }


    /**
     * 根据电影 id 查找每月评论数量
     *
     * @param movieId    电影ID
     * @param startMonth eg. 2017-01
     * @param endMonth   eg. 2017-03
     * @return ReviewCountMonthVO
     */
    public ReviewCountVO[] findMonthCountByMovieId(String movieId, String startMonth, String endMonth) {
        getReviewPOList(movieId, "All");

        DateUtil dateUtil = new MonthDateUtil();
        DateChecker dateChecker = new MonthDateChecker(startMonth, endMonth);
        DateFormatter dateFormatter = new MonthDateFormatter();
        commonReviewCountVOGetter = new CommonReviewCountVOGetter(reviewPOList, startMonth, endMonth, dateUtil, dateChecker, dateFormatter);

        return commonReviewCountVOGetter.getReviewCountVOs();
    }

    /**
     * 根据电影 id 和起始时间和结束时间查找每日评论数量
     *
     * @param movieId   电影ID
     * @param startDate eg. 2017-01-12
     * @param endDate   eg. 2017-02-03
     * @return
     */
    public ReviewCountVO[] findDayCountByMovieId(String movieId, String startDate, String endDate) {
        getReviewPOList(movieId, "All");

        DateUtil dateUtil = new DayDateUtil();
        DateChecker dateChecker = new DayDateChecker(startDate, endDate);
        DateFormatter dateFormatter = new DayDateFormatter();
        commonReviewCountVOGetter = new CommonReviewCountVOGetter(reviewPOList, startDate, endDate, dateUtil, dateChecker, dateFormatter);

        return commonReviewCountVOGetter.getReviewCountVOs();
    }


    public WordVO findWordsByMovieId(String movieId) {
        WordPO wordPO = reviewDataService.findWordsByMovieId(movieId);
        //如果是错误的movie id
        if (wordPO == null) {
            return null;
        }
        return new WordVO(wordPO.getTopWords());
    }

    //迭代二

    public PageVO<MovieVO> findMoviesByKeywordInPage(String keyword, int page) {
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
    private List<MovieVO> moviePoListToVOList(List<MoviePO> poList) {
        List<MovieVO> newResults = new ArrayList<>();
        if (poList == null) {
            newResults = Collections.EMPTY_LIST;
        } else {
            for (MoviePO moviePO : poList) {
                MovieVO movieVO = new MovieVO(moviePO.getId(), moviePO.getName(), moviePO.getDuration(), moviePO.getGenre(), moviePO.getReleaseDate(), moviePO.getCountry(), moviePO.getLanguage(), moviePO.getPlot(), moviePO.getDirector(), moviePO.getWriters(), moviePO.getActors(), moviePO.getRating());
                newResults.add(movieVO);
            }
        }
        return newResults;
    }

    public MovieStatisticsVO findMovieStatisticsVOByMovieId(String movieId) {
        getReviewPOList(movieId, "All");

        if (reviewPOList.size() == 0) {
            return null;
        }

        double scoreSum = 0;

        //计算评分均值
        for (int i = 0; i < reviewPOList.size(); i++) {
            scoreSum = scoreSum + reviewPOList.get(i).getScore();
        }
        double averageScore = scoreSum / reviewPOList.size();

        //第一条评论日期和最后一条评论日期
        TreeSet<LocalDate> dates = new TreeSet<>();
        for (ReviewPO reviewPO : reviewPOList) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate();
            dates.add(date);
        }
        String firstReviewDate = dates.first().toString();
        String lastReviewDate = dates.last().toString();

        int amazonSize = getReviewPOList(movieId, "Amazon").size();
        int imdbSize = getReviewPOList(movieId, "Imdb").size();
        return new MovieStatisticsVO(amazonSize, imdbSize, averageScore, firstReviewDate, lastReviewDate);
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
                ReviewVO reviewVO = new ReviewVO(null, reviewPO.getUserId(), reviewPO.getUserName(), reviewPO.getHelpfulness(), reviewPO.getScore(), Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate(),
                        reviewPO.getSummary(), reviewPO.getText());
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
        getReviewPOList(Id, "All");

        DateChecker dateChecker = new YearDateChecker(startYear, endYear);
        DateFormatter dateFormatter = new YearDateFormatter();
        DateUtil dateUtil = new YearDateUtil();

        commonScoreDateVOGetter = new CommonScoreDateVOGetter(reviewPOList, startYear, endYear, dateChecker, dateFormatter, dateUtil);
        return commonScoreDateVOGetter.getScoreDateVO();
    }

    public ScoreDateVO findScoreDateByMonth(String Id, String startMonth, String endMonth) {
        getReviewPOList(Id, "All");

        DateChecker dateChecker = new MonthDateChecker(startMonth, endMonth);
        DateFormatter dateFormatter = new MonthDateFormatter();
        DateUtil dateUtil = new MonthDateUtil();

        commonScoreDateVOGetter = new CommonScoreDateVOGetter(reviewPOList, startMonth, endMonth, dateChecker, dateFormatter, dateUtil);
        return commonScoreDateVOGetter.getScoreDateVO();
    }

    public ScoreDateVO findScoreDateByDay(String Id, String startDate, String endDate) {
        getReviewPOList(Id, "All");

        DateChecker dateChecker = new DayDateChecker(startDate, endDate);
        DateFormatter dateFormatter = new DayDateFormatter();
        DateUtil dateUtil = new DayDateUtil();

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

    public  BoxPlotVO getBoxPlotVOFromAmazon(String movieId, String source) {
        getReviewPOList(movieId, source);
        return getBoxPlotVO(5);
    }

    public  BoxPlotVO getBoxPlotVOFromImdb(String movieId, String source) {
        getReviewPOList(movieId, source);
        return getBoxPlotVO(10);
    }

    private BoxPlotVO getBoxPlotVO(int maxScore){
        List<Integer> allScores = new ArrayList<>();
        for (ReviewPO reviewPO: reviewPOList){
            int score = reviewPO.getScore();
            if (maxScore == 5){
                score = score/2;
            }
            allScores.add(score);
        }

        Collections.sort(allScores);
        int size = allScores.size();

        //计算Q1,Q2,Q3,下边缘和上边缘
        double Q1 = calNum((size+1)*1.0/4, allScores);
        double Q2 = calNum((size+1)*2.0/4, allScores);
        double Q3 = calNum((size+1)*3.0/4, allScores);
        double IQR = Q3-Q1;
        double upper = Q3+1.5*IQR;
        double lower = Q1-1.5*IQR;
        List<Double> quartiles = new ArrayList<>();
        quartiles.addAll(Arrays.asList(lower, Q1, Q2, Q3, upper));

        //计算离群点
        List<Integer> outerliers = new ArrayList<>();
        for (int score: allScores){
            if (score<lower || score> upper){
                outerliers.add(score);
            }
        }

        return new BoxPlotVO(maxScore, 0, quartiles, outerliers);
    }

    private double calNum(Double d, List<Integer> scores){
        if (d-Math.floor(d) == Math.ceil(d)-d){
            //小数位是0.5的情况
            double low = scores.get((int)Math.floor(d));
            double high = scores.get((int)Math.ceil(d));
            return (low+high)/2;
        } else {
            return scores.get((int)Math.round(d));
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
        } else if (source.equals("Imdb")) {
            if (!reviewPOLinkedHashMapForImdb.containsKey(movieId)) {
                reviewPOList = reviewDataService.findAllReviewsByMovieIdFromImdb(movieId);
                if (reviewPOList.size() != 0) {
                    reviewPOLinkedHashMapForImdb.put(movieId, reviewPOList);
                } else {
                    System.out.println("There is no reviews matching the movieId.");
                    return Collections.emptyList();
                }
            } else {
                reviewPOList = reviewPOLinkedHashMapForImdb.get(movieId);
            }
        } else {
            if (!reviewPOLinkedHashMapForAll.containsKey(movieId)) {
                reviewPOList = this.getAllReviewPOList(movieId);
                if (reviewPOList.size() != 0) {
                    reviewPOLinkedHashMapForAll.put(movieId, reviewPOList);
                } else {
                    System.out.println("There is no reviews matching the movieId.");
                    return Collections.emptyList();
                }
            } else {
                reviewPOList = reviewPOLinkedHashMapForAll.get(movieId);
            }
        }

        return reviewPOList;
    }

    private List<ReviewPO> getAllReviewPOList(String movieId) {
        List<ReviewPO> listAmazon = reviewDataService.findAllReviewsByMovieIdFromAmazon(movieId);
        List<ReviewPO> listImdb = reviewDataService.findAllReviewsByMovieIdFromImdb(movieId);
        listAmazon.addAll(listImdb);
        if (listAmazon.size() == 0) {
            return Collections.emptyList();
        }
        return listAmazon;
    }


    /**
     * 根据图片的URL返回一个Image
     *
     * @param imageUrl-图片源地址
     * @return Image
     */
    private static Image getImage(String imageUrl) {
        // 从服务器获得一个输入流(本例是指从服务器获得一个image输入流)

        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        Image result;

        if (imageUrl.equals("N/A")) {
            return null;
        }

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
}