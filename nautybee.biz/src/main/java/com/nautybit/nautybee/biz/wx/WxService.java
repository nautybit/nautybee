package com.nautybit.nautybee.biz.wx;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nautybit.nautybee.common.result.Result;
import com.nautybit.nautybee.common.result.wx.JsapiTicket;
import com.nautybit.nautybee.common.result.wx.JssdkConfig;
import com.nautybit.nautybee.common.result.wx.WxAccessToken;
import com.nautybit.nautybee.common.utils.DateUtils;
import com.nautybit.nautybee.common.utils.HttpUtils;
import com.nautybit.nautybee.common.utils.SHA1;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class WxService{

    @Value("${nautybee.wechat.appid}")
    private String wechatappid;
    @Value("${nautybee.wechat.secret}")
    private String wechatsecret;
    private Gson gson = new Gson();

    private String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
    private String accessToken;
    private Date lastUpdateTime;
    private Integer accessExpire;

    private String jsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    private String jsapiTicket;

    public Result<?> getAccessToken(){
        boolean reFetch = false;
        log.debug("beforeReFetch:"+"accessToken_"+accessToken+",lastUpdateTime_"+lastUpdateTime+",jsapiTicket"+jsapiTicket);
        //服务器重启导致
        if(StringUtils.isEmpty(accessToken) || null == lastUpdateTime || StringUtils.isEmpty(jsapiTicket)){
            reFetch = true;
        }else {
            Date date = new Date();
            //过期了
            if(date.getTime() - lastUpdateTime.getTime() > accessExpire * 1000){
                reFetch = true;
            }
        }
        if(reFetch){

            log.debug("reFetch:"+"accessToken_"+accessToken+",lastUpdateTime_"+lastUpdateTime+",jsapiTicket"+jsapiTicket);

            String resStr = HttpUtils.sendGet(accessTokenUrl, "grant_type=client_credential&appid=" + wechatappid + "&secret=" + wechatsecret);
            if(resStr.indexOf("errcode")>-1){
                return Result.wrapErrorResult("","failed...damn");
            }
            WxAccessToken wxAccessToken = gson.fromJson(resStr, new TypeToken<WxAccessToken>(){}.getType());
            accessExpire = wxAccessToken.getExpires_in();
            accessToken = wxAccessToken.getAccess_token();
            lastUpdateTime = new Date();


            //jsapi_ticket
            String ticketResStr = HttpUtils.sendGet(jsapiTicketUrl, "access_token=" + accessToken + "&type=jsapi");
            JsapiTicket ticketRes = gson.fromJson(ticketResStr, new TypeToken<JsapiTicket>(){}.getType());
            if(!"0".equals(ticketRes.getErrcode())){
                return Result.wrapErrorResult("","failed...damn");
            }
            jsapiTicket = ticketRes.getTicket();

            return Result.wrapSuccessfulResult(accessToken);
        }else {
            return Result.wrapSuccessfulResult(accessToken);
        }
    }

    public Result<?> jssdkConfig(String url){

        if(StringUtils.isEmpty(url)){
            return Result.wrapErrorResult("","invalid url");
        }else {
            if(StringUtils.isEmpty(jsapiTicket)){
                System.out.println("jsapiTicket empty");
                getAccessToken();
            }
            String appId = wechatappid;
            String nonceStr = UUID.randomUUID().toString();
            Long timestamp = System.currentTimeMillis() / 1000;
            url = url.split("#")[0];

            String concatStr = "jsapi_ticket="+jsapiTicket+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+url;
            // SHA1加密
            String signature = SHA1.encode(concatStr).toLowerCase();

            JssdkConfig jssdkConfig = new JssdkConfig();
            jssdkConfig.setAppId(appId);
            jssdkConfig.setNonceStr(nonceStr);
            jssdkConfig.setTimestamp(timestamp);
            jssdkConfig.setSignature(signature);
            return Result.wrapSuccessfulResult(jssdkConfig);
        }
    }
}
