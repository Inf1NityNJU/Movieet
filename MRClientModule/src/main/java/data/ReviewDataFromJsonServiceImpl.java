package data;

import com.google.gson.reflect.TypeToken;
import dataservice.ReviewDataService;
import po.*;
import util.MovieGenre;
import util.MovieSortType;
import util.ReviewSortType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by SilverNarcissus on 2017/3/8.
 *
 */
class ReviewDataFromJsonServiceImpl implements ReviewDataService {
    /**
     * URL的起始部分
     */
    private static final String COMMON_URL = "http://123.206.185.186:8080/MovieReview/api";

    @Override
    //review?page=0&order=date&asc=true
    //http://123.206.185.186:8080/MovieReview/api/user/A16NHMMS2S0Z3F/review?page=0&order=date&asc=true
    public PagePO<ReviewPO> findReviewsByUserId(String userId) {
        System.out.println(COMMON_URL + "/user/" + userId + "/review?page=0&order=date&asc=true");
        return GsonUtil.parseJsonInGeneric(readJsonFromUrl(COMMON_URL + "/user/" + userId + "/review?page=0&order=date&asc=true")
                , new TypeToken<PagePO<ReviewPO>>(){}.getType());
    }

    @Override
    public PagePO<ReviewPO> findReviewsByUserIdInPage(String userId, ReviewSortType sortType, int page) {
        System.out.println(COMMON_URL + "/user/" + userId + "/review?page="+page+"&order="+sortType.getOrderBy()+"&asc="+sortType.getOrder());
        return GsonUtil.parseJsonInGeneric(readJsonFromUrl(COMMON_URL + "/user/" + userId + "/review?page="+page+"&order="+sortType.getOrderBy()+"&asc="+sortType.getOrder())
                , new TypeToken<PagePO<ReviewPO>>(){}.getType());
    }


    @Override
    public MoviePO findMovieByMovieId(String productId) {
        return GsonUtil.parseJson(readJsonFromUrl(COMMON_URL + "/movie/" + productId), MoviePO.class);
    }

    @Override
    public WordPO findWordsByMovieId(String movieId) {
        System.out.println(COMMON_URL + "/movie/" + movieId + "/word/");
        WordPO result = new WordPO(new ArrayList<String>(
                GsonUtil.<String, Integer>parseJsonAsMap(
                        readJsonFromUrl(COMMON_URL + "/movie/" + movieId + "/word/")).
                        keySet()));
        //判断是否是错误的电影 id
        if (result.getTopWords().size() <= 2) {
            return null;
        }
        return result;
    }

    @Override
    public WordPO findWordsByUserId(String userId) {
        Map<String, Integer> resultMap = GsonUtil.<String, Integer>parseJsonAsMap(
                readJsonFromUrl(COMMON_URL + "/user/" + userId + "/word/"));

        if (resultMap == null) {
            return null;
        }
        WordPO result = new WordPO(new ArrayList<>(resultMap.keySet()));
        //判断是否是错误的电影 id
        if (result.getTopWords().size() <= 2) {
            return null;
        }
        return result;
    }

    @Override
    //xxx/api/movie/search/?keyword=test&page=1&order=date&asc=false
    public PagePO<MoviePO> findMoviesByKeywordInPage(String movieName, int page) {
        System.out.println(COMMON_URL + "/movie/search/?keyword=" + movieName
                + "&page=" + page + "&order=date&asc=false");
        return GsonUtil.parseJsonInGeneric(readJsonFromUrl(COMMON_URL + "/movie/search/?keyword=" + movieName
                        + "&page=" + page + "&order=date&asc=false")
                , new TypeToken<PagePO<MoviePO>>() {
                }.getType());
    }

    @Override
    //xxx/api/movie/B0014ERKO0/review?page=2&order=date&asc=true
    public PagePO<ReviewPO> findReviewsByMovieIdInPageFromAmazon(String productId, ReviewSortType reviewSortType, int page) {
        System.out.println(COMMON_URL + "/movie/" + productId +
                "/review?page=" + page + "&order=" + reviewSortType.getOrderBy() + "&asc=" + reviewSortType.getOrder());
        return GsonUtil.parseJsonInGeneric(readJsonFromUrl(COMMON_URL + "/movie/" + productId +
                        "/review?page=" + page + "&order=" + reviewSortType.getOrderBy() + "&asc=" + reviewSortType.getOrder())
                , new TypeToken<PagePO<ReviewPO>>() {
                }.getType());
    }

    @Override
    public PagePO<ReviewPO> findReviewsByMovieIdInPageFromImdb(String productId, ReviewSortType reviewSortType, int page) {
        System.out.println(COMMON_URL + "/movie/" + productId +
                "/imdb/review?page=" + page + "&order=" + reviewSortType.getOrderBy() + "&asc=" + reviewSortType.getOrder());
        return GsonUtil.parseJsonInGeneric(readJsonFromUrl(COMMON_URL + "/movie/" + productId +
                        "/imdb/review?page=" + page + "&order=" + reviewSortType.getOrderBy() + "&asc=" + reviewSortType.getOrder())
                , new TypeToken<PagePO<ReviewPO>>() {
                }.getType());
    }

    @Override
    //xxx/api/movie/search/?tags=action,drama&page=1&order=date&asc=false
    public PagePO<MoviePO> findMoviesByTagInPage(EnumSet<MovieGenre> tags, MovieSortType movieSortType, int page) {
        String tag = fromTagsToString(tags);
        System.out.println(COMMON_URL + "/movie/search/?tags=" + tag
                + "&page=" + page + "&order=" + movieSortType.getOrderBy() + "&asc=" + movieSortType.getOrder());
        return GsonUtil.parseJsonInGeneric(readJsonFromUrl(COMMON_URL + "/movie/search/?tags=" + tag
                        + "&page=" + page + "&order=" + movieSortType.getOrderBy() + "&asc=" + movieSortType.getOrder())
                , new TypeToken<PagePO<MoviePO>>() {
                }.getType());
    }

    /**
     * 将tags枚举转换为字符串
     * @param tags 枚举集合
     * @return 字符串
     */
    private String fromTagsToString(EnumSet<MovieGenre> tags) {
        StringBuilder tag = new StringBuilder();
        for (MovieGenre genre : tags) {
            tag.append(genre.toString().toLowerCase()).append(",");
        }
        tag.deleteCharAt(tag.length() - 1);
        return tag.toString();
    }


    @Override
    public MovieGenrePO findMovieGenre() {
        return GsonUtil.parseJson(readJsonFromUrl(COMMON_URL + "/movie/genre"), MovieGenrePO.class);
    }

    @Override
    //http://123.206.185.186:8080/MovieReview/api/movie/scoreandreview/?tags=action,drama,history
    public ScoreAndReviewAmountPO findRelationBetweenScoreAndReviewAmount(EnumSet<MovieGenre> tags) {
        String tag=fromTagsToString(tags);
        return GsonUtil.parseJson(readJsonFromUrl(COMMON_URL + "/movie/scoreandreview/?tags="+tag), ScoreAndReviewAmountPO.class);
    }

    @Override
    public boolean checkNetWork() {
        try {
            new URL("http://www.baidu.com").openStream();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<ReviewPO> findAllReviewsByMovieIdFromAmazon(String Id) {
        System.out.println(COMMON_URL+"/movie/"+Id+"/allreviews/amazon");
        return GsonUtil.parseJsonAsList(readJsonFromUrl(COMMON_URL+"/movie/"+Id+"/allreviews/amazon"),ReviewPO[].class);
    }

    @Override
    public List<ReviewPO> findAllReviewsByMovieIdFromImdb(String Id) {
        System.out.println(COMMON_URL+"/movie/"+Id+"/allreviews/imdb");
        return GsonUtil.parseJsonAsList(readJsonFromUrl(COMMON_URL+"/movie/"+Id+"/allreviews/imdb"),ReviewPO[].class);
    }


    /**
     * 从url中读取Json
     *
     * @param url 指定的url
     * @return Json字符串
     */
    private String readJsonFromUrl(String url) {
        /*
         * 用于读取url的reader
         */
        BufferedReader urlReader = setUpReader(url);
        if (urlReader == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        String line;

        try {
            while ((line = urlReader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.err.println("read are done");
        return builder.toString();
    }

    /**
     * 建立连接指定url的reader
     *
     * @param url 指定url
     * @return 连接指定url的reader
     */
    private BufferedReader setUpReader(String url) {
        try {
            return new BufferedReader(
                    new InputStreamReader(
                            new URL(url).openStream(), Charset.forName("UTF-8")
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
