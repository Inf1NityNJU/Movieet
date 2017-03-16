package bl;

import java.time.LocalDate;

/**
 * Created by SilverNarcissus on 2017/3/10.
 */
interface DateFormatter {
    /**
     * 格式化日期
     * @param date 需要格式化的日期
     * @return 格式化后的字符串
     */
    String format(LocalDate date);
}
