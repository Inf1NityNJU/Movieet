package bl.date;

import java.time.LocalDate;

/**
 * Created by vivian on 2017/3/10.
 */
class DayDateFormatter implements DateFormatter {
    @Override
    public String format(LocalDate date) {
        return date.toString();
    }

    @Override
    public LocalDate parse(String date) {
        if (date.length() != 10) return null;
        return LocalDate.parse(date);
    }
}
