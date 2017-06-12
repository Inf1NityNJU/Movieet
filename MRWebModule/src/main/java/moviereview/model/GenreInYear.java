package moviereview.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by vivian on 2017/6/12.
 */
@Entity
@Table(name = "genre_in_year")
public class GenreInYear  implements Serializable {
    @Id
    private int id;

    private int genreId;
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

    public GenreInYear() {
    }

    public GenreInYear(int genreId, Integer year, Double count, Double score) {
        this.genreId = genreId;
        this.year = year;
        this.count = count;
        this.score = score;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
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
