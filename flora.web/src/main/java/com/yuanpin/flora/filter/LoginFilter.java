package com.yuanpin.flora.filter;

import com.yuanpin.flora.http.request.FloraReaderHttpServletRequestWrapper;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.handler.Handler;
import java.io.IOException;

/**
 * Created by UFO on 16/12/27.
 */
public class LoginFilter extends HandlerInterceptorAdapter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(servletRequest instanceof HttpServletRequest) {
            requestWrapper = new FloraReaderHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        }
        if(null == requestWrapper) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    public void destroy() {

    }
}
