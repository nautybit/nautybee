package com.nautybit.nautybee.web.goods;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nautybit.nautybee.biz.goods.SpuService;
import com.nautybit.nautybee.biz.user.UserService;
import com.nautybit.nautybee.common.result.Result;
import com.nautybit.nautybee.common.result.wx.WxAuthToken;
import com.nautybit.nautybee.common.utils.HttpUtils;
import com.nautybit.nautybee.entity.goods.Spu;
import com.nautybit.nautybee.entity.user.User;
import com.nautybit.nautybee.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by UFO on 17/01/12.
 */
@Slf4j
@Controller
@RequestMapping("goods")
public class SpuController extends BaseController {
    @Autowired
    private SpuService spuService;

    @Value("${nautybee.wechat.appid}")
    private String wechatappid;
    @Value("${nautybee.wechat.secret}")
    private String wechatsecret;

    private Gson gson = new Gson();

    private String authUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";


    @RequestMapping("getSpuList")
    public String getSpuList(ModelMap model,String code,String state) {

        if(code != null){
            String authRes = HttpUtils.sendGet(authUrl, "appid=" + wechatappid + "&secret=" + wechatsecret+ "&code=" +code + "&grant_type=authorization_code");
            WxAuthToken wxAuthToken = gson.fromJson(authRes, new TypeToken<WxAuthToken>() {}.getType());
            String openid = wxAuthToken.getOpenid();
            System.out.println("openid:"+openid);
            model.addAttribute("openid",openid);
            log.debug("openid:"+openid);
        }

        Spu spu = spuService.getById(1000l);
        List<Spu> spuList = new ArrayList<>();
        spuList.add(spu);
        model.addAttribute("spuList",spuList);
        return "goods/goodsList";
    }


}















