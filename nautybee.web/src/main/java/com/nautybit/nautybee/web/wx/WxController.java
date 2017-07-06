package com.nautybit.nautybee.web.wx;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nautybit.nautybee.biz.wx.WxService;
import com.nautybit.nautybee.common.result.Result;
import com.nautybit.nautybee.common.result.wx.WxAccessToken;
import com.nautybit.nautybee.common.result.wx.WxApiResult;
import com.nautybit.nautybee.common.utils.HttpUtils;
import com.nautybit.nautybee.common.utils.SHA1;
import com.nautybit.nautybee.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * Created by UFO on 17/01/19.
 */
@Slf4j
@Controller
@RequestMapping("wx")
public class WxController extends BaseController {
    private String verifyServerToken = "verifyServerToken_nautybee";
    @Autowired
    private WxService wxService;
    private String createMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
    private Gson gson = new Gson();

    @RequestMapping("/")
    @ResponseBody
    public String verifyServerToken(){
        // 微信加密签名
        String signature = request.getParameter("signature");
        System.out.println(signature);
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

        String[] str = { verifyServerToken, timestamp, nonce };
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];
        // SHA1加密
        String digest = SHA1.encode(bigStr).toLowerCase();

        // 确认请求来至微信
        if (digest.equals(signature)) {
            return echostr;
        }
        return "";
    }

    @RequestMapping("/getAccessToken")
    @ResponseBody
    public String getAccessToken(){
        Result res = wxService.getAccessToken();
        if(res.isSuccess()){
            String accessToken = (String)res.getData();
            return accessToken;
        }else {
            return res.getErrorMsg();
        }
    }

    @RequestMapping("/jssdkConfig")
    @ResponseBody
    public Result<?> jssdkConfig(String url){
        return wxService.jssdkConfig(url);
    }

    @RequestMapping("/createMenu")
    @ResponseBody
    public Result<?> createMenu(){
        Result getAccessToken = wxService.getAccessToken();
        if(!getAccessToken.isSuccess()){
            return getAccessToken;
        }
        String accessToken = (String)getAccessToken.getData();
        String url = createMenuUrl + accessToken;
        String param = "{\"button\":[{\"name\":\"小栈服务\",\"sub_button\":[{\"type\":\"view\",\"name\":\"课程报名\",\"url\":\"http://test.bitstack.cn/nautybee/goods/getSpuList\"},{\"type\":\"view\",\"name\":\"课程分享\",\"url\":\"http://www.baidu.com/\"}]},{\"type\":\"view\",\"name\":\"我\",\"url\":\"http://www.baidu.com/\"},{\"type\":\"view\",\"name\":\"关于小栈\",\"url\":\"http://www.baidu.com/\"}]}";
        String result = HttpUtils.sendPost(url, param);
        //根据返回值判断结果
        if (StringUtils.isEmpty(result)) {
            log.warn("创建菜单调用接口超时");
            return Result.wrapErrorResult("","创建菜单调用接口超时");
        } else {
            WxApiResult wxApiResult = gson.fromJson(result, new TypeToken<WxApiResult>(){}.getType());
            if("0".equals(wxApiResult.getErrcode())){
                return Result.wrapSuccessfulResult("menu created successfully");
            }else {
                return Result.wrapErrorResult(wxApiResult.getErrcode(),wxApiResult.getErrmsg());
            }
        }
    }

    @RequestMapping("/wxPay")
    @ResponseBody
    public Result<?> wxPay(){
        Result getAccessToken = wxService.getAccessToken();
        if(!getAccessToken.isSuccess()){
            return getAccessToken;
        }
        String accessToken = (String)getAccessToken.getData();
        String url = createMenuUrl + accessToken;
        String param = "{\"button\":[{\"name\":\"小栈服务\",\"sub_button\":[{\"type\":\"view\",\"name\":\"课程报名\",\"url\":\"http://www.baidu.com/\"},{\"type\":\"view\",\"name\":\"课程分享\",\"url\":\"http://www.baidu.com/\"}]},{\"type\":\"view\",\"name\":\"我\",\"url\":\"http://www.baidu.com/\"},{\"type\":\"view\",\"name\":\"关于小栈\",\"url\":\"http://www.baidu.com/\"}]}";
        String result = HttpUtils.sendPost(url, param);
        //根据返回值判断结果
        if (StringUtils.isEmpty(result)) {
            log.warn("创建菜单调用接口超时");
            return Result.wrapErrorResult("","创建菜单调用接口超时");
        } else {
            WxApiResult wxApiResult = gson.fromJson(result, new TypeToken<WxApiResult>(){}.getType());
            if("0".equals(wxApiResult.getErrcode())){
                return Result.wrapSuccessfulResult("menu created successfully");
            }else {
                return Result.wrapErrorResult(wxApiResult.getErrcode(),wxApiResult.getErrmsg());
            }
        }
    }
}















