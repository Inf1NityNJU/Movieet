package util;

/**
 * Created by vivian on 17/3/5.
 */
public class EqualJudgeHelper {

    /**
     * 判断两个int变量是否相等
     *
     * @param i1;
     * @param i2;
     * @return 判断结果
     */
    public static boolean judgeEqual(int i1, int i2) {
        return i1 == i2;
    }

    /**
     * 判断两个double变量是否相等
     *
     * @param d1;
     * @param d2;
     * @return 判断结果
     */
    public static boolean judgeEqual(double d1, double d2) {
        return d1 == d2;
    }


    /**
     * 判断两个int数组是否相等
     *
     * @param i1;
     * @param i2;
     * @return 判断结果
     */
    public static boolean judgeEqual(int[] i1, int[] i2) {
        if (i1.length != i2.length) {
            return false;
        } else {
            for (int i = 0; i < i1.length; i++) {
                if (i1[i] != i2[i]) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 判断两个int数组是否相等
     *
     * @param s1;
     * @param s2;
     * @return 判断结果
     */
    public static boolean judgeEqual(String[] s1, String[] s2) {
        if (s1.length != s2.length) {
            return false;
        } else {
            for (int i = 0; i < s1.length; i++) {
                if (s1[i] != s2[i]) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 判断两个对象是否相等
     *
     * @param o1;
     * @param o2;
     * @return 判断结果
     */
    public static boolean judgeEqual(Object o1, Object o2) {
        if (o1 == null && o2 == null) {
            return true;
        } else if (o1 == null) {
            return false;
        }
        return o1.equals(o2);
    }
}
