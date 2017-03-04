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
}
