package moviereview.bean;

import moviereview.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2017/5/15.
 */
public class MovieFull {
    /**
     * 数据库里 id
     */
    private int id;

    /**
     * 电影标题
     */
    private String title;

    /**
     * 电影原始标题
     */
    private String originTitle;

    /**
     * 电影中午标题
     */
    private String titleCN;

    /**
     * 海报url
     */
    private String poster;

    /**
     * 类型
     */
    private List<Integer> genre = new ArrayList<>();

    /**
     * 上映日期
     */
    private String releaseDate;

    /**
     * 情节
     */
    private String plot;

    /**
     * 出版国家
     */
    private String country;

    /**
     * 电影语言
     */
    private String language;

    /**
     * 导演
     */
    private List<PeopleMini> director = new ArrayList<>();

    /**
     * 演员
     */
    private List<PeopleMini> actore = new ArrayList<>();

    /**
     * 关键词
     */
    private List<Integer> keyword = new ArrayList<>();

    /**
     * 时长
     */
    private int runtime;

    /**
     * 热度
     */
    private double popularity;

    /**
     * 票房
     */
    private int boxoffice;

    /**
     * 外国评分
     */
    private double scoreFR;

    /**
     * 外国评分人数
     */
    private int votesFR;

    /**
     * 外国评分分布
     */
    private List<Integer> distributionFR = new ArrayList<>();

    /**
     * 中国评分
     */
    private double scoreCN;

    /**
     * 中国评分人数
     */
    private int votesCN;

    /**
     * 中国评分分布
     */
    private List<Integer> distributionCN = new ArrayList<>();

    public MovieFull() {
    }

    public MovieFull(Movie movie) {
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

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        if (plot.equals("N/A")) {
            this.plot = null;
        } else {
            this.plot = plot;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginTitle() {
        return originTitle;
    }

    public void setOriginTitle(String originTitle) {
        this.originTitle = originTitle;
    }

    public String getTitleCN() {
        return titleCN;
    }

    public void setTitleCN(String titleCN) {
        this.titleCN = titleCN;
    }

    public List<Integer> getGenre() {
        return genre;
    }

    public void setGenre(List<Integer> genre) {
        this.genre = genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<PeopleMini> getDirector() {
        return director;
    }

    public void setDirector(List<PeopleMini> director) {
        this.director = director;
    }

    public List<PeopleMini> getActore() {
        return actore;
    }

    public void setActore(List<PeopleMini> actore) {
        this.actore = actore;
    }

    public List<Integer> getKeyword() {
        return keyword;
    }

    public void setKeyword(List<Integer> keyword) {
        this.keyword = keyword;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getBoxoffice() {
        return boxoffice;
    }

    public void setBoxoffice(int boxoffice) {
        this.boxoffice = boxoffice;
    }

    public double getScoreFR() {
        return scoreFR;
    }

    public void setScoreFR(double scoreFR) {
        this.scoreFR = scoreFR;
    }

    public int getVotesFR() {
        return votesFR;
    }

    public void setVotesFR(int votesFR) {
        this.votesFR = votesFR;
    }

    public List<Integer> getDistributionFR() {
        return distributionFR;
    }

    public void setDistributionFR(List<Integer> distributionFR) {
        this.distributionFR = distributionFR;
    }

    public double getScoreCN() {
        return scoreCN;
    }

    public void setScoreCN(double scoreCN) {
        this.scoreCN = scoreCN;
    }

    public int getVotesCN() {
        return votesCN;
    }

    public void setVotesCN(int votesCN) {
        this.votesCN = votesCN;
    }

    public List<Integer> getDistributionCN() {
        return distributionCN;
    }

    public void setDistributionCN(List<Integer> distributionCN) {
        this.distributionCN = distributionCN;
    }
}
