package component.scatterchart;

/**
 * Created by Sorumi on 17/4/12.
 */
public class PointData {

    public String name;
    public Integer x;
    public Double y;

    public PointData(String name, Integer x, Double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public PointData(Integer x, Double y) {
        this.x = x;
        this.y = y;
    }
}
