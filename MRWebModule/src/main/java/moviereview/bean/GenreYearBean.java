package moviereview.bean;

import moviereview.model.GenreInYear;

/**
 * Created by vivian on 2017/6/7.
 * 某类型随着年份的所占比和平均分
 */
public class GenreYearBean {
    /**
     * 年份，1970-2017
     */
    private Integer year;

    /**
     * 每年某类型的占比
     */
    private Double count;

    /**
     * 每年某类型的平均分
     */
    private Double score;

    public GenreYearBean() {
    }

    public GenreYearBean(GenreInYear genreInYear) {
        this.year = genreInYear.getYear();
        this.count = genreInYear.getCount();
        this.score = genreInYear.getScore();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
