package com.nautybit.nautybee.biz.prize.impl;

import java.util.List;

import com.nautybit.nautybee.biz.order.OrderService;
import com.nautybit.nautybee.biz.pay.WechatPayService;
import com.nautybit.nautybee.biz.sys.CommonResourcesService;
import com.nautybit.nautybee.biz.wx.WxService;
import com.nautybit.nautybee.common.constant.order.PrizeOriginEnum;
import com.nautybit.nautybee.common.constant.order.PrizeStatusEnum;
import com.nautybit.nautybee.common.constant.order.PrizeTypeEnum;
import com.nautybit.nautybee.common.constant.pay.WechatPayConstants;
import com.nautybit.nautybee.common.param.pay.RedBagPayResult;
import com.nautybit.nautybee.common.param.pay.SendRedBagParam;
import com.nautybit.nautybee.common.result.wx.UserInfo;
import com.nautybit.nautybee.entity.order.Order;
import com.nautybit.nautybee.entity.recommend.Recommend;
import com.nautybit.nautybee.entity.sys.CommonResources;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.prize.PrizeService;
import com.nautybit.nautybee.dao.prize.PrizeDao;
import com.nautybit.nautybee.entity.prize.Prize;


@Service
@Slf4j
public class PrizeServiceImpl extends BaseServiceImpl  implements PrizeService{

  @Autowired
  private PrizeDao prizeDao;
    @Autowired
    private CommonResourcesService commonResourcesService;
    @Autowired
    private WechatPayService wechatPayService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WxService wxService;

  public List<Prize> getAll() {
    return super.getAll(prizeDao);
  }


  public Prize getById(Long id) {
    return super.getById(prizeDao, id);
  }

  public boolean save(Prize prize) {
    return super.save(prizeDao, prize);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(prizeDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(prizeDao, ids);
  }

    @Override
    public void sendOrderRedBag(String openid,String orderSn){
        CommonResources commonResources = commonResourcesService.selectByKey("orderRedBag");
        SendRedBagParam sendRedBagParam = new SendRedBagParam();
        sendRedBagParam.setSend_name(commonResources.getValue());
        sendRedBagParam.setRe_openid(openid);
        sendRedBagParam.setTotal_amount(Integer.parseInt(commonResources.getValue1())*100);
        sendRedBagParam.setWishing(commonResources.getValue2());
        sendRedBagParam.setAct_name(commonResources.getValue3());
        sendRedBagParam.setRemark(commonResources.getValue4());
        try {
            RedBagPayResult redBagPayResult = wechatPayService.sendRedBag(sendRedBagParam);
            if(WechatPayConstants.SUCCESE.equals(redBagPayResult.getResult_code()) && WechatPayConstants.SUCCESE.equals(redBagPayResult.getResult_code())){
                //记录红包发放信息
                Order order = orderService.queryByOrderSn(orderSn);
                Prize prize = new Prize();
                prize.setDefaultBizValue();
                prize.setMchBillno(redBagPayResult.getMch_billno());
                prize.setPrizeOrigin(PrizeOriginEnum.ORDER.name());
                prize.setOriginId(order.getId());
                prize.setWxOpenId(openid);

                UserInfo userInfo = wxService.getUserInfo(openid);
                prize.setWxNickName(userInfo.getNickname());

                prize.setPrizeType(PrizeTypeEnum.amount.name());
                prize.setPrizeValue(String.valueOf(Integer.parseInt(commonResources.getValue1())));
                prize.setPrizeStatus(PrizeStatusEnum.WLQ.name());
                save(prize);
            }
        }catch (Exception e){
            log.error("下单发放红包异常:tradeNo[" + orderSn + "]",e);
        }
    }

    @Override
    public void sendRecommendRedBag(String openid,String recommendedUserName,Long recommendId,String orderSn){
        CommonResources commonResources = commonResourcesService.selectByKey("recommendRedBag");
        SendRedBagParam sendRedBagParam = new SendRedBagParam();
        sendRedBagParam.setSend_name(commonResources.getValue());
        sendRedBagParam.setRe_openid(openid);
        sendRedBagParam.setTotal_amount(Integer.parseInt(commonResources.getValue1()) * 100);
        sendRedBagParam.setWishing("您推荐的" + recommendedUserName + "已报名");
        sendRedBagParam.setAct_name(commonResources.getValue3());
        sendRedBagParam.setRemark(commonResources.getValue4());
        try {
            RedBagPayResult redBagPayResult = wechatPayService.sendRedBag(sendRedBagParam);
            if(WechatPayConstants.SUCCESE.equals(redBagPayResult.getResult_code()) && WechatPayConstants.SUCCESE.equals(redBagPayResult.getResult_code())){
                //记录红包发放信息
                Prize prize = new Prize();
                prize.setDefaultBizValue();
                prize.setMchBillno(redBagPayResult.getMch_billno());
                prize.setPrizeOrigin(PrizeOriginEnum.RECOMMEND.name());
                prize.setOriginId(recommendId);
                prize.setWxOpenId(openid);

                UserInfo userInfo = wxService.getUserInfo(openid);
                prize.setWxNickName(userInfo.getNickname());

                prize.setPrizeType(PrizeTypeEnum.amount.name());
                prize.setPrizeValue(String.valueOf(Integer.parseInt(commonResources.getValue1())));
                prize.setPrizeStatus(PrizeStatusEnum.WLQ.name());
                save(prize);
            }
        }catch (Exception e){
            log.error("推荐发放红包异常:tradeNo[" + orderSn + "]",e);
        }
    }

    @Override
    public boolean isInBlackList(String openId){
        CommonResources commonResources = commonResourcesService.selectByKey("redBagBlackList");
        String blackListStr = commonResources.getValue7();
        String[] blackList = StringUtils.split(blackListStr, ',');
        for(String str:blackList){
            if(str!=null && !"".equals(str)){
                if(str.equals(openId)){
                    return true;
                }
            }
        }
        return false;
    }
}
