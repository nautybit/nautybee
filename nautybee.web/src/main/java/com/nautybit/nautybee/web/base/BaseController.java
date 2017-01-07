package com.nautybit.nautybee.web.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by UFO on 16/12/29.
 */
@Controller
@RequestMapping("system")
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @RequestMapping("currentUser")
    @ResponseBody
    public void currentUser() {
        System.out.println("@WDFFGHHH");
    }
}
