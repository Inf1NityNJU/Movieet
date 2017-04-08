package moviereview.util;

/**
 * Created by Kray on 2017/4/8.
 */
public class Sort {

    private String order;
    private String asc;

    public Sort(String sortType, boolean isAsc) {
        this.order = sortType.toUpperCase();
        this.asc = isAsc ? "ASC" : "DESC";
    }

    @Override
    public String toString() {
        return order + "_" + asc;
    }

    public String getOrder() {
        return order;
    }

    public String getAsc() {
        return asc;
    }
}
