package moviereview.bean;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by SilverNarcissus on 2017/6/2.
 */
public class EstimateResultBean {
    /**
     * 国外评分
     */
    private List<PlotDataBean> scoreFR;
    /**
     * 国内评分
     */
    private List<PlotDataBean> scoreCN;
    /**
     * 国外评论数
     */
    private List<PlotDataBean> votesFR;
    /**
     * 国内评论数
     */
    private List<PlotDataBean> votesCN;
    /**
     * 票房
     */
    private List<PlotDataBean> boxOffice;
    /**
     * 暂时空着的描述
     */
    private String descriptionEN;
    /**
     * 暂时空着的描述
     */
    private String descriptionCN;

    public EstimateResultBean() {
    }

    public EstimateResultBean(List<PlotDataBean> scoreFR, List<PlotDataBean> scoreCN, List<PlotDataBean> votesFR, List<PlotDataBean> votesCN, List<PlotDataBean> boxOffice) {
        this.scoreFR = scoreFR;
        this.scoreCN = scoreCN;
        this.votesFR = votesFR;
        this.votesCN = votesCN;
        this.boxOffice = boxOffice;
    }

    public List<PlotDataBean> getScoreFR() {
        return scoreFR;
    }

    public void setScoreFR(List<PlotDataBean> scoreFR) {
        this.scoreFR = scoreFR;
    }

    public List<PlotDataBean> getScoreCN() {
        return scoreCN;
    }

    public void setScoreCN(List<PlotDataBean> scoreCN) {
        this.scoreCN = scoreCN;
    }

    public List<PlotDataBean> getVotesFR() {
        return votesFR;
    }

    public void setVotesFR(List<PlotDataBean> votesFR) {
        this.votesFR = votesFR;
    }

    public List<PlotDataBean> getVotesCN() {
        return votesCN;
    }

    public void setVotesCN(List<PlotDataBean> votesCN) {
        this.votesCN = votesCN;
    }

    public List<PlotDataBean> getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(List<PlotDataBean> boxOffice) {
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
