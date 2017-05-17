package moviereview.bean;

/**
 * Created by vivian on 2017/5/17.
 */
public class MovieStateForUser {
    /**
     * 状态： 收藏/看过
     */
    private String state;

    /**
     *如果是收藏，为null，如果是看过，为评价的具体信息
     */
    private EvaluateBean evaluateBean;

    public MovieStateForUser(String state, EvaluateBean evaluateBean) {
        this.state = state;
        this.evaluateBean = evaluateBean;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public EvaluateBean getEvaluateBean() {
        return evaluateBean;
    }

    public void setEvaluateBean(EvaluateBean evaluateBean) {
        this.evaluateBean = evaluateBean;
    }
}
