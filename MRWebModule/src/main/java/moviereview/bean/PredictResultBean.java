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
    private int votesFR;
    /**
     * 国内评论数
     */
    private int votesCN;
    /**
     * 票房
     */
    private double boxOffice;
    /**
     * 暂时空着的描述
     */
    private String descriptionEN;
    /**
     * 暂时空着的描述
     */
    private String descriptionCN;

    public PredictResultBean(List<Double> params) {
        scoreFR = params.get(0);
        scoreCN = params.get(1);
        votesFR = params.get(2).intValue();
        votesCN = params.get(3).intValue();
        boxOffice = params.get(4);
        descriptionCN = "";
        descriptionEN = "";
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

    public int getVotesFR() {
        return votesFR;
    }

    public void setVotesFR(int votesFR) {
        this.votesFR = votesFR;
    }

    public int getVotesCN() {
        return votesCN;
    }

    public void setVotesCN(int votesCN) {
        this.votesCN = votesCN;
    }

    public double getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(double boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public String getDescriptionCN() {
        return descriptionCN;
    }

    public void setDescriptionCN(String descriptionCN) {
        this.descriptionCN = descriptionCN;
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
