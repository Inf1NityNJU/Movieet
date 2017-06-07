package moviereview.bean;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by SilverNarcissus on 2017/5/31.
 */
public class PredictResultBean {
    /**
     * 国外评分
     */
    private double scoreFR;
    /**
     * 国内评分
     */
    private double scoreCN;
    /**
     * 国外评论数
     */
    private int voteFR;
    /**
     * 国内评论数
     */
    private int voteCN;
    /**
     * 票房
     */
    private double boxOffice;
    /**
     * 暂时空着的描述
     */
    private String description;

    public PredictResultBean(List<Double> params) {
        scoreFR = params.get(0);
        scoreCN = params.get(1);
        voteFR = params.get(2).intValue();
        voteCN = params.get(3).intValue();
        boxOffice = params.get(4);
        description = "";
    }

    public double getScoreFR() {
        return scoreFR;
    }

    public void setScoreFR(double scoreFR) {
        this.scoreFR = scoreFR;
    }

    public double getScoreCN() {
        return scoreCN;
    }

    public void setScoreCN(double scoreCN) {
        this.scoreCN = scoreCN;
    }

    public int getVoteFR() {
        return voteFR;
    }

    public void setVoteFR(int voteFR) {
        this.voteFR = voteFR;
    }

    public int getVoteCN() {
        return voteCN;
    }

    public void setVoteCN(int voteCN) {
        this.voteCN = voteCN;
    }

    public double getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(double boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
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
