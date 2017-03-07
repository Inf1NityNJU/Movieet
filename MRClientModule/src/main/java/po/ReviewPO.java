package po;

import util.FieldCount;

import java.lang.reflect.Field;
import java.util.EnumSet;

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

    public ReviewPO() {

    }

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

    /**
     * 提供一个编辑PO的编辑器
     *
     * @return 编辑器
     */
    public static Builder getBuilder() {
        return new Builder();
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

    public static class Builder {
        //要build的产品
        private ReviewPO product;
        //用于查看域是否填充完全
        private EnumSet<FieldCount> fieldCount;

        public Builder() {
            product = new ReviewPO();
            fieldCount = EnumSet.noneOf(FieldCount.class);
        }

        public Builder setMovieId(String movieId) {
            product.movieId = movieId;
            fieldCount.add(FieldCount.attribute1);
            return this;
        }

        public Builder setUserId(String userId) {
            product.userId = userId;
            fieldCount.add(FieldCount.attribute2);
            return this;
        }

        public Builder setProfileName(String profileName) {
            product.profileName = profileName;
            fieldCount.add(FieldCount.attribute3);
            return this;
        }

        public Builder setHelpfulness(String helpfulness) {
            product.helpfulness = helpfulness;
            fieldCount.add(FieldCount.attribute4);
            return this;
        }

        public Builder setScore(int score) {
            product.score = score;
            fieldCount.add(FieldCount.attribute5);
            return this;
        }

        public Builder setTime(long time) {
            product.time = time;
            fieldCount.add(FieldCount.attribute6);
            return this;
        }

        public Builder setSummary(String summary) {
            product.summary = summary;
            fieldCount.add(FieldCount.attribute7);
            return this;
        }

        public Builder setText(String text) {
            product.text = text;
            fieldCount.add(FieldCount.attribute8);
            return this;
        }

        public ReviewPO build() {
            if (!valid()) {
                throw new IllegalStateException("MoviePO's fields aren't complete!");
            }
            return product;
        }

        //检查产品的重要属性是否均设置完全
        private boolean valid() {
            return fieldCount.size() == 8;
        }
    }
}
