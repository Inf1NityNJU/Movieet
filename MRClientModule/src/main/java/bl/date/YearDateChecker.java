package bl.date;

import java.time.LocalDate;

/**
 * Created by SilverNarcissus on 2017/3/9.
 */
public class YearDateChecker implements DateChecker {
    private LocalDate startDate;
    private LocalDate endDate;

    public YearDateChecker(String start, String end) {
        start = start + "-01-01";
        end = end + "-01-01";

        startDate = LocalDate.parse(start);
        endDate = LocalDate.parse(end).plusYears(1).minusDays(1);
    }

    @Override
    public boolean check(LocalDate dateToCheck) {
        if ((dateToCheck.isAfter(startDate) && dateToCheck.isBefore(endDate)) || dateToCheck.isEqual(startDate) || dateToCheck.isEqual(endDate)) {
            return true;
        }
        return false;
    }

//    @Override
//    public boolean check(LocalDate dateToCheck) {
//        return true;
//    }
}
