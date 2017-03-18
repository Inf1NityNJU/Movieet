package bl;

import org.junit.Test;
import vo.ReviewCountVO;
import vo.ReviewWordsVO;
import vo.UserVO;
import vo.WordVO;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by vivian on 2017/3/11.
 */
public class UserTest {
    private User user = new User();

    @Test
    public void testGetUserVO() {
        UserVO userVOExpected = new UserVO("A2582KMXLI2P06", "B. E Jackson 6", 6, "2011-05-25", "2011-06-01");
        UserVO userVOActual = user.findUserById("A2582KMXLI2P06");
        assertEquals(userVOExpected, userVOActual);
    }

    @Test
    public void testGetReviewWords() {
        String[] keys = {"20", "40", "60", "80", "100", "120", "140", "160", "180", "200", "200+"};
        Integer[] reviewAmounts = {0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0};
        ReviewWordsVO reviewWordsVOExpected = new ReviewWordsVO(Arrays.asList(keys), Arrays.asList(reviewAmounts));
        ReviewWordsVO reviewWordsVOAcutual = user.getReviewWordsLengthVO("A195ZVQGHL0IA");
        assertEquals(reviewWordsVOExpected, reviewWordsVOAcutual);
    }

    @Test
    public void testFindYearCountByMovieId() {
        String[] keys = {"2011"};
        Integer[] reviewAmounts = {6};
        Integer[] reviewAmounts1 = {1};
        Integer[] reviewAmounts2 = {1};
        Integer[] reviewAmounts3 = {1};
        Integer[] reviewAmounts4 = {3};
        Integer[] reviewAmounts5 = {0};
        ReviewCountVO reviewCountVO = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts));
        ReviewCountVO reviewCountVO1 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts1));
        ReviewCountVO reviewCountVO2 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts2));
        ReviewCountVO reviewCountVO3 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts3));
        ReviewCountVO reviewCountVO4 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts4));
        ReviewCountVO reviewCountVO5 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts5));
        ReviewCountVO[] reviewCountVOsExpected = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
        ReviewCountVO[] reviewCountVOsActual = user.findYearCountByUserId("B000I5XDV1", "2011", "2012");
        for (int i = 0; i < reviewCountVOsActual.length; i++) {
            System.out.println(reviewCountVOsExpected[i].toString());
            System.out.println(reviewCountVOsActual[i].toString());
            assertEquals(reviewCountVOsExpected[i], reviewCountVOsActual[i]);
        }
    }


    @Test
    public void testFindMonthCountByUserId() {
        String[] keys = {"2011-05", "2011-06"};
        Integer[] reviewAmounts = {5, 1};
        Integer[] reviewAmounts1 = {1, 0};
        Integer[] reviewAmounts2 = {0, 1};
        Integer[] reviewAmounts3 = {1, 0};
        Integer[] reviewAmounts4 = {3, 0};
        Integer[] reviewAmounts5 = {0, 0};
        ReviewCountVO reviewCountVO = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts));
        ReviewCountVO reviewCountVO1 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts1));
        ReviewCountVO reviewCountVO2 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts2));
        ReviewCountVO reviewCountVO3 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts3));
        ReviewCountVO reviewCountVO4 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts4));
        ReviewCountVO reviewCountVO5 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts5));
        ReviewCountVO[] reviewCountVOsExpected = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
        ReviewCountVO[] reviewCountVOsActual = user.findMonthCountByUserId("B000I5XDV1", "2011-05", "2012-03");
        for (int i = 0; i < reviewCountVOsActual.length; i++) {
            System.out.println(reviewCountVOsExpected[i].toString());
            System.out.println(reviewCountVOsActual[i].toString());
            assertEquals(reviewCountVOsExpected[i], reviewCountVOsActual[i]);
        }
    }

    @Test
    public void testFindDayCountByUserId() {
        String[] keys = {"2011-05-25", "2011-05-26", "2011-05-27", "2011-05-28", "2011-05-29", "2011-05-30", "2011-05-31", "2011-06-01"};
        Integer[] reviewAmounts = {1, 1, 1, 2, 0, 0, 0, 1};
        Integer[] reviewAmounts1 = {0, 1, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts2 = {0, 0, 0, 0, 0, 0, 0, 1};
        Integer[] reviewAmounts3 = {0, 0, 0, 1, 0, 0, 0, 0};
        Integer[] reviewAmounts4 = {1, 0, 1, 1, 0, 0, 0, 0};
        Integer[] reviewAmounts5 = {0, 0, 0, 0, 0, 0, 0, 0};
        ReviewCountVO reviewCountVO = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts));
        ReviewCountVO reviewCountVO1 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts1));
        ReviewCountVO reviewCountVO2 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts2));
        ReviewCountVO reviewCountVO3 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts3));
        ReviewCountVO reviewCountVO4 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts4));
        ReviewCountVO reviewCountVO5 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts5));
        ReviewCountVO[] reviewCountVOsExpected = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
        ReviewCountVO[] reviewCountVOsActual = user.findDayCountByUserId("B000I5XDV1", "2011-05-25", "2016-06-01");
        for (int i = 0; i < reviewCountVOsActual.length; i++) {
            assertEquals(reviewCountVOsExpected[i], reviewCountVOsActual[i]);
        }
    }

    @Test
    public void testFindYearCountByUserId2() {
        String[] keys = {"2010", "2011", "2012", "2013"};
        Integer[] reviewAmounts = {0, 6, 0, 0};
        Integer[] reviewAmounts1 = {0, 1, 0, 0};
        Integer[] reviewAmounts2 = {0, 1, 0, 0};
        Integer[] reviewAmounts3 = {0, 1, 0, 0};
        Integer[] reviewAmounts4 = {0, 3, 0, 0};
        Integer[] reviewAmounts5 = {0, 0, 0, 0};
        ReviewCountVO reviewCountVO = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts));
        ReviewCountVO reviewCountVO1 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts1));
        ReviewCountVO reviewCountVO2 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts2));
        ReviewCountVO reviewCountVO3 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts3));
        ReviewCountVO reviewCountVO4 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts4));
        ReviewCountVO reviewCountVO5 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts5));
        ReviewCountVO[] reviewCountVOsExpected = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
        ReviewCountVO[] reviewCountVOsActual = user.findYearCountByUserId("B000I5XDV1", "2010", "2013");
        for (int i = 0; i < reviewCountVOsActual.length; i++) {
            assertEquals(reviewCountVOsExpected[i], reviewCountVOsActual[i]);
        }
    }

    @Test
    public void testFindMonthCountByUserId2() {
        String[] keys = {"2009-07", "2009-08", "2009-09", "2009-10", "2009-11", "2009-12", "2010-01", "2010-02", "2010-03", "2010-04", "2010-05", "2010-06", "2010-07", "2010-08", "2010-09", "2010-10", "2010-11", "2010-12",
                "2011-01", "2011-02", "2011-03", "2011-04", "2011-05", "2011-06", "2011-07", "2011-08", "2011-09", "2011-10", "2011-11", "2011-12",
                "2012-01", "2012-02", "2012-03", "2012-04"};
//        String[] keys = {"2011-05", "2011-06", "2011-07", "2011-08", "2011-09", "2011-10", "2011-11", "2011-12",
//                "2012-01", "2012-02", "2012-03", "2012-04"};
        Integer[] reviewAmounts = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts4 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts5 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        Integer[] reviewAmounts = {5, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        Integer[] reviewAmounts1 = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        Integer[] reviewAmounts2 = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        Integer[] reviewAmounts3 = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        Integer[] reviewAmounts4 = {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        Integer[] reviewAmounts5 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ReviewCountVO reviewCountVO = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts));
        ReviewCountVO reviewCountVO1 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts1));
        ReviewCountVO reviewCountVO2 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts2));
        ReviewCountVO reviewCountVO3 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts3));
        ReviewCountVO reviewCountVO4 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts4));
        ReviewCountVO reviewCountVO5 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts5));
        ReviewCountVO[] reviewCountVOsExpected = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
        ReviewCountVO[] reviewCountVOsActual = user.findMonthCountByUserId("ANGH6401L9Y0T", "2009-07", "2012-04");
        for (int i = 0; i < reviewCountVOsActual.length; i++) {
            assertEquals(reviewCountVOsExpected[i], reviewCountVOsActual[i]);
        }
    }

    @Test
    public void testFindMonthCountByUserId3() {
        String[] keys = {"2011-05", "2011-06", "2011-07", "2011-08", "2011-09", "2011-10", "2011-11", "2011-12",
                "2012-01", "2012-02", "2012-03", "2012-04"};
//        String[] keys = {"2011-05", "2011-06", "2011-07", "2011-08", "2011-09", "2011-10", "2011-11", "2011-12",
//                "2012-01", "2012-02", "2012-03", "2012-04"};
        Integer[] reviewAmounts = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts1 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts2 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts3 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts4 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts5 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        Integer[] reviewAmounts = {5, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        Integer[] reviewAmounts1 = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        Integer[] reviewAmounts2 = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        Integer[] reviewAmounts3 = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        Integer[] reviewAmounts4 = {3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        Integer[] reviewAmounts5 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ReviewCountVO reviewCountVO = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts));
        ReviewCountVO reviewCountVO1 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts1));
        ReviewCountVO reviewCountVO2 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts2));
        ReviewCountVO reviewCountVO3 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts3));
        ReviewCountVO reviewCountVO4 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts4));
        ReviewCountVO reviewCountVO5 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts5));
        ReviewCountVO[] reviewCountVOsExpected = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
        ReviewCountVO[] reviewCountVOsActual = user.findMonthCountByUserId("ANGH6401L9Y0T", "2011-05", "2012-04");
        for (int i = 0; i < reviewCountVOsActual.length; i++) {
            assertEquals(reviewCountVOsExpected[i], reviewCountVOsActual[i]);
        }
    }

    @Test
    public void testFindDayCountByUserId2() {
        String[] keys = {"2011-05-25", "2011-05-26", "2011-05-27", "2011-05-28", "2011-05-29", "2011-05-30", "2011-05-31", "2011-06-01", "2011-06-02", "2011-06-03", "2011-06-04"};
        Integer[] reviewAmounts = {1, 1, 1, 2, 0, 0, 0, 1, 0, 0, 0};
        Integer[] reviewAmounts1 = {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts2 = {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0};
        Integer[] reviewAmounts3 = {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts4 = {1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0};
        Integer[] reviewAmounts5 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ReviewCountVO reviewCountVO = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts));
        ReviewCountVO reviewCountVO1 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts1));
        ReviewCountVO reviewCountVO2 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts2));
        ReviewCountVO reviewCountVO3 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts3));
        ReviewCountVO reviewCountVO4 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts4));
        ReviewCountVO reviewCountVO5 = new ReviewCountVO(Arrays.asList(keys), Arrays.asList(reviewAmounts5));
        ReviewCountVO[] reviewCountVOsExpected = {reviewCountVO, reviewCountVO1, reviewCountVO2, reviewCountVO3, reviewCountVO4, reviewCountVO5};
        ReviewCountVO[] reviewCountVOsActual = user.findDayCountByUserId("ANGH6401L9Y0T", "2011-05-25", "2011-06-04");
        for (int i = 0; i < reviewCountVOsActual.length; i++) {
            assertEquals(reviewCountVOsExpected[i], reviewCountVOsActual[i]);
        }
    }

    @Test
    public void findWordByUserId() {
        WordVO wordVO = user.findWordsByUserId("A11YJS79DZD7D9");
        assertEquals(10, wordVO.getTopWords().size());
    }

    @Test
    public void findWordByUserId2() {
        WordVO wordVO = user.findWordsByUserId("2");
        assertEquals(null, wordVO);
    }

    @Test
    public void findWordByUserId3() {
        WordVO wordVO = user.findWordsByUserId("");
        assertEquals(null, wordVO);
    }
}
