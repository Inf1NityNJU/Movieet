package bl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by Sorumi on 17/3/18.
 */
public class YearDateUtil implements DateUtil {
    @Override
    public LocalDate plus(LocalDate date, int num) {
        return date.plusYears(num);
    }

    @Override
    public int between(LocalDate startDate, LocalDate endDate) {
        return Math.toIntExact(ChronoUnit.YEARS.between(startDate, endDate));
    }
}
