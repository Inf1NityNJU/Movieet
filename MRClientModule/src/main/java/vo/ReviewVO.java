package vo;


import javafx.scene.image.Image;

import java.time.LocalDate;

/**
 * Created by vivian on 2017/3/3.
 */
public class ReviewVO {
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

    public Image avatar;

    public ReviewVO(int score, LocalDate localDate, int words, String summary, String context) {
        this.score = score;
        this.localDate = localDate;
        this.words = words;
        this.summary = summary;
        this.context = context;
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
}
