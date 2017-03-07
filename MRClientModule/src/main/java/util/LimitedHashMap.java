package util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by SilverNarcissus on 2017/3/7.
 */
public class LimitedHashMap<K, V> extends LinkedHashMap<K, V> {
    /**
     * 最大容量
     */
    private int maxEntries = 10;

    /**
     * 构造方法统一说明：<br>
     * 该类的构造方法中 maxEntries 是指这个 map 最多可以存放的键值对个数<br>
     * 如果不指定，这个值默认为10<br>
     * 如果这个值非正，则抛出 IllegalArgumentException<br>
     * 剩余的构造参数可以查询 LinkedHashMap 的构造函数<br>
     */

    public LimitedHashMap() {
        super();
    }

    public LimitedHashMap(int maxEntries) {
        this(maxEntries, 16, 0.75f, false);
    }

    public LimitedHashMap(int maxEntries, int capacity) {
        this(maxEntries, capacity, 0.75f, false);
    }

    public LimitedHashMap(int maxEntries, int capacity, float loadFactor) {
        this(maxEntries, capacity, loadFactor, false);
    }

    public LimitedHashMap(int maxEntries, int capacity, float loadFactor, boolean accessOrder) {
        super(capacity, loadFactor, accessOrder);
        if (maxEntries <= 0) {
            throw new IllegalArgumentException("Max entries can't be negative");
        }
        this.maxEntries = maxEntries;
    }

    public LimitedHashMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public LimitedHashMap(int maxEntries, Map<? extends K, ? extends V> m) {
        super(m);
        this.maxEntries = maxEntries;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxEntries;
    }

}
