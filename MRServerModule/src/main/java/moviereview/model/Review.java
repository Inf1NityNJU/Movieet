package moviereview.model;

import moviereview.util.DateUtil;

import java.util.Comparator;
import java.util.Objects;

/**
 * Created by Kray on 2017/3/8.
 */
public class Review {

    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 电影序列号
     */
    private String movieId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名称
     */
    private String profileName;
    /**
     *
     */
    private String helpfulness;
    /**
     * 电影评分
     */
    private int score;
    /**
     * 评论时间
     */
    private long time;
    /**
     * 简评
     */
    private String summary;
    /**
     * 评价全文
     */
    private String text;

    public Review() {

    }

    public Review(String movieId, String userId, String profileName, String helpfulness, int score, long time, String summary, String text) {
        this.movieId = movieId;
        this.userId = userId;
        this.profileName = profileName;
        this.helpfulness = helpfulness;
        this.score = score * 2; //amazon, * 2
        this.time = time;
        this.summary = summary;
        this.text = text;
    }

    public Review(String movieId, String userId, String profileName, String helpfulness, int score, long time, String summary, String text, String avatar) {
        this.movieId = movieId;
        this.userId = userId;
        this.profileName = profileName;
        this.helpfulness = helpfulness;
        this.score = score * 2; //amazon, * 2
        this.time = time;
        this.summary = summary;
        this.text = text;
        this.avatar = avatar;
    }

    public Review(String movieId, ReviewIMDB reviewIMDB) {
        this.movieId = movieId;
        this.userId = "";
        this.profileName = reviewIMDB.getAuthor();
        this.helpfulness = reviewIMDB.getHelpfulness();
        this.score = Integer.parseInt(reviewIMDB.getScore().split("/")[0]);
        this.text = reviewIMDB.getContent();
        this.summary = reviewIMDB.getTitle();
        this.time = DateUtil.transformDate(reviewIMDB.getDate());
        this.userId = reviewIMDB.getUserid();
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getHelpfulness() {
        return helpfulness;
    }

    public void setHelpfulness(String helpfulness) {
        this.helpfulness = helpfulness;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return score == review.score &&
                time == review.time &&
                Objects.equals(userId, review.userId) &&
                Objects.equals(profileName, review.profileName) &&
                Objects.equals(helpfulness, review.helpfulness) &&
                Objects.equals(summary, review.summary) &&
                Objects.equals(text, review.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, profileName, helpfulness, score, time, summary, text);
    }

}