package util;

/**
 * Created by vivian on 2017/3/24.
 * 该类制定了对评论列表的排序方法，其中:<br>
 * DATE_ASC，按照时间顺序由近到远<br>
 * DATE_DESC，按照时间由远到近<br>
 * HELPFULNESS_ASC, helpfulness字段求出来的数值从高到低排序<br>
 * HELPFULNESS_DESC, helpfulness字段求出来的数值从低到高排序<br>
 */
public enum ReviewSortType {
    DATE_ASC,
    DATE_DESC,
    HELPFULNESS_ASC,
    HELPFULNESS_DESC;

    /**
     * 得到排序依据
     *
     * @return 排序依据
     */
    public String getOrderBy() {
        return toString().split("_")[0].toLowerCase();
    }

    /**
     * 得到排序顺序
     *
     * @return 排序顺序
     */
    public boolean getOrder() {
        if (toString().split("_")[1].equals("ASC")) {
            return true;
        }
        return false;
    }
}
