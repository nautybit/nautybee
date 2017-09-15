package com.nautybit.nautybee.biz.wx;

import com.google.gson.Gson;
import com.nautybit.nautybee.biz.recommend.RecommendService;
import com.nautybit.nautybee.biz.sys.CommonResourcesService;
import com.nautybit.nautybee.common.param.wx.ArticleItem;
import com.nautybit.nautybee.common.param.wx.ArticleMessage;
import com.nautybit.nautybee.common.param.wx.TextMessage;
import com.nautybit.nautybee.common.result.wx.UserInfo;
import com.nautybit.nautybee.common.utils.MessageUtil;
import com.nautybit.nautybee.entity.recommend.Recommend;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by UFO on 17/7/16.
 */
@Service
@Slf4j
public class MessageService {

    @Autowired
    private WxService wxService;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private CommonResourcesService commonResourcesService;
    /**
     * 处理微信发来的请求
     * @param request
     * @return xml
     */
    public String processRequest(HttpServletRequest request) {
        String respStr = null;
        // 默认返回的文本消息内容
        String respContent = "未知的消息类型！";
        try {
            // 调用parseXml方法解析请求消息
            Map requestMap = MessageUtil.parseXml(request);
            // 发送方帐号
            String fromUserName = (String)requestMap.get("FromUserName");
            // 开发者微信号
            String toUserName = (String)requestMap.get("ToUserName");
            // 消息类型
            String msgType = (String)requestMap.get("MsgType");

            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                respContent = "您发送的是文本消息！";
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
            }
            // 语音消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是语音消息！";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                respContent = "您发送的是视频消息！";
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
                respContent = "您发送的是小视频消息！";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = (String)requestMap.get("Event");
                // 关注
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    String EventKey = (String)requestMap.get("EventKey");
                    if(StringUtils.isNotEmpty(EventKey) && EventKey.indexOf("recommend_")>-1){
                        String openId = EventKey.substring(EventKey.indexOf("recommend_")+"recommend_".length(),EventKey.length());
                        ArticleMessage articleMessage = handleRecommendEvent(fromUserName,openId,toUserName);
                        respStr = MessageUtil.messageToXml(articleMessage);
                    }else {
                        // 回复文本消息
                        TextMessage textMessage = new TextMessage();
                        textMessage.setToUserName(fromUserName);
                        textMessage.setFromUserName(toUserName);
                        textMessage.setCreateTime(new Date().getTime());
                        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                        respContent = "恭喜" + commonResourcesService.selectByKey("subscribeTip").getValue7();
                        // 设置文本消息的内容
                        textMessage.setContent(respContent);
                        respStr = MessageUtil.messageToXml(textMessage);
                    }
                }
                // 取消关注
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
                }
                // 扫描带参数二维码
                else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    String EventKey = (String)requestMap.get("EventKey");
                    if(EventKey.indexOf("recommend_")>-1){
                        String openId = EventKey.substring(EventKey.indexOf("recommend_")+"recommend_".length(),EventKey.length());
                        ArticleMessage articleMessage = handleRecommendEvent(fromUserName,openId,toUserName);
                        respStr = MessageUtil.messageToXml(articleMessage);
                    }
                }
                // 上报地理位置
                else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                    // TODO 处理上报地理位置事件
                }
                // 自定义菜单
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    String EventKey = (String)requestMap.get("EventKey");
                    if("recommend_code".equals(EventKey)){
                        ArticleMessage articleMessage = new ArticleMessage();
                        articleMessage.setToUserName(fromUserName);
                        articleMessage.setFromUserName(toUserName);
                        articleMessage.setCreateTime(new Date().getTime());
                        articleMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                        articleMessage.setArticleCount(1);

                        List<ArticleItem> articleItemList = new ArrayList<>();
                        ArticleItem articleItem = new ArticleItem();
                        articleItem.setTitle("title test");
                        articleItem.setDescription("description test");
                        articleItem.setPicUrl("");
                        articleItem.setUrl("http://www.baidu.com/");
                        articleItemList.add(articleItem);
                        articleMessage.setArticles(articleItemList);

                        respStr = MessageUtil.messageToXml(articleMessage);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respStr;
    }

    private ArticleMessage handleRecommendEvent(String beRecommendUser,String recommendUser,String appOpenId){
        ArticleMessage articleMessage = new ArticleMessage();
        articleMessage.setToUserName(beRecommendUser);
        articleMessage.setFromUserName(appOpenId);
        articleMessage.setCreateTime(new Date().getTime());
        articleMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        articleMessage.setArticleCount(1);

        List<ArticleItem> articleItemList = new ArrayList<>();
        ArticleItem articleItem = new ArticleItem();

        //自己不能推荐自己
        UserInfo beRecommendUserInfo = wxService.getUserInfo(beRecommendUser);
        UserInfo recommendUserInfo = wxService.getUserInfo(recommendUser);

        if(beRecommendUser.equals(recommendUser)){
            articleItem.setTitle("恭喜");
            articleItem.setDescription(commonResourcesService.selectByKey("subscribeTip").getValue7());
        }else {
            Recommend recommend = recommendService.selectByToUser(beRecommendUser);
            if(recommend!=null){
                recommend.setDefaultBizValue();
                recommend.setIsValid("N");
                recommend.setRemark("被新的推荐人：【"+recommendUser+"】取代");
                recommendService.save(recommend);
            }
            Recommend newOne = new Recommend();
            newOne.setDefaultBizValue();
            newOne.setFromUser(recommendUser);
            newOne.setFromUserName(recommendUserInfo.getNickname());
            newOne.setToUser(beRecommendUser);
            newOne.setToUserName(beRecommendUserInfo.getNickname());
            newOne.setIsValid("Y");
            recommendService.save(newOne);

            String recommendSource = recommendUserInfo.getNickname();
            articleItem.setTitle(recommendSource+" 推荐了您");
            log.debug("recommendSource:"+recommendSource);
            articleItem.setDescription("恭喜" + commonResourcesService.selectByKey("subscribeTip").getValue7());
        }
        articleItem.setPicUrl("");
        articleItem.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx47b77ec8ef89f1a7&redirect_uri=http%3A%2F%2Fwww.bitstack.cn%2Fnautybee%2Fwx%2Fgoods%2FgetSpuList&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
        articleItemList.add(articleItem);
        articleMessage.setArticles(articleItemList);

        return articleMessage;

    }


}
