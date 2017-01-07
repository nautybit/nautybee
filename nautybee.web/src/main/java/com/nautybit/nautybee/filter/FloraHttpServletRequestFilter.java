package com.nautybit.nautybee.filter;


import com.nautybit.nautybee.http.request.FloraReaderHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Minutch on 15/7/20.
 */
public class FloraHttpServletRequestFilter implements Filter {

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
