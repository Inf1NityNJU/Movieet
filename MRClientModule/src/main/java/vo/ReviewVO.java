package vo;


import javafx.scene.image.Image;
import po.ReviewPO;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * Created by vivian on 2017/3/3.
 */
public class ReviewVO {
    /**
     * 用户头像
     */
    public Image avatar;

    /**
     * 用户ID
     */
    public String userId;

    /**
     * 用户名称
     */
    public String userName;

    /**
     * 有效率
     */
    public String helpfulness;

    /**
     * 评分
     */
    public int score;

    /**
     * 评价时间
     */
    public LocalDate localDate;

    /**
     * 单词数？？？
     */
    public int words;

    /**
     * 评论概括
     */
    public String summary;

    /**
     * 评论内容
     */
    public String context;

    public ReviewVO(Image avatar, String userId, String userName, String helpfulness, int score, LocalDate localDate, String summary, String context) {
        this.avatar = avatar;
        this.userId = userId;
        this.userName = userName;
        this.helpfulness = helpfulness;
        this.score = score;
        this.localDate = localDate;
        this.words = calWords(context);
        this.summary = summary;
        this.context = context;
    }

    public ReviewVO(ReviewPO reviewPO, Image avatar) {
        this.avatar = avatar;
        this.userId = reviewPO.getUserId();
        this.userName = reviewPO.getProfileName();
        this.helpfulness = reviewPO.getHelpfulness();
        this.score = reviewPO.getScore();
        this.localDate = Instant.ofEpochMilli(reviewPO.getTime() * 1000l).atZone(ZoneId.systemDefault()).toLocalDate();
        this.words = calWords(reviewPO.getText());
        this.summary = reviewPO.getSummary();
        this.context = reviewPO.getText();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDate getLocalDateTime() {
        return localDate;
    }

    public void setLocalDateTime(LocalDate localDate) {
        this.localDate = localDate;
    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    private int calWords(String context) {
        if (context != null && context.trim().length() != 0) {
            int count = 1;
            for (int i = 0; i < context.length(); i++) {
                if (context.charAt(i) == ' ' && i > 0 && context.charAt(i - 1) != ' ') {
                    count++;
                }
            }
            return count;
        } else {
            return 0;
        }
    }
}
