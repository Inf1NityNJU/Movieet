package bl.date;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by Sorumi on 17/3/18.
 */
public class MonthDateUtil implements DateUtil {
    @Override
    public LocalDate plus(LocalDate date, int num) {
        return date.plusMonths(num);
    }

    @Override
    public int between(LocalDate startDate, LocalDate endDate) {
        return Math.toIntExact(ChronoUnit.MONTHS.between(startDate, endDate));
    }
}
