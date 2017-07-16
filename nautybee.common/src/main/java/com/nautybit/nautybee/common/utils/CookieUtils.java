package com.nautybit.nautybee.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class CookieUtils {

    public static final String UTF_8 = "UTF-8";

    public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        if(maxAge>0){
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     *
     * @param s 需要编码的字符串
     * @param ens 编码类型
     * @return 被编码的字符串
     * @throws java.io.UnsupportedEncodingException
     */
    public static String decode(String s,String ens) throws UnsupportedEncodingException {
        s = URLDecoder.decode(s,ens);
        return s;
    }
}
