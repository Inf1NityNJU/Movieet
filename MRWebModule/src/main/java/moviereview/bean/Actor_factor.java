package moviereview.bean;

/**
 * Created by SilverNarcissus on 2017/5/8.
 */
public class Actor_factor {
    /**
     * 潜在因子
     */
    private double factor;

    /**
     * 主演姓名
     */
    private String name;

    public Actor_factor(double factor, String name) {
        this.factor = factor;
        this.name = name;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
