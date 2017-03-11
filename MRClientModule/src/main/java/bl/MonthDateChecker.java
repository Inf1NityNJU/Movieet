package bl;

import java.time.LocalDate;

/**
 * Created by vivian on 2017/3/10.
 */
class MonthDateChecker implements DateChecker {
    private LocalDate startDate;
    private LocalDate endDate;

    public MonthDateChecker(String start, String end) {
        start = start + "-01";
        end = end + "-01";
        startDate = LocalDate.parse(start);
        endDate = LocalDate.parse(end).plusMonths(1).minusDays(1);
    }

    @Override
    public boolean check(LocalDate dateToCheck) {
        if ((dateToCheck.isAfter(startDate) && dateToCheck.isBefore(endDate)) || dateToCheck.isEqual(startDate) || dateToCheck.isEqual(endDate)) {
            return true;
        }
        return false;
    }
}
