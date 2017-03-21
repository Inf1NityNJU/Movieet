package util;

/**
 * Created by vivian on 2017/3/21.
 * 该类制定了对列表的排序方法，其中:<br>
 * TIME_LATEST_TO_OLD为按时间排序，由近到远<br>
 * TIME_OLD_TO_LATEST为按时间排序，由远到近<br>
 * SCORE_HIGH_TO_LOW为按评分排序，由高到低<br>
 * SCORE_LOW_TO_HIGH为按评分排序，由低到高<br>
 */
public enum SortType {
    TIME_LATEST_TO_OLD, //按时间排序，由近到远
    TIME_OLD_TO_LATEST, //按时间排序，由远到近
    SCORE_HIGH_TO_LOW, //按评分排序，由高到低
    SCORE_LOW_TO_HIGH //按评分排序，由低到高
}
