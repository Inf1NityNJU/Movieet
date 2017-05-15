package moviereview.bean;

/**
 * Created by vivian on 2017/5/12.
 */
public class Result {
    /**
     * 操作结果：成功或失败
     */
    public boolean result;

    /**
     * 操作结果的提示信息（如"您查找的电影不存在"）
     */
    public String message;

    public Result(boolean result) {
        this.result = result;
        this.message = "Success";
    }

    public Result(boolean result, String message) {
        this.result = result;
        this.message = message;
    }
}
