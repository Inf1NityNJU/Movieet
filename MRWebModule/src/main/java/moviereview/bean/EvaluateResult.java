package moviereview.bean;

import moviereview.model.Page;

/**
 * Created by vivian on 2017/6/6.
 */
public class EvaluateResult {
    /**
     * 操作结果：成功或失败
     */
    public boolean result;

    /**
     * 操作结果的提示信息（如"您查找的电影不存在"）
     */
    public String message;

    /**
     * 推荐电影
     */
    public Page<MovieMini> recommend;

    public EvaluateResult() {
    }

    public EvaluateResult(boolean result, Page<MovieMini> recommend) {
        this.result = result;
        this.message = "Success";
        this.recommend = recommend;
    }

    public EvaluateResult(boolean result, String message, Page<MovieMini> recommend) {
        this.result = result;
        this.message = message;
        this.recommend = recommend;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Page<MovieMini> getRecommend() {
        return recommend;
    }

    public void setRecommend(Page<MovieMini> recommend) {
        this.recommend = recommend;
    }
}
