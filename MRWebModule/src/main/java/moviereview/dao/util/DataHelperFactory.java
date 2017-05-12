package moviereview.dao.util;

/**
 * Created by SilverNarcissus on 2017/5/5.
 */
public class DataHelperFactory {
    private static DataHelper hibernateHelper;

    public static <T> DataHelper<T> getHibernateHelper(Class<T> type){
        HibernateHelper<T> product = new HibernateHelper<T>();
        product.init(type);
        return product;
    }
}
