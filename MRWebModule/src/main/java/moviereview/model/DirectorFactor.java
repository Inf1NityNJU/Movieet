package moviereview.model;


/**
 * Created by SilverNarcissus on 2017/5/8.
 *
 */
public class DirectorFactor implements Comparable<DirectorFactor>{
    /**
     * 潜在因子
     */
    private double factor;

    /**
     * 导演姓名
     */
    private String name;

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

    public DirectorFactor(double factor, String name) {
        this.factor = factor;
        this.name = name;
    }

    @Override
    public int compareTo(DirectorFactor o) {
        if (factor > o.factor){
            return -1;
        }
        else if (factor == o.factor){
            return 0;
        }
        return 1;
    }
}
