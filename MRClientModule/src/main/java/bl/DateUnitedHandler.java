package bl;

/**
 * Created by vivian on 2017/3/11.
 */
interface DateUnitedHandler {
    //用于将同一年或同一月的数据归并在一起
    DateIntPair[] getAmount(DateIntPair[] dateIntPairs);
}
