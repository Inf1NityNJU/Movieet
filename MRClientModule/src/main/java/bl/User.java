package bl;

import data.DataServiceFactory;
import dataservice.ReviewDataService;
import javafx.scene.image.Image;
import po.PagePO;
import po.ReviewPO;
import po.WordPO;
import util.LimitedHashMap;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by vivian on 2017/3/9.
 */
class User {
    private ReviewDataService reviewDataService = DataServiceFactory.getJsonService();
    //            private ReviewDataService reviewDataService = new ReviewDataServiceStub();
    private List<ReviewPO> reviewPOList;
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMap = new LimitedHashMap<>(10);
    private CommonReviewCountVOGetter commonReviewCountVOGetter;

    public UserVO findUserById(String userId) {
        getReviewPOList(userId);

        if (reviewPOList.size() == 0) {
            return null;
        }

        TreeSet<LocalDate> dates = new TreeSet<>();
        for (ReviewPO reviewPO : reviewPOList) {
            LocalDate date =
                    Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate();
            dates.add(date);
        }
        String firstReviewDate = dates.first().toString();
        String lastReviewDate = dates.last().toString();

        ReviewPO reviewPO = reviewPOList.get(0);
        return new UserVO(userId, reviewPOList.get(0).getProfileName(), getImage(reviewPO.getAvatar()), reviewPOList.size(), firstReviewDate, lastReviewDate);
    }

    /**
     * 根据 userId 获得评论文字长度分布
     *
     * @param userId 用户ID
     * @return ReviewWordsVO
     */
    public ReviewWordsVO getReviewWordsLengthVO(String userId) {
        getReviewPOList(userId);

        //横坐标
        int maxWords = 200;
        int step = 20;
        int size = maxWords / step + 1;
        List<String> keys = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            keys.add(i * step + "");
        }

        //纵坐标
        List<Integer> reviewAmounts = new ArrayList<>(keys.size());
        for (int i = 0; i < size; i++) {
            reviewAmounts.add(0);
        }
        for (ReviewPO reviewPO : reviewPOList) {
            String review = reviewPO.getText();
            if (review != null && review.trim().length() != 0) {
                int count = 1;
                for (int i = 0; i < review.length(); i++) {
                    if (review.charAt(i) == ' ' && i > 0 && review.charAt(i - 1) != ' ') {
                        count++;
                    }
                }
                int words = count;
                int index = words / 20;
                index = index > size - 1 ? size - 1 : index;
                reviewAmounts.set(index, reviewAmounts.get(index) + 1);
            }
        }
        return new ReviewWordsVO(keys, reviewAmounts);
    }

    public ReviewCountVO[] findYearCountByUserId(String userId, String startYear, String endYear) {
        getReviewPOList(userId);

        bl.date.DateUtil dateUtil = new bl.date.YearDateUtil();
        bl.date.DateChecker dateChecker = new bl.date.YearDateChecker(startYear, endYear);
        bl.date.DateFormatter dateFormatter = new bl.date.YearDateFormatter();
        commonReviewCountVOGetter = new CommonReviewCountVOGetter(reviewPOList, startYear, endYear, dateUtil, dateChecker, dateFormatter, 5);

        return commonReviewCountVOGetter.getReviewCountVOs();
    }

    public ReviewCountVO[] findMonthCountByUserId(String userId, String startMonth, String endMonth) {
        getReviewPOList(userId);

        bl.date.DateUtil dateUtil = new bl.date.MonthDateUtil();
        bl.date.DateChecker dateChecker = new bl.date.MonthDateChecker(startMonth, endMonth);
        bl.date.DateFormatter dateFormatter = new bl.date.MonthDateFormatter();
        commonReviewCountVOGetter = new CommonReviewCountVOGetter(reviewPOList, startMonth, endMonth, dateUtil, dateChecker, dateFormatter, 5);

        return commonReviewCountVOGetter.getReviewCountVOs();
    }

    public ReviewCountVO[] findDayCountByUserId(String userId, String startDate, String endDate) {
        getReviewPOList(userId);

        bl.date.DateUtil dateUtil = new bl.date.DayDateUtil();
        bl.date.DateChecker dateChecker = new bl.date.DayDateChecker(startDate, endDate);
        bl.date.DateFormatter dateFormatter = new bl.date.DayDateFormatter();
        commonReviewCountVOGetter = new CommonReviewCountVOGetter(reviewPOList, startDate, endDate, dateUtil, dateChecker, dateFormatter, 5);

        return commonReviewCountVOGetter.getReviewCountVOs();
    }

    public WordVO findWordsByUserId(String userId) {
        WordPO wordPO = reviewDataService.findWordsByUserId(userId);
        //如果是错误的movie id
        if (wordPO == null) {
            return null;
        }
        return new WordVO(wordPO.getTopWords());
    }

    //迭代二
    public PageVO<ReviewVO> findReviewsByUserIdInPage(String movieId, ReviewSortType reviewSortType, int page) {
        PagePO<ReviewPO> pagePO = reviewDataService.findReviewsByUserIdInPage(movieId, reviewSortType, page);
        List<ReviewPO> results = pagePO.getResult();
        List<ReviewVO> newResults = new ArrayList<>();
        if (results == null) {
            newResults = Collections.EMPTY_LIST;
        } else {
            for (int i = 0; i < results.size(); i++) {
                ReviewPO reviewPO = results.get(i);
                ReviewVO reviewVO = new ReviewVO(getImage(reviewPO.getAvatar()), reviewPO.getUserId(), reviewPO.getProfileName(), reviewPO.getHelpfulness(), reviewPO.getScore(), Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate(),
                        reviewPO.getSummary(), reviewPO.getText());
                newResults.add(reviewVO);
            }
        }
        return new PageVO<ReviewVO>(pagePO.getPageNo(), (pagePO.getTotalCount() + pagePO.getPageSize() - 1) / pagePO.getPageSize(), newResults);
    }

    private List<ReviewPO> getReviewPOList(String userId) {
        if (!reviewPOLinkedHashMap.containsKey(userId)) {
            reviewPOList = reviewDataService.findReviewsByUserId(userId);
            if (reviewPOList.size() != 0) {
                reviewPOLinkedHashMap.put(userId, reviewPOList);
            } else {
                System.out.println("There is no reviews matching the userId.");
                return Collections.emptyList();
            }
        } else {
            reviewPOList = reviewPOLinkedHashMap.get(userId);
        }
        return reviewPOList;
    }

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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result = new Image(inputStream);
        return result;
    }

}
