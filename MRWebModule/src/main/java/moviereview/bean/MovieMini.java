package moviereview.bean;

import moviereview.model.Movie;
import moviereview.model.ReleaseDate;

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
    private List<String> releaseDateIDs = new ArrayList<>();


    public MovieMini(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.kind = movie.getKind();
        this.poster = "";

        this.rank = movie.getRank();
        this.votes = movie.getVotes();

        this.releaseDateIDs = new ArrayList<>();
        for (ReleaseDate releaseDate : movie.getReleaseDate()) {
            this.releaseDateIDs.add(releaseDate.getIddate());
        }
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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

    public List<String> getReleaseDateIDs() {
        return releaseDateIDs;
    }

    public void setReleaseDateIDs(List<String> releaseDateIDs) {
        this.releaseDateIDs = releaseDateIDs;
    }
}
