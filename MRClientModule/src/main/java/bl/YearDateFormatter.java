package bl;

import java.time.LocalDate;

/**
 * Created by vivian on 2017/3/10.
 */
public class YearDateFormatter implements DateFormatter{
    @Override
    public String format(LocalDate date) {
        String dateString = date.toString();
        return dateString.substring(0,4);
    }

}
