import moviereview.dao.impl.DataConst;
import moviereview.util.ShellUtil;
import org.junit.Test;

/**
 * Created by Kray on 2017/4/12.
 */
public class ReviewTest {

    @Test
    public void testIMDBReviewCount(){
        String imdbID = "tt1372710";
        try {
            System.out.println(ShellUtil.getResultOfShellFromCommand("python3 " + DataConst.PYTHON_FILE_LOCATION + "/MovieIMDBReviewCountGetter.py " + imdbID).trim());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }
    }
}
