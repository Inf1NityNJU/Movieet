package bl.date;

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

    /**
     * 解析字符串
     * @param date 需要解析的字符串
     * @return 解析后的日期
     */
    LocalDate parse(String date);

}
