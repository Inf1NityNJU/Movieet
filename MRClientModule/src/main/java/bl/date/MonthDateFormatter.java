package bl.date;

import java.time.LocalDate;

/**
 * Created by vivian on 2017/3/10.
 */
class MonthDateFormatter implements DateFormatter {
    @Override
    public String format(LocalDate date) {
        String dateString = date.toString();
        return dateString.substring(0,7);
    }

    @Override
    public LocalDate parse(String date) {
        if (date.length() != 7) return null;
        return LocalDate.parse(date + "-01");
    }
}
