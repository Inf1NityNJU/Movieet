package bl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivian on 2017/3/11.
 */
class MonthDateUnitedHandler implements DateUnitedHandler{
    @Override
    public DateIntPair[] getUnitedArray(DateIntPair[] dateIntPairs) {
        if (dateIntPairs.length <= 1) {
            return dateIntPairs;
        } else {
            //定位指针
            int point = 0;

            //生成新的DateIntPair数组
            List<DateIntPair> dateIntPairList = new ArrayList<>();
            DateIntPair dateIntPairTemp = new DateIntPair(dateIntPairs[0].getDate());

            while (point<dateIntPairs.length){
                if (dateIntPairs[point].getDate().getYear() == dateIntPairTemp.getDate().getYear()
                        && dateIntPairs[point].getDate().getMonthValue() == dateIntPairTemp.getDate().getMonthValue()){
                    for(int i=0;i<dateIntPairTemp.count.length;i++){
                        dateIntPairTemp.count[i] += dateIntPairs[point].count[i];
                    }
                    point++;
                } else {
                    dateIntPairList.add(dateIntPairTemp);
                    dateIntPairTemp = new DateIntPair(dateIntPairTemp.getDate().plusMonths(1));

                }
            }

            dateIntPairList.add(dateIntPairTemp);

            return dateIntPairList.toArray(new DateIntPair[dateIntPairList.size()]);
        }
    }
}
