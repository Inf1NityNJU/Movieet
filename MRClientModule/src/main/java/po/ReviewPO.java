package po;

/**
 * Created by SilverNarcissus on 2017/3/3.
 */
public class ReviewPO {
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
    public ReviewPO(String movieId, String userId, String profileName, String helpfulness, int score, long time, String summary, String text) {
        this.movieId = movieId;
        this.userId = userId;
        this.profileName = profileName;
        this.helpfulness = helpfulness;
        this.score = score;
        this.time = time;
        this.summary = summary;
        this.text = text;
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
}
