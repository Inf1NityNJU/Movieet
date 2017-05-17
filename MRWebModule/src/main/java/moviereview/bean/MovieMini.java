package moviereview.bean;

import moviereview.model.Genre;
import moviereview.model.Movie;
import moviereview.model.ReleaseDate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2017/5/15.
 */
public class MovieMini {


    /**
     * 海报
     */
    private String poster;

    /**
     * 数据库里 id
     */
    private String id;

    /**
     * 电影标题
     */
    private String title;

    /**
     * 年份
     */
    private String year;

    /**
     * 种类
     */
    private String kind;

    /**
     * 投票数
     */
    private int votes;

    /**
     * 评分
     */
    private double rank;

    /**
     * 上映日期
     */
    private List<String> releaseDates = new ArrayList<>();

    /**
     * 种类
     */
    private List<String> genres = new ArrayList<>();

    public MovieMini(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.kind = movie.getKind();
        this.poster = null;

        this.rank = movie.getRank();
        this.votes = movie.getVotes();

        this.releaseDates = new ArrayList<>();
        for (ReleaseDate releaseDate : movie.getReleaseDate()) {
            this.releaseDates.add(releaseDate.getIddate());
        }

        this.genres = new ArrayList<>();
        for (Genre genre : movie.getGenre()) {
            this.genres.add(genre.getIdgenre());
        }
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        if (poster.equals("N/A")) {
            this.poster = null;
        } else {
            this.poster = poster;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public List<String> getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(List<String> releaseDates) {
        this.releaseDates = releaseDates;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
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
