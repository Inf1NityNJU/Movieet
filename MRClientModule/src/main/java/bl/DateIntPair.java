package bl;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Created by SilverNarcissus on 2017/3/9.
 */
class DateIntPair implements Comparable<DateIntPair> {
    /**
     * 记录日期
     */
    private LocalDate date;

    public DateIntPair(LocalDate date) {
        Arrays.fill(count, 0);
        this.date = date;
    }

    /**
     * 根据评分记录评论数量
     */
    int[] count = new int[5];


    public LocalDate getDate() {
        return date;
    }

    @Override
    public int compareTo(DateIntPair o) {
        return date.compareTo(o.date);
    }

    public void increase(int i) {
        count[i]++;
    }

    public int getTotalAmountOfReview(){
        int sum = 0;
        for(int num: count){
            sum = sum+num;
        }
        return sum;
    }

}
