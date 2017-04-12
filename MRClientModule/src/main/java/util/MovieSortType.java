package util;

/**
 * Created by vivian on 2017/3/21.
 * 该类制定了对列表的排序方法，其中:<br>
 * DATE_ASC 为按时间排序，由近到远<br>
 * DATE_DESC 为按时间排序，由远到近<br>
 * SCORE_ASC 为按评分排序，由高到低<br>
 * SCORE_DESC 为按评分排序，由低到高<br>
 */
public enum MovieSortType {
    DATE_ASC,
    DATE_DESC,
    SCORE_ASC,
    SCORE_DESC;

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
