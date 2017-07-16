package com.nautybit.nautybee.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by UFO on 17/7/16.
 */
public class CommonUtils {
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return  name;
    }
}
