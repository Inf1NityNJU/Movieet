package bl;

import java.time.LocalDate;

/**
 * Created by SilverNarcissus on 2017/3/9.
 */
class AllDateChecker implements DateChecker {

    @Override
    public boolean check(LocalDate dateToCheck) {
        return false;
    }
}
