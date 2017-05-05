package moviereview.dao;

import moviereview.dao.impl.HibernateHelper;

/**
 * Created by SilverNarcissus on 2017/5/5.
 */
public class DataHelperFactory {
    private static DataHelper hibernateHelper;

    public static <T> DataHelper<T> getHibernateHelper(Class<T> type){
        return new HibernateHelper<T>(type);
    }
}
