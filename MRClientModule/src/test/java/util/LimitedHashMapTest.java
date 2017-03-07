package util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by SilverNarcissus on 2017/3/7.
 */
public class LimitedHashMapTest {
    @Test
    public void removeEldestEntry() throws Exception {
        LimitedHashMap<String, String> map = new LimitedHashMap<>();
        for (int i = 0; i < 11; i++) {
            map.put("test" + i, "test" + i);
        }

        assertEquals(10, map.size());
        assertEquals(null, map.get("test0"));
    }

}