package util;

import java.util.ArrayList;

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
        return Math.abs(d1 - d2) < 0.01;
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
     * 判断两个String数组是否相等
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
                if (!(s1[i].equals(s2[i]))) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 判断两个列表是否相等
     *
     * @param l1;
     * @param l2;
     * @return 判断结果
     */
    public static boolean judgeEqual(ArrayList l1, ArrayList l2) {
        if (l1.containsAll(l2)&&l2.containsAll(l1)){
            return true;
        }
        return false;
    }

    /**
     * 判断两个double列表是否相等
     *
     * @param d1;
     * @param d2;
     * @return 判断结果
     */
//    public static boolean judgeEqual(ArrayList<Double> d1, ArrayList<Double> d2) {
//        if (d1.size() != d2.size()) {
//            return false;
//        } else {
//            for (int i = 0; i < d1.size(); i++) {
//                if (!judgeEqual(d1.get(i), d2.get(i))) {
//                    return false;
//                }
//            }
//            return true;
//        }
//    }

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
