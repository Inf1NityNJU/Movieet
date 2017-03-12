package service;

import bl.User;
import org.junit.Test;
import vo.ReviewWordsVO;

import static org.junit.Assert.assertEquals;

/**
 * Created by vivian on 2017/3/11.
 */
public class UserTest {
    private User user = new User();

    @Test
    public void testGetReviewWords(){
        String[] keys = {"0-20","20-40","40-60","60-80","80-100","100-120","120-140","140-160","160-180","180-200","200+"};
        int[] reviewAmounts = {0,0,7,0,0,0,0,0,0,0,0};
        ReviewWordsVO reviewWordsVOExpected = new ReviewWordsVO(keys, reviewAmounts);
        ReviewWordsVO reviewWordsVOAcutual = user.getReviewWordsVO("0001");
        assertEquals(reviewWordsVOExpected, reviewWordsVOAcutual);
    }
}
