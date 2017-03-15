package vo;


import java.time.LocalDateTime;

/**
 * Created by vivian on 2017/3/3.
 */
public class ReviewVO {
    /**
     * 评分
     */
    int score;

    /**
     * 评价时间
     */
    LocalDateTime localDateTime;

    /**
     * 单词数？？？
     */
    int words;

    /**
     * 评论概括
     */
    String summary;

    /**
     * 评论内容
     */
    String context;

    public ReviewVO(int score, LocalDateTime localDateTime, int words, String summary, String context) {
        this.score = score;
        this.localDateTime = localDateTime;
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

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
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
