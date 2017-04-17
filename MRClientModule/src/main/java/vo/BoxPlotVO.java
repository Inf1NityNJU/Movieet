package vo;

import java.util.List;

/**
 * Created by vivian on 2017/4/16.
 */
public class BoxPlotVO {
    /**
     * 最高评分
     */
    public int maxScore;

    /**
     * 最低评分
     */
    public int minScore;

    /**
     * 四分点
     */
    public List<Double> quartiles;

    /**
     * 离群点
     */
    public List<Double> outerliers;

    public BoxPlotVO(int maxScore, int minScore, List<Double> quartiles, List<Double> outerliers) {
        this.maxScore = maxScore;
        this.minScore = minScore;
        this.quartiles = quartiles;
        this.outerliers = outerliers;
    }

    @Override
    public String toString() {
        String string = "min: " + minScore + " max: " + maxScore;
        string += "\nquartiles:\n";
        for (Double quartile : quartiles) {
            string += quartile.toString() + " ";
        }
        string += "\n";
        if (outerliers != null) {
            for (Double outerlier : outerliers) {
                string += outerlier.toString() + " ";
            }
        }
        string += "\n";
        return string;
    }
}
