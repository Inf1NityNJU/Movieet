package bl;

import java.time.LocalDate;

/**
 * Created by SilverNarcissus on 2017/3/9.
 */
class BetweenDateChecker implements DateChecker {
    /**
     * 起始日期
     */
    private LocalDate start;
    /**
     * 结束日期
     */
    private LocalDate end;

    public BetweenDateChecker(LocalDate start, LocalDate end) {
        //讲一下保护性拷贝
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean check(LocalDate dateToCheck) {
        return dateToCheck.isAfter(start) && dateToCheck.isBefore(end)
                &&dateToCheck.isEqual(start)&&dateToCheck.isEqual(end);
    }
}
