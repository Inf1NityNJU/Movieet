package bl;

import java.time.LocalDate;

/**
 * Created by vivian on 2017/3/10.
 */
public class DayDateFormmatter implements DateFormatter{
    @Override
    public String format(LocalDate date) {
        return date.toString();
    }
}
