package moviereview.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2017/5/16.
 */
public class GenreInfo {

    private String genre;
    private List<Integer> count;
    private List<Double> score;
    private List<Integer> year;

    public GenreInfo() {
    }

    public GenreInfo(String genre, List<Integer> count, List<Double> score, List<Integer> year) {
        this.genre = genre;
        this.count = count;
        this.score = score;
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Integer> getCount() {
        return count;
    }

    public void setCount(List<Integer> count) {
        this.count = count;
    }

    public List<Double> getScore() {
        return score;
    }

    public void setScore(List<Double> score) {
        this.score = score;
    }

    public List<Integer> getYear() {
        return year;
    }

    public void setYear(List<Integer> year) {
        this.year = year;
    }
}
