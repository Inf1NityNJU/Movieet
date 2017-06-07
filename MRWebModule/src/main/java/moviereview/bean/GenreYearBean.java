package moviereview.bean;

import java.util.List;

/**
 * Created by vivian on 2017/6/7.
 * 某类型随着年份的所占比和平均分
 */
public class GenreYearBean {
    /**
     * 年份，1970-2017
     */
    private List<Integer> years;

    /**
     * 每年某类型的占比
     */
    private List<Double> count;

    /**
     * 每年某类型的平均分
     */
    private List<Double> score;

    public GenreYearBean() {
        for (int i=1970;i<=2017;i++){
            this.years.add(i);
        }
    }

    public GenreYearBean(List<Double> count, List<Double> score) {
        this.count = count;
        this.score = score;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public List<Double> getCount() {
        return count;
    }

    public void setCount(List<Double> count) {
        this.count = count;
    }

    public List<Double> getScore() {
        return score;
    }

    public void setScore(List<Double> score) {
        this.score = score;
    }
}
