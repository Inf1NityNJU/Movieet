package data;

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
public class ReviewDataFromJsonServiceImpl {
    /**
     * URL的起始部分
     */
    private static final String COMMON_URL = "http://123.206.185.186:8080/MovieReview/api/review";
    /**
     * 用于读取url的reader
     */
    private BufferedReader urlReader;

    public ReviewDataFromJsonServiceImpl() {

    }

    public List<ReviewPO> findReviewByMovieId(String productId) {
        return GsonUtil.paeseJsonAsList(readJsonFromUrl(COMMON_URL + "/movie/" + productId), ReviewPO[].class);
    }

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

    private BufferedReader setUpReader(String url) {
        try {
            return new BufferedReader(
                    new InputStreamReader(
                            new URL(url).openStream(), Charset.forName("UTF-8")
                    ));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //System.err.println("set up are done");
        }
        assert false : "we should't get here!";
        return null;
    }
}
