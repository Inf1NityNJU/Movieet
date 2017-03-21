package util;

/**
 * Created by vivian on 2017/3/21.
 */
public enum SortType {
    TIMESORT_LATEST_TO_OLD, //按时间排序，由近到远
    TIMESORT_OLD_TO_LATEST, //按时间排序，由远到近
    SCORESORT_HIGH_TO_LOW, //按评分排序，由高到低
    SCORESORT_LOW_TO_HIGH //按评分排序，由低到高
}
