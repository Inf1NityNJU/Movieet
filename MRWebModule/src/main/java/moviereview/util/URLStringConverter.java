package moviereview.util;

/**
 * Created by Kray on 2017/5/17.
 */
public class URLStringConverter {

    public static String convertToURLString(String normalString) {
        return normalString.replaceAll(" ", "\\+");
    }

    public static String convertToNormalString(String urlString) {
        return urlString.replaceAll("\\+", " ");
    }
}
