package bl;

import java.util.TreeSet;

/**
 * Created by vivian on 2017/3/11.
 */
class MonthDateUnitedHandler implements DateUnitedHandler{
    @Override
    public DateIntPair[] getUnitedArray(DateIntPair[] dateIntPairs) {
        if (dateIntPairs.length == 1) {
            return dateIntPairs;
        } else {
            //定位指针
            int formerPoint = 0;
            int latterPoint = 1;

            //记录没有月份重复的DateIntPair数组
            DateIntPair[] newDateIntPairs;

            //记录不重复月份在原数组中的位置
            TreeSet<Integer> uniqueYears = new TreeSet<>();

            //将年月份相同的DateIntPair放在一起
            while (latterPoint < dateIntPairs.length) {
                if (dateIntPairs[formerPoint].getDate().getYear() == dateIntPairs[latterPoint].getDate().getYear()
                        && dateIntPairs[formerPoint].getDate().getMonthValue()==dateIntPairs[latterPoint].getDate().getMonthValue()) {
                    for(int i=0;i<dateIntPairs[formerPoint].count.length;i++){
                        dateIntPairs[formerPoint].count[i] = dateIntPairs[formerPoint].count[i] + dateIntPairs[latterPoint].count[i];
                    }
//                    dateIntPairs[formerPoint].count = dateIntPairs[formerPoint].count + dateIntPairs[latterPoint].count;
//                    uniqueYears.add(formerPoint);
//                    latterPoint++;
                } else {
                    formerPoint = latterPoint;
//                    uniqueYears.add(formerPoint);
//                    latterPoint++;
                }
                uniqueYears.add(formerPoint);
                latterPoint++;
            }

            //生成新的DateIntPair数组
            newDateIntPairs = new DateIntPair[uniqueYears.size()];
            int count = 0;
            for (int location : uniqueYears) {
                newDateIntPairs[count] = dateIntPairs[location];
                count++;
            }

            return newDateIntPairs;
        }
    }
}
