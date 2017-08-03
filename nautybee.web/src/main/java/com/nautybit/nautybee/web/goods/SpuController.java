package com.nautybit.nautybee.web.goods;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nautybit.nautybee.biz.goods.GoodsPicService;
import com.nautybit.nautybee.biz.goods.SpuService;
import com.nautybit.nautybee.biz.user.UserService;
import com.nautybit.nautybee.biz.wx.WxService;
import com.nautybit.nautybee.common.result.Result;
import com.nautybit.nautybee.common.result.wx.WxAuthToken;
import com.nautybit.nautybee.common.utils.HttpUtils;
import com.nautybit.nautybee.entity.goods.GoodsPic;
import com.nautybit.nautybee.entity.goods.Spu;
import com.nautybit.nautybee.entity.user.User;
import com.nautybit.nautybee.http.result.goods.SpuListVO;
import com.nautybit.nautybee.view.goods.GoodsPicView;
import com.nautybit.nautybee.view.goods.SpuView;
import com.nautybit.nautybee.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.mockito.internal.util.collections.ListUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.ListUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by UFO on 17/01/12.
 */
@Slf4j
@Controller
@RequestMapping("wx/goods")
public class SpuController extends BaseController {
    @Autowired
    private SpuService spuService;
    @Autowired
    private WxService wxService;
    @Autowired
    private GoodsPicService goodsPicService;

    @Value("${nautybee.wechat.appid}")
    private String wechatappid;
    @Value("${nautybee.wechat.secret}")
    private String wechatsecret;

    private Gson gson = new Gson();

    private String authUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";


    @RequestMapping("getSpuList")
    public String getSpuList(ModelMap model,String code,String state) {

        String openid = wxService.getOpenId(authUrl,code);
        System.out.println("openid from getSpuList:"+openid);
        model.addAttribute("openid", openid);

        List<SpuView> spuList = spuService.queryListOfCategory();


        List<SpuListVO> targetList = new ArrayList<>();
        if(!ListUtils.isEmpty(spuList)){
            for(SpuView spuView:spuList){
                if(ListUtils.isEmpty(targetList)){
                    SpuListVO spuListVO = new SpuListVO();
                    spuListVO.setCatName(spuView.getCatName());
                    List<SpuView> spuViewList = new ArrayList<>();
                    spuViewList.add(spuView);
                    spuListVO.setSpuViewList(spuViewList);
                    targetList.add(spuListVO);
                }else {
                    boolean hasGroup = false;
                    for(SpuListVO spuListVO:targetList){
                        if(spuListVO.getCatName().equals(spuView.getCatName())){
                            hasGroup = true;
                            spuListVO.getSpuViewList().add(spuView);
                        }
                    }
                    if(!hasGroup){
                        SpuListVO spuListVO = new SpuListVO();
                        spuListVO.setCatName(spuView.getCatName());
                        List<SpuView> spuViewList = new ArrayList<>();
                        spuViewList.add(spuView);
                        spuListVO.setSpuViewList(spuViewList);
                        targetList.add(spuListVO);
                    }
                }
            }
        }


        model.addAttribute("targetList",targetList);
        return "goods/goodsList";
    }

    @RequestMapping("getSpuDetail")
    public String getSpuDetail(ModelMap model,String code,String state,Long spuId) {

        if(code != null){
            String authRes = HttpUtils.sendGet(authUrl, "appid=" + wechatappid + "&secret=" + wechatsecret+ "&code=" +code + "&grant_type=authorization_code");
            WxAuthToken wxAuthToken = gson.fromJson(authRes, new TypeToken<WxAuthToken>() {}.getType());
            String openid = wxAuthToken.getOpenid();
            System.out.println("openid:"+openid);
            model.addAttribute("openid",openid);
            log.debug("openid:"+openid);
        }

        Spu spu = spuService.getById(spuId);
        SpuView spuView = new SpuView();
        BeanUtils.copyProperties(spu,spuView);
        List<GoodsPicView> picList = goodsPicService.selectBySpuId(spuId);
        spuView.setPicList(picList);
        model.addAttribute("spuView",spuView);
        return "goods/goodsDetail";
    }


}















