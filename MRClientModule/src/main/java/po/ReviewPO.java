package po;

import java.lang.reflect.Field;

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
