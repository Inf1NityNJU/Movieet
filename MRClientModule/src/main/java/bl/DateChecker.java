package bl;

import java.time.LocalDate;

/**
 * Created by SilverNarcissus on 2017/3/9.
 */
interface DateChecker {
    /**
     * 查看日期是否符合规范
     *
     * @param dateToCheck 指定日期
     * @return 是否符合规范
     */
    public boolean check(LocalDate dateToCheck);
}
