package vo;

import java.lang.reflect.Field;
import java.util.List;

import static util.EqualJudgeHelper.judgeEqual;

/**
 * Created by vivian on 2017/3/10.
 */
public class ReviewCountVO {
    /**
     * 横坐标
     */
    List<String> keys;


    /**
     * 评论数量
     */
    List<Integer> reviewAmounts;

    public ReviewCountVO(){

    }

    public ReviewCountVO(List<String> keys, List<Integer> reviewAmounts) {
        this.keys = keys;
        this.reviewAmounts = reviewAmounts;
    }


    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<Integer> getReviewAmounts() {
        return reviewAmounts;
    }

    public void setReviewAmounts(List<Integer> reviewAmounts) {
        this.reviewAmounts = reviewAmounts;
    }

    @Override
    public int hashCode() {
        return reviewAmounts.get(0);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ReviewCountVO) {
            ReviewCountVO ReviewCountVO = (ReviewCountVO) o;
            return compareData(ReviewCountVO);
        }
        return false;
    }

    private boolean compareData(ReviewCountVO ReviewCountVO) {
        return judgeEqual(keys, ReviewCountVO.getKeys())
                && judgeEqual(reviewAmounts, ReviewCountVO.getReviewAmounts());
    }

    public String toString() {
        String lineSeparator = System.getProperty("line.separator", "\n");

        StringBuilder result = new StringBuilder();
        result.append("----------")
                .append(this.getClass().getName())
                .append("----------")
                .append(lineSeparator);
        //
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                result.append(field.getName());
                if (field.get(this) == null) {
                    result.append(": null    ");
                } else {
                    result.append(": ").append(field.get(this).toString()).append("    ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        result.append(lineSeparator).append("--------------------").append(lineSeparator);

        return result.toString();
    }
}
