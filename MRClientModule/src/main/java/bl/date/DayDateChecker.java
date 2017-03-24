package bl.date;

import java.time.LocalDate;

/**
 * Created by vivian on 2017/3/10.
 */
class DayDateChecker implements DateChecker {
    private LocalDate startDate;
    private LocalDate endDate;

    public DayDateChecker(String startDate, String endDate) {
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
    }

    @Override
    public boolean check(LocalDate dateToCheck) {
        if ((dateToCheck.isAfter(startDate) && dateToCheck.isBefore(endDate)) || dateToCheck.isEqual(startDate) || dateToCheck.isEqual(endDate)) {
            return true;
        }
        return false;
    }
}
