package com.nautybit.nautybee.web.base;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by UFO on 16/12/29.
 */
public class BaseController {
    static String[] headsNames = {"clientip", "X-Real-IP", "X-Forwarded-For", "remote_addr"};

    @Autowired
    protected HttpServletRequest request;

    public String getIp() {
        for (String headName : headsNames) {
            String header = request.getHeader(headName);
            if (header != null) {
                return header;
            }
        }
        return request.getRemoteAddr();
    }
}
