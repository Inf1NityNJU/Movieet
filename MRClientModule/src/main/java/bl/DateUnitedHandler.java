package bl;

/**
 * Created by vivian on 2017/3/11.
 */
public interface DateUnitedHandler {
    //用于将同一年或同一月的数据归并在一起
    public DateIntPair[] getAmount(DateIntPair[] dateIntPairs);
}
