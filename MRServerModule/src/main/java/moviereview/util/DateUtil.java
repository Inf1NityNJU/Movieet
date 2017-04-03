package moviereview.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Kray on 2017/4/3.
 */
public class DateUtil {

    public static long transformDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy", new Locale("en"));
        String[] temp = dateStr.split(" ");
        String newDate = temp[0] + " " + get3LengthMonth(temp[1]) + " " + temp[2];
        LocalDate parsedDate = LocalDate.parse(newDate, formatter);
        return parsedDate.toEpochDay() * 24 * 3600;
    }

    private static String get3LengthMonth(String month) {
        return month.substring(0, 3);
    }
}
