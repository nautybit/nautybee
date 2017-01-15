package com.nautybit.nautybee.web.user;

import com.nautybit.nautybee.biz.user.UserService;
import com.nautybit.nautybee.common.result.Result;
import com.nautybit.nautybee.entity.user.User;
import com.nautybit.nautybee.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * Created by UFO on 17/01/12.
 */
@Slf4j
@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;


    @RequestMapping("getUserList")
    @ResponseBody
    public Result<?> getUserList() throws IOException{
//        User user = userService.getById(1000l);
        User user = new User();
        user.setUserName("李秋峦");
        return Result.wrapSuccessfulResult(user);
    }


}















