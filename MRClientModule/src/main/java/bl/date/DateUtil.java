package bl.date;

import java.time.LocalDate;

/**
 * Created by Sorumi on 17/3/18.
 */
public interface DateUtil {

    public LocalDate plus(LocalDate date, int num);

    public int between(LocalDate startDate, LocalDate endDate);

}
