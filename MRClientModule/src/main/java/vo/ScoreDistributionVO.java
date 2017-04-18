package vo;

import java.util.List;

import static util.EqualJudgeHelper.judgeEqual;

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
     * size = 5 or 10
     */
    List<Integer> reviewAmounts;

    public ScoreDistributionVO(int totalAmount, List<Integer> reviewAmounts) {
        this.totalAmount = totalAmount;
        this.reviewAmounts = reviewAmounts;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Integer> getReviewAmounts() {
        return reviewAmounts;
    }

    public void setReviewAmounts(List<Integer> reviewAmounts) {
        this.reviewAmounts = reviewAmounts;
    }

    @Override
    public int hashCode() {
        return totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ScoreDistributionVO) {
            ScoreDistributionVO scoreDistributionVO = (ScoreDistributionVO) o;
            return compareData(scoreDistributionVO);
        }
        return false;
    }

    private boolean compareData(ScoreDistributionVO scoreDistributionVO) {
        return judgeEqual(totalAmount, scoreDistributionVO.getTotalAmount())
                && judgeEqual(reviewAmounts, scoreDistributionVO.getReviewAmounts());
    }

    @Override
    public String toString() {
        int count = reviewAmounts.size();
        String string = "*******************************\n";
        string += "total: " + totalAmount + " \n";
        for (int i = 0; i < count; i++) {
            string += i + " " + reviewAmounts.get(i) + "\n";
        }
        string += "*******************************";
        return string;
    }
}
