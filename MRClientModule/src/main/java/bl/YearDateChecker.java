package bl;

import java.time.LocalDate;

/**
 * Created by SilverNarcissus on 2017/3/9.
 */
class YearDateChecker implements DateChecker {

    @Override
    public boolean check(LocalDate dateToCheck) {
        return true;
    }
}
