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
    private List<PlotDataBean> score_en;
    /**
     * 国内评分
     */
    private List<PlotDataBean> score_cn;
    /**
     * 国外评论数
     */
    private List<PlotDataBean> vote_en;
    /**
     * 国内评论数
     */
    private List<PlotDataBean> vote_cn;
    /**
     * 票房
     */
    private List<PlotDataBean> boxOffice;

    public EstimateResultBean(List<PlotDataBean> score_en, List<PlotDataBean> score_cn, List<PlotDataBean> vote_en, List<PlotDataBean> vote_cn, List<PlotDataBean> boxOffice) {

        this.score_en = score_en;
        this.score_cn = score_cn;
        this.vote_en = vote_en;
        this.vote_cn = vote_cn;
        this.boxOffice = boxOffice;
    }

    public List<PlotDataBean> getScore_en() {
        return score_en;
    }

    public void setScore_en(List<PlotDataBean> score_en) {
        this.score_en = score_en;
    }

    public List<PlotDataBean> getScore_cn() {
        return score_cn;
    }

    public void setScore_cn(List<PlotDataBean> score_cn) {
        this.score_cn = score_cn;
    }

    public List<PlotDataBean> getVote_en() {
        return vote_en;
    }

    public void setVote_en(List<PlotDataBean> vote_en) {
        this.vote_en = vote_en;
    }

    public List<PlotDataBean> getVote_cn() {
        return vote_cn;
    }

    public void setVote_cn(List<PlotDataBean> vote_cn) {
        this.vote_cn = vote_cn;
    }

    public List<PlotDataBean> getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(List<PlotDataBean> boxOffice) {
        this.boxOffice = boxOffice;
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
