package data;

import dataservice.ReviewDataService;
import po.MoviePO;
import po.ReviewPO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by SilverNarcissus on 2017/3/8.
 */
public class ReviewDataFromJsonServiceImpl implements ReviewDataService {
    /**
     * URL的起始部分
     */
    private static final String COMMON_URL = "http://123.206.185.186:8080/MovieReview/api";
    /**
     * 用于读取url的reader
     */
    private BufferedReader urlReader;

    @Override
    public List<ReviewPO> findReviewsByUserId(String userId) {
        return GsonUtil.paeseJsonAsList(readJsonFromUrl(COMMON_URL + "/review/user/" + userId), ReviewPO[].class);
    }

    @Override
    public MoviePO findMovieByMovieId(String productId) {
        return GsonUtil.parseJson(readJsonFromUrl(COMMON_URL + "/movie/" + productId), MoviePO.class);
    }

    @Override
    public List<ReviewPO> findReviewsByMovieId(String productId) {
        return GsonUtil.paeseJsonAsList(readJsonFromUrl(COMMON_URL + "/review/movie/" + productId), ReviewPO[].class);
    }

    /**
     * 从url中读取Json
     *
     * @param url 指定的url
     * @return Json字符串
     */
    private String readJsonFromUrl(String url) {
        urlReader = setUpReader(url);
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
        assert false : "we should't get here!";
        return null;
    }
}
