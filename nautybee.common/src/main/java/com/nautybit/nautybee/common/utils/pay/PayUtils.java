package com.nautybit.nautybee.common.utils.pay;

import java.lang.reflect.Field;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Minutch on 15/7/22.
 */
public class PayUtils {

    /**
     * 支付参数转成Map
     * @param payparam
     * @return
     */
    public static SortedMap<String,String> payParamToMap(Object payparam,boolean isAllowNull) {
        SortedMap<String, String> resMap = new TreeMap<String, String>();
        Field[] declaredFields = payparam.getClass().getDeclaredFields();
        try {
            for (Field field : declaredFields) {
                field.setAccessible(true);
                Object value = field.get(payparam);
                if(!isAllowNull&&value==null){
                    continue;
                }
                resMap.put(field.getName(),String.valueOf(value));
            }
        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return resMap;
    }
    
    public static SortedMap<String,String> payParamToMap(Object payparam) {
        return payParamToMap(payparam,true);
    }
}
