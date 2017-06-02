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
    private double score_en;
    /**
     * 国内评分
     */
    private double score_cn;
    /**
     * 国外评论数
     */
    private int vote_en;
    /**
     * 国内评论数
     */
    private int vote_cn;
    /**
     * 票房
     */
    private double boxOffice;
    /**
     * 暂时空着的描述
     */
    private String description;

    public PredictResultBean(List<Double> params) {
        score_en = params.get(0);
        score_cn = params.get(1);
        vote_en = params.get(2).intValue();
        vote_cn = params.get(3).intValue();
        boxOffice = params.get(4);
        description = "";
    }

    public double getScore_en() {
        return score_en;
    }

    public void setScore_en(double score_en) {
        this.score_en = score_en;
    }

    public double getScore_cn() {
        return score_cn;
    }

    public void setScore_cn(double score_cn) {
        this.score_cn = score_cn;
    }

    public int getVote_en() {
        return vote_en;
    }

    public void setVote_en(int vote_en) {
        this.vote_en = vote_en;
    }

    public int getVote_cn() {
        return vote_cn;
    }

    public void setVote_cn(int vote_cn) {
        this.vote_cn = vote_cn;
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
