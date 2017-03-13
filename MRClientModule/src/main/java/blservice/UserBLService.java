package blservice;

import vo.ReviewWordsVO;

/**
 * Created by vivian on 2017/3/13.
 */
public interface UserBLService {
    /**
     * 根据 userId 获得评论文字长度分布
     *
     * @param userId 用户ID
     * @return ReviewWordsVO
     */
    public ReviewWordsVO getReviewWordsVO(String userId);
}
