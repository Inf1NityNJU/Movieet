package moviereview.bean;

/**
 * Created by vivian on 2017/5/17.
 */
public class MovieStateForUser {
    /**
     * 状态： 收藏/看过
     */
    private String status;

    /**
     *如果是收藏，为null，如果是看过，为评价的具体信息
     */
    private EvaluateBean result;

    public MovieStateForUser() {
    }

    public MovieStateForUser(String status, EvaluateBean result) {
        this.status = status;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EvaluateBean getResult() {
        return result;
    }

    public void setResult(EvaluateBean result) {
        this.result = result;
    }
}
