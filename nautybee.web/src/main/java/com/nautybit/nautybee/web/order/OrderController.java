package com.nautybit.nautybee.web.order;

import com.google.gson.Gson;
import com.nautybit.nautybee.biz.goods.SpuService;
import com.nautybit.nautybee.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by UFO on 17/01/12.
 */
@Slf4j
@Controller
@RequestMapping("wx/order")
public class OrderController extends BaseController {

    @Value("${nautybee.wechat.appid}")
    private String wechatappid;
    @Value("${nautybee.wechat.secret}")
    private String wechatsecret;


    @RequestMapping("confirmOrder")
    public String confirm() {
        return "order/confirmOrder";
    }




}















