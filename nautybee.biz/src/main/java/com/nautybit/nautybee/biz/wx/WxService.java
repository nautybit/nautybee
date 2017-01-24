package com.nautybit.nautybee.biz.wx;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nautybit.nautybee.common.result.Result;
import com.nautybit.nautybee.common.result.wx.WxAccessToken;
import com.nautybit.nautybee.common.utils.DateUtils;
import com.nautybit.nautybee.common.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WxService{

    @Value("${nautybee.wechat.appid}")
    private String wechatappid;
    @Value("${nautybee.wechat.secret}")
    private String wechatsecret;
    private String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
    private Gson gson = new Gson();
    private String accessToken;
    private Date lastUpdateTime;
    private Integer accessExpire;

    public Result<?> getAccessToken(){
        boolean reFetch = false;
        //服务器重启导致
        if(accessToken == null || lastUpdateTime == null){
            reFetch = true;
        }else {
            Date date = new Date();
            //过期了
            if(date.getTime() - lastUpdateTime.getTime() > accessExpire * 1000){
                reFetch = true;
            }
        }
        if(reFetch){
            String resStr = HttpUtils.sendGet(accessTokenUrl, "grant_type=client_credential&appid=" + wechatappid + "&secret=" + wechatsecret);
            if(resStr.indexOf("errcode")>-1){
                return Result.wrapErrorResult("","failed...damn");
            }
            WxAccessToken wxAccessToken = gson.fromJson(resStr, new TypeToken<WxAccessToken>(){}.getType());
            accessExpire = wxAccessToken.getExpires_in();
            accessToken = wxAccessToken.getAccess_token();
            lastUpdateTime = new Date();
            return Result.wrapSuccessfulResult(accessToken);
        }else {
            return Result.wrapSuccessfulResult(accessToken);
        }
    }
}
