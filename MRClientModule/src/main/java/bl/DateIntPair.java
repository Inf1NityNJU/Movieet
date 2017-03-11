package bl;

import java.time.LocalDate;

/**
 * Created by SilverNarcissus on 2017/3/9.
 */
class DateIntPair implements Comparable<DateIntPair> {
    /**
     * 记录日期
     */
    LocalDate date;

    public DateIntPair(LocalDate date) {
        this.date = date;
    }

    /**
     * 记录评论数量
     */
    int count = 1;

    public LocalDate getDate() {
        return date;
    }

    @Override
    public int compareTo(DateIntPair o) {
        return date.compareTo(o.date);
    }

    public void increase() {
        count++;
    }

}
