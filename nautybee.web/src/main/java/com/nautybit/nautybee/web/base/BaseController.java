package com.nautybit.nautybee.web.base;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by UFO on 16/12/29.
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;
}
