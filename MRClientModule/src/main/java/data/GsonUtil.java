package data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.List;

/**
 * Created by SilverNarcissus on 2017/3/8.
 */
class GsonUtil {
    /**
     * 用于解析的gson实例
     */
    private static final Gson gson = new Gson();

    /**
     * 将Json数据解析成相应的映射对象
     *
     * @param jsonData 需要被解析的json串
     * @param type     要生成的PO.class
     * @param <T>      要生成的PO.class
     * @return 解析得到的PO
     */
    public static <T> T parseJson(String jsonData, Class<T> type) {
        return gson.fromJson(jsonData, type);
    }

    /**
     * 将Json数组解析成相应的映射对象列表
     *
     * @param jsonData 需要被解析的json串
     * @param type     要生成的PO.class
     * @param <T>      要生成的PO.class
     * @return 解析得到的PO列表
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> paeseJsonAsList(String jsonData, Class<T[]> type)
    {
        T[] array = gson.fromJson(jsonData, type);
        return Arrays.asList(array);
    }
}
