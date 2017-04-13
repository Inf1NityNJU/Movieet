package bl;

import bl.date.*;
import data.DataServiceFactory;
import dataservice.ReviewDataService;
import po.*;
import util.LimitedHashMap;
import util.MovieGenre;
import util.MovieSortType;
import util.ReviewSortType;
import vo.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by vivian on 2017/3/4.
 */
class Movie {
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMap = new LimitedHashMap<>(10);
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
     * 根据电影 movieId 查找评价分布
     *
     * @param movieId 电影ID
     * @return ScoreDistributionVO
     */
    public ScoreDistributionVO findScoreDistributionByMovieId(String movieId) {
        getReviewPOList(movieId);

        if (reviewPOList.size() == 0) {
            return null;
        }

        int[] reviewAmounts = {0, 0, 0, 0, 0};
        for (int i = 0; i < reviewPOList.size(); i++) {
            reviewAmounts[(int) Math.floor(reviewPOList.get(i).getScore()) - 1]++;
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
        getReviewPOList(movieId);

        DateUtil dateUtil = new YearDateUtil();
        DateChecker dateChecker = new YearDateChecker();
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
        getReviewPOList(movieId);

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
        getReviewPOList(movieId);

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
        if (results.size()==0){
            return new PageVO<MovieVO>(pagePO.getPageNo(), 0, results);
        }
        return new PageVO<MovieVO>(pagePO.getPageNo(), (pagePO.getTotalCount()+pagePO.getPageSize()-1)/pagePO.getPageSize(), results);
    }

    public PageVO<MovieVO> findMoviesByTagInPage(EnumSet<MovieGenre> tag, MovieSortType movieSortType, int page) {
        PagePO<MoviePO> pagePO = reviewDataService.findMoviesByTagInPage(tag, movieSortType, page);
        List<MovieVO> results = moviePoListToVOList(pagePO.getResult());
        if (results.size()==0){
            return new PageVO<MovieVO>(pagePO.getPageNo(), 0, results);
        }
        return new PageVO<MovieVO>(pagePO.getPageNo(), (pagePO.getTotalCount()+pagePO.getPageSize()-1)/pagePO.getPageSize(), results);
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
                MovieVO movieVO = new MovieVO(moviePO.getId(), moviePO.getName(), moviePO.getDuration(), moviePO.getGenre(), moviePO.getReleaseDate(), null, moviePO.getCountry(), moviePO.getLanguage(), moviePO.getPlot(), moviePO.getDirector(), moviePO.getWriters(), moviePO.getActors());
                newResults.add(movieVO);
            }
        }
        return newResults;
    }

    public MovieStatisticsVO findMovieStatisticsVOByMovieId(String movieId) {
        getReviewPOList(movieId);

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
        return new MovieStatisticsVO(reviewPOList.size(), averageScore, firstReviewDate, lastReviewDate);
    }

    public PageVO<ReviewVO> findReviewsByMovieIdInPage(String movieId, ReviewSortType reviewSortType, int page) {
        PagePO<ReviewPO> pagePO = reviewDataService.findReviewsByMovieIdInPageFromAmazon(movieId, reviewSortType, page);
        List<ReviewPO> results = pagePO.getResult();
        List<ReviewVO> newResults = new ArrayList<>();
        if (results == null) {
            newResults = Collections.EMPTY_LIST;
        } else {
            for (int i = 0; i < results.size(); i++) {
                ReviewPO reviewPO = results.get(i);
                ReviewVO reviewVO = new ReviewVO(reviewPO.getScore(), Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate(),
                         reviewPO.getSummary(), reviewPO.getText());
                newResults.add(reviewVO);
            }
        }
        return new PageVO<ReviewVO>(pagePO.getPageNo(), (pagePO.getTotalCount()+pagePO.getPageSize()-1)/pagePO.getPageSize(), newResults);
    }

    //分类统计
    public MovieGenreVO findMovieGenre() {
        MovieGenrePO movieGenrePO = reviewDataService.findMovieGenre();
        return new MovieGenreVO(movieGenrePO.getTags(), movieGenrePO.getAmounts());
    }

    public ScoreAndReviewAmountVO findRelationBetweenScoreAndReviewAmount(EnumSet<MovieGenre> tag) {
        ScoreAndReviewAmountPO scoreAndReviewAmountPO = reviewDataService.findRelationBetweenScoreAndReviewAmount(tag);
        return new ScoreAndReviewAmountVO(scoreAndReviewAmountPO.getScores(), scoreAndReviewAmountPO.getReviewAmounts());
    }

    public ScoreDateVO findScoreDateByMonth(String Id, String startMonth, String endMonth) {
        getReviewPOList(Id);

        DateChecker dateChecker = new MonthDateChecker(startMonth, endMonth);
        DateFormatter dateFormatter = new MonthDateFormatter();
        DateUtil dateUtil = new MonthDateUtil();

        commonScoreDateVOGetter = new CommonScoreDateVOGetter(reviewPOList, startMonth, endMonth, dateChecker, dateFormatter, dateUtil);
        return commonScoreDateVOGetter.getScoreDateVO();
    }

    public ScoreDateVO findScoreDateByDay(String Id, String startDate, String endDate) {
        getReviewPOList(Id);

        DateChecker dateChecker = new DayDateChecker(startDate, endDate);
        DateFormatter dateFormatter = new DayDateFormatter();
        DateUtil dateUtil = new DayDateUtil();

        commonScoreDateVOGetter = new CommonScoreDateVOGetter(reviewPOList, startDate, endDate, dateChecker, dateFormatter, dateUtil);
        return commonScoreDateVOGetter.getScoreDateVO();
    }

    private List<ReviewPO> getReviewPOList(String movieId) {
        if (!reviewPOLinkedHashMap.containsKey(movieId)) {
            reviewPOList = reviewDataService.findReviewsByMovieId(movieId);
            if (reviewPOList.size() != 0) {
                reviewPOLinkedHashMap.put(movieId, reviewPOList);
            } else {
                System.out.println("There is no reviews matching the movieId.");
                return Collections.emptyList();
            }
        } else {
            reviewPOList = reviewPOLinkedHashMap.get(movieId);
        }
        return reviewPOList;
    }

    /**
     * 根据头像图片的URL返回一个Image
     * @param avatarUrl-头像的源地址
     * @return Image-头像
     */
//    public static Image getAvatar(String avatarUrl) {
//        // 从服务器获得一个输入流(本例是指从服务器获得一个image输入流)
//
//        InputStream inputStream = null;
//        HttpURLConnection httpURLConnection = null;
//        Image result;
//
//        try {
//            URL url = new URL(avatarUrl);
//            httpURLConnection = (HttpURLConnection) url.openConnection();
//            // 设置网络连接超时时间
//            httpURLConnection.setConnectTimeout(3000);
//            // 设置应用程序要从网络连接读取数据
//            httpURLConnection.setDoInput(true);
//
//            httpURLConnection.setRequestMethod("GET");
//            int responseCode = httpURLConnection.getResponseCode();
//            if (responseCode == 200) {
//                // 从服务器返回一个输入流
//                inputStream = httpURLConnection.getInputStream();
//
//            }
//
//        } catch (MalformedURLException e) {
//            try {
//                inputStream = new FileInputStream(Constant.localDataPath+"img/index.jpg");
//            } catch (FileNotFoundException e1) {
//                e1.printStackTrace();
//            }
//            result = new Image(inputStream);
//            return result;
//        } catch (IOException e) {
//            try {
//                inputStream = new FileInputStream(Constant.localDataPath+"img/index.jpg");
//            } catch (FileNotFoundException e1) {
//                e1.printStackTrace();
//            }
//            result = new Image(inputStream);
//            return result;
//        }
//
//        result = new Image(inputStream);
//
//        if(result == null){
//            try {
//                inputStream = new FileInputStream(Constant.localDataPath+"img/index.jpg");
//            } catch (FileNotFoundException e1) {
//                e1.printStackTrace();
//            }
//            result = new Image(inputStream);
//        }
//
//        return result;
//    }
}