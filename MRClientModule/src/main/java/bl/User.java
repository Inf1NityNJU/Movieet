package bl;

import datastub.ReviewDataServiceStub;
import po.ReviewPO;
import util.LimitedHashMap;
import vo.ReviewWordsVO;

import java.util.List;

/**
 * Created by vivian on 2017/3/9.
 */
public class User {
    private ReviewDataServiceStub reviewDataServiceStub = new ReviewDataServiceStub();
    private List<ReviewPO> reviewPOList;
    private static LimitedHashMap<String, List<ReviewPO>> reviewPOLinkedHashMap = new LimitedHashMap<>(10);


    /**
     * 根据 userId 获得评论文字长度分布
     *
     * @param userId 用户ID
     * @return ReviewWordsVO
     */
    public ReviewWordsVO getReviewWordsVO(String userId) {
        getReviewPOList(userId);

        //横坐标
        int maxWords = 200;
        int step = 20;
        String[] keys = new String[maxWords / step + 1];
        for (int i = 0; i < keys.length - 1; i++) {
            keys[i] = i * step + "-" + (i + 1) * step;
        }
        keys[keys.length - 1] = maxWords + "+";

        //纵坐标
        int[] reviewAmounts = new int[keys.length];
        for (ReviewPO reviewPO : reviewPOList) {
            String review = reviewPO.getText();
            if (review != null && review.replace(" ", "") != null) {
                int count = 1;
                for (int i = 0; i < review.length(); i++) {
                    if (review.charAt(i) == ' ' && i > 0 && review.charAt(i - 1) != ' ') {
                        count++;
                    }
                }
                int words = count;
//                int words = reviewPO.getWords();
                reviewAmounts[words / 20]++;
            }
        }
        return new ReviewWordsVO(keys, reviewAmounts);
    }

    private List<ReviewPO> getReviewPOList(String userId) {
        if (!reviewPOLinkedHashMap.containsKey(userId)) {
            reviewPOList = reviewDataServiceStub.findReviewsByUserId(userId);
            reviewPOLinkedHashMap.put(userId, reviewPOList);

        } else {
            reviewPOList = reviewPOLinkedHashMap.get(userId);
        }
        return reviewPOList;
    }
}
