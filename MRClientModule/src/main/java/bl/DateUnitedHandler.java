package bl;

/**
 * Created by vivian on 2017/3/11.
 */
interface DateUnitedHandler {
    /**
     * 将指定 reviews 列表按照规定方式归并
     * @param dateIntPairs 指定reviews列表
     * @return 归并后的reviews 列表
     */
    DateIntPair[] getUnitedArray(DateIntPair[] dateIntPairs);
}
