package moviereview.util;

/**
 * Created by Kray on 2017/5/17.
 */
public class URLStringConverter {

    public static String convertToURLString(String normalString) {
        StringBuilder sb = new StringBuilder();
        for (String s : normalString.split(" ")) {
            sb.append(s);
            sb.append("+");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    public static String convertToNormalString(String urlString) {
        StringBuilder sb = new StringBuilder();
        for (String s : urlString.split("\\+")) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }
}
