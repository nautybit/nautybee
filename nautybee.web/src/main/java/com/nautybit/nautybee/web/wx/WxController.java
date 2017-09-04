package com.nautybit.nautybee.web.wx;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nautybit.nautybee.biz.recommend.RecommendService;
import com.nautybit.nautybee.biz.redis.RedisHashService;
import com.nautybit.nautybee.biz.sys.CommonResourcesService;
import com.nautybit.nautybee.biz.wx.MessageService;
import com.nautybit.nautybee.biz.wx.WxService;
import com.nautybit.nautybee.common.result.Result;
import com.nautybit.nautybee.common.result.wx.UserInfo;
import com.nautybit.nautybee.common.result.wx.WxApiResult;
import com.nautybit.nautybee.common.utils.HttpUtils;
import com.nautybit.nautybee.common.utils.SHA1;
import com.nautybit.nautybee.view.recommend.RecommendView;
import com.nautybit.nautybee.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    @Autowired
    private MessageService messageService;
    @Autowired
    private RedisHashService redisHashService;
    @Autowired
    private CommonResourcesService commonResourcesService;
    @Autowired
    private RecommendService recommendService;

    @Value("${nautybee.server.url}")
    private String nautybeeServerUrl;
    private String createMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
//    private String authUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private String userInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info";
    private Gson gson = new Gson();

    @RequestMapping("/")
    @ResponseBody
    public String verifyServerToken(HttpServletRequest request, HttpServletResponse response){

        boolean isGet = request.getMethod().toLowerCase().equals("get");
        if (isGet) {
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
        }else {
            try {
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                String respStr = messageService.processRequest(request);
                return respStr;
//                return "<xml><ToUserName><![CDATA[oE1wbwoqKSISaaDyoV_VFBl9oXnw]]></ToUserName><FromUserName><![CDATA[gh_18918a5de3eb]]></FromUserName><CreateTime>12345678</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[谢谢您的关注！]]></Content></xml>";
            }catch (Exception e){
                log.error("wx request.getInputStream():",e);
            }
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
//        String param = "{\"button\":[{\"name\":\"课程服务\",\"sub_button\":[{\"type\":\"view\",\"name\":\"课程报名\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx47b77ec8ef89f1a7&redirect_uri=http%3A%2F%2Fwww.bitstack.cn%2Fnautybee%2Fwx%2Fgoods%2FgetSpuList&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\"},{\"type\":\"view\",\"name\":\"我的课程\",\"url\":\"http://www.baidu.com/\"}]},{\"name\":\"课程推荐\",\"sub_button\":[{\"type\":\"view\",\"name\":\"我的推荐码\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx47b77ec8ef89f1a7&redirect_uri=http%3A%2F%2Fwww.bitstack.cn%2Fnautybee%2Fwx%2FgetShareCode&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\"},{\"type\":\"view\",\"name\":\"我的推荐记录\",\"url\":\"http://www.baidu.com/\"}]},{\"name\":\"关于我们\",\"sub_button\":[{\"type\":\"view\",\"name\":\"学校简介\",\"url\":\"http://www.baidu.com/\"},{\"type\":\"view\",\"name\":\"学校地址\",\"url\":\"http://apis.map.qq.com/tools/poimarker?type=0&marker=coord:28.889300,119.803420;title:%E6%AD%A6%E4%B9%89%E5%B0%8F%E4%BD%9C%E5%AE%B6%E8%BE%85%E5%AF%BC%E4%B8%AD%E5%BF%83;addr:%E6%B5%99%E6%B1%9F%E7%9C%81%E9%87%91%E5%8D%8E%E5%B8%82%E6%AD%A6%E4%B9%89%E5%8E%BF%E7%8E%AF%E5%9F%8E%E8%A5%BF%E8%B7%AF48%E5%8F%B7&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77&referer=myapp\"},{\"type\":\"view\",\"name\":\"加入我们\",\"url\":\"http://www.baidu.com/\"}]}]}";
        String param = commonResourcesService.selectByKey("wechatMenu").getValue7();
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

//    @RequestMapping("/wxPay")
//    @ResponseBody
//    public Result<?> wxPay(){
//        Result getAccessToken = wxService.getAccessToken();
//        if(!getAccessToken.isSuccess()){
//            return getAccessToken;
//        }
//        String accessToken = (String)getAccessToken.getData();
//        String url = createMenuUrl + accessToken;
//        String param = "{\"button\":[{\"name\":\"小栈服务\",\"sub_button\":[{\"type\":\"view\",\"name\":\"课程报名\",\"url\":\"http://www.baidu.com/\"},{\"type\":\"view\",\"name\":\"课程分享\",\"url\":\"http://www.baidu.com/\"}]},{\"type\":\"view\",\"name\":\"我\",\"url\":\"http://www.baidu.com/\"},{\"type\":\"view\",\"name\":\"关于小栈\",\"url\":\"http://www.baidu.com/\"}]}";
//        String result = HttpUtils.sendPost(url, param);
//        //根据返回值判断结果
//        if (StringUtils.isEmpty(result)) {
//            log.warn("创建菜单调用接口超时");
//            return Result.wrapErrorResult("","创建菜单调用接口超时");
//        } else {
//            WxApiResult wxApiResult = gson.fromJson(result, new TypeToken<WxApiResult>(){}.getType());
//            if("0".equals(wxApiResult.getErrcode())){
//                return Result.wrapSuccessfulResult("menu created successfully");
//            }else {
//                return Result.wrapErrorResult(wxApiResult.getErrcode(),wxApiResult.getErrmsg());
//            }
//        }
//    }

    @RequestMapping("getShareCode")
    public String getShareCode(ModelMap model,String code) {
        String openid = wxService.getOpenId(authUrl,code);
        System.out.println("openid from getShareCode:"+openid);
        if(StringUtils.isEmpty(openid)){
            Map cookieMap = this.getCookieMap();
            /*获取openId*/
            openid = (String) this.getViewRequestParam(cookieMap, "wxOpenId", "");
        }
        model.addAttribute("openid", openid);

        String QRCode;
        String existQRCode = redisHashService.hget("recommend_QRCode",openid);
        if(StringUtils.isNotEmpty(existQRCode)){
            QRCode = existQRCode;
        }else {
            QRCode = wxService.getQRCode("recommend_"+openid);
            redisHashService.hset("recommend_QRCode", openid, QRCode);
            log.info("create a new recommend QRCode by:"+openid);
        }

        model.addAttribute("QRCode",QRCode);

        UserInfo userInfo = wxService.getUserInfo(openid);

        model.addAttribute("nickname",userInfo.getNickname());
        model.addAttribute("headimgurl",userInfo.getHeadimgurl());


        return "wx/recommendCode";
    }

    @RequestMapping("toFollowPage")
    @ResponseBody
    public void toFollowPage() {
        try {
            response.sendRedirect(wxShareUrl);
        }catch (Exception e){

        }
    }

    @RequestMapping("queryHonorList")
    public String queryHonorList(ModelMap model) {
        List<RecommendView> honorList = recommendService.queryHonorList();
        List<RecommendView> exactList = new ArrayList<>();
        for(RecommendView recommendView:honorList){
            if(!recommendView.getFromUser().equals("oE1wbwllXnM4mUI5Y8NprNR6MdVA")){
                UserInfo userInfo = wxService.getUserInfo(recommendView.getFromUser());
                recommendView.setFromUserName(userInfo.getNickname());
                recommendView.setHeadimgurl(userInfo.getHeadimgurl());
                exactList.add(recommendView);
            }
        }
        model.addAttribute("honorList",exactList);
        return "wx/honorList";
    }

    @RequestMapping("recommendGuide")
    public String recommendGuide(ModelMap model) {

        return "wx/recommendGuide";
    }
}















