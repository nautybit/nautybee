package com.nautybit.nautybee.web.base;

import com.nautybit.nautybee.common.utils.CookieUtils;
import com.nautybit.nautybee.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by UFO on 16/12/29.
 */
public class BaseController {
    static String[] headsNames = {"clientip", "X-Real-IP", "X-Forwarded-For", "remote_addr"};

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    protected static String wxShareUrl = "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzI1NjU3Mjk0Nw==#wechat_redirect";

    public String getIp() {
        for (String headName : headsNames) {
            String header = request.getHeader(headName);
            if (header != null) {
                return header;
            }
        }
        return request.getRemoteAddr();
    }

    private String getViewRequestParam(Map<String, Cookie> cookieMap, String key) {
        String param = null;
        param = request.getParameter(key);
        if (param == null) {
            Cookie cookie = cookieMap.get(key);
            if (cookie != null) {
                param = cookie.getValue();
            }
        }
        return param;
    }

    protected Object getViewRequestParam(Map<String, Cookie> cookieMap, String key, Object sample) {
        String strParam = this.getViewRequestParam(cookieMap, key);
        if (sample instanceof java.lang.Integer) {
            Integer param = null;
            try {
                param = Integer.parseInt(strParam);
            } catch (Exception e) {
            }
            return param;
        } else if (sample instanceof java.lang.Long) {
            Long param = null;
            try {
                param = Long.parseLong(strParam);
            } catch (Exception e) {
            }
            return param;
        } else if (sample instanceof java.lang.Double) {
            Double param = null;
            try {
                param = Double.parseDouble(strParam);
            } catch (Exception e) {
            }
            return param;
        } else if (sample instanceof java.math.BigDecimal) {
            BigDecimal param = null;
            try {
                param = new BigDecimal(strParam);
            } catch (Exception e) {
            }
            return param;
        } else if (sample instanceof String) {
            String param = strParam;
            return param;
        } else if (sample instanceof Date) {
            Date param = null;
            try {
                param = DateUtils.smartFormat(strParam);
            } catch (Exception e) {
            }
            return param;
        }
        return strParam;
    }

    protected Map<String, Cookie> getCookieMap() {
        Cookie[] cookies = request.getCookies();
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String param = null;
                try {
                    param = CookieUtils.decode(cookie.getValue(), CookieUtils.UTF_8);
                } catch (UnsupportedEncodingException e) {

                }
                cookie.setValue(param);
                cookie.setPath(request.getRequestURI());
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    protected void removeCookie(String cookieKey) {
        Cookie[] cookies = request.getCookies();
        Cookie toRemoveCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieKey)) {
                    toRemoveCookie = cookie;
                    break;
                }
            }
        }
        if (toRemoveCookie != null) {
            toRemoveCookie.setValue(null);
            toRemoveCookie.setMaxAge(0);
            response.addCookie(toRemoveCookie);
        }
    }
}
