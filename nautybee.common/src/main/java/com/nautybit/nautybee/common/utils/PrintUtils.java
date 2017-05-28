package com.nautybit.nautybee.common.utils;

import java.util.List;
import java.util.Map;

/**
 * Created by Minutch on 15/7/9.
 */
public class PrintUtils {

    public static <T> String printArray(T[] array) {
        StringBuffer sb = new StringBuffer();
        if (array!=null && array.length>0) {
            for (T str : array) {
                sb.append(str);
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static <T> String printList(List<T> list) {
        StringBuffer sb = new StringBuffer();
        if (list!=null && list.size()>0) {
            for (T lon : list) {
                sb.append(lon);
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static String printMap(Map<String,String> map) {
        StringBuffer sb = new StringBuffer();
        for (String key: map.keySet()) {
            sb.append("{");
            sb.append(key);
            sb.append("=");
            sb.append(map.get(key));
            sb.append("}");
        }

        return sb.toString();
    }
}
