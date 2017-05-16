package moviereview.model;

/**
 * Created by Kray on 2017/3/31.
 */
public class ReviewIMDB {

    private String title;

    private String author;

    private String helpfulness;

    private String score;

    private String date;

    private String content;

    private String avatar;

    public ReviewIMDB(String title, String author, String helpfulness, String score, String date, String content, String avatar) {
        this.title = title;
        this.author = author;
        this.helpfulness = helpfulness;
        this.score = score;
        this.date = date;
        //去除无用标签
        this.content = content.replaceAll("<br>|<br/>|<br />", "");
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHelpfulness() {
        return helpfulness;
    }

    public void setHelpfulness(String helpfulness) {
        this.helpfulness = helpfulness;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
