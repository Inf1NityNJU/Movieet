package moviereview.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by SilverNarcissus on 2017/3/8.
 */
public class GsonUtil {
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
     * 将Json数据解析成相应的映射对象
     *
     * @param jsonData 需要被解析的json串
     * @param listType 要生成的PO的泛型type
     * @param <T>      要生成的PO.class
     * @return 解析得到的PO
     */
    public static <T> T parseJsonInGeneric(String jsonData, Type listType) {
        return gson.fromJson(jsonData, listType);
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
    public static <T> List<T> parseJsonAsList(String jsonData, Class<T[]> type) {
        if (jsonData == null || jsonData.charAt(0) != '[') {
            return Collections.emptyList();
        }
        T[] array = gson.fromJson(jsonData, type);
        return Arrays.asList(array);
    }

    /**
     * 将Json字符串解析成相应的映射图
     *
     * @param jsonData 需要被解析的json串
     * @param <K>      图的键
     * @param <V>      图的值
     * @return 映射图
     */
    public static <K, V> Map<K, V> parseJsonAsMap(String jsonData) {
        return gson.fromJson(jsonData,
                new TypeToken<Map<K, V>>() {
                }.getType());
    }
}
