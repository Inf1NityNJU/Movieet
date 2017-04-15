package vo;

import java.util.List;

import static util.EqualJudgeHelper.judgeEqual;

/**
 * Created by vivian on 2017/3/21.
 */
public class ScoreDateVO {
    //日期列表
    public List<String> dates;

    //评分列表
    public List<Double> scores;

    public ScoreDateVO(List<String> dates, List<Double> scores) {
        this.dates = dates;
        this.scores = scores;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ScoreDateVO) {
            ScoreDateVO scoreDateVO = (ScoreDateVO) o;
            return compareData(scoreDateVO);
        }
        return false;
    }

    public boolean compareData(ScoreDateVO scoreDateVO) {
        return judgeEqual(this.dates, scoreDateVO.dates)
                && judgeEqual(this.scores, scoreDateVO.scores);
    }

    @Override
    public String toString() {
        int count = dates.size();
        String string = "*******************************\n";
        for (int i = 0; i<count;i++){
            string += dates.get(i) + " " + scores.get(i) + "\n";
        }
        string += "*******************************";
        return string;
    }
}
