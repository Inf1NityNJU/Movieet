package vo;

/**
 * Created by vivian on 2017/3/3.
 */
public class ScoreDistributionVO {

    /**
     * 评分总数
     */
    int totalAmount;

    /**
     * 各级评分数量
     * size = 5
     * [0]: 1分数量
     * [1]: 2分数量
     * [2]: 3分数量
     * [3]: 4分数量
     * [4]: 5分数量
     */
    int[] reviewAmounts;
}
