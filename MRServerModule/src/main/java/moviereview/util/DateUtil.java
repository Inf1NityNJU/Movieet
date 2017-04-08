package moviereview.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Kray on 2017/4/3.
 */
public class DateUtil {

    public static long transformDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy", new Locale("en"));
            String[] temp = dateStr.split(" ");
            String newDate = temp[0] + " " + get3LengthMonth(temp[1]) + " " + temp[2];
            LocalDate parsedDate = LocalDate.parse(newDate, formatter);
            return parsedDate.toEpochDay() * 24 * 3600;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Fail to transfer date");
            System.out.println(dateStr);
            return -1;
        }
    }

    private static String get3LengthMonth(String month) {
        return month.substring(0, 3);
    }
}
