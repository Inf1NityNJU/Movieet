package bl.date;

import java.time.LocalDate;

/**
 * Created by SilverNarcissus on 2017/3/9.
 */
public class YearDateChecker implements DateChecker {

    @Override
    public boolean check(LocalDate dateToCheck) {
        return true;
    }
}