package com.nautybit.nautybee.web.pay;

import com.nautybit.nautybee.biz.goods.GoodsService;
import com.nautybit.nautybee.biz.order.OrderService;
import com.nautybit.nautybee.common.config.NautybeeSystemCfg;
import com.nautybit.nautybee.common.constant.pay.WechatPayConstants;
import com.nautybit.nautybee.common.param.pay.PayParam;
import com.nautybit.nautybee.common.param.pay.WechatPayParam;
import com.nautybit.nautybee.common.utils.DateUtils;
import com.nautybit.nautybee.common.utils.GenerationUtils;
import com.nautybit.nautybee.common.utils.PrintUtils;
import com.nautybit.nautybee.view.goods.GoodsView;
import com.nautybit.nautybee.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by UFO on 17/5/24.
 */
@Slf4j
public class BasePayController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private NautybeeSystemCfg nautybeeSystemCfg;
    @Autowired
    private GoodsService goodsService;

    @Value("${nautybee.wechat.appid}")
    private String wechatappid;
    @Value("${nautybee.wechat.mchid}")
    private String wechatmchid;

    private static final String goodsTitleKey = "goodsTitle";
    private static final String goodsDetailKey = "goodsDetail";

    /**
     * 微信支付订单有效时间段：30min内
     */
    private static Long rangeTime = 1000 * 60 * 30L;
    private static String WECHAT_PAY_NOTIFY_SERVICE = "/pay/wechatPayNotify";  //微信支付通知URL
    /**
     * 构造微信支付参数
     *
     * @param payParam
     * @return
     */
    //TODO:order-snapshot
    protected WechatPayParam makeWechatPayParam(PayParam payParam) {

        //商品描述信息
//        Map<String, String> goodsMap = queryGoodsInfo(payParam.getOrderList());
        BigDecimal totalFee = payParam.getTotalFee();
        if (totalFee.compareTo(BigDecimal.ZERO) < 1) {
            throw new RuntimeException("totalFee weird");
        }

        totalFee = totalFee.multiply(new BigDecimal(100)).setScale(0);
        //支付订单起止时间
        Date currentDate = new Date();
        Long startTime = currentDate.getTime();
        Long endTime = startTime + rangeTime;

        WechatPayParam wechatPayParam = new WechatPayParam();
        wechatPayParam.setAppid("wx47b77ec8ef89f1a7");
        wechatPayParam.setMch_id(wechatmchid);
        wechatPayParam.setDevice_info("1111");
        wechatPayParam.setNonce_str(GenerationUtils.generateRandomCode(16));
//        wechatPayParam.setBody(goodsMap.get(goodsTitleKey));
//        wechatPayParam.setDetail(goodsMap.get(goodsDetailKey));
        wechatPayParam.setBody("test_body");
        wechatPayParam.setDetail("test_detail");
        wechatPayParam.setAttach("test_attach");

        wechatPayParam.setOut_trade_no(orderService.generateTradeNo());


        wechatPayParam.setFee_type(WechatPayConstants.FEE_TYPE);
        wechatPayParam.setTotal_fee(totalFee.intValue());
//        wechatPayParam.setSpbill_create_ip(getIp());
        wechatPayParam.setSpbill_create_ip("116.62.35.66");


        wechatPayParam.setTime_start(DateUtils.dateFormat(currentDate, DateUtils.YMDHMS));
        wechatPayParam.setTime_expire(DateUtils.dateFormat(new Date(endTime), DateUtils.YMDHMS));
        wechatPayParam.setGoods_tag("test_goods_tag");
        wechatPayParam.setNotify_url(nautybeeSystemCfg.getServerUrl() + WECHAT_PAY_NOTIFY_SERVICE);
        wechatPayParam.setTrade_type("JSAPI");
        wechatPayParam.setProduct_id("test_product_id");
        wechatPayParam.setLimit_pay("no_credit");
        wechatPayParam.setOpenid("oqCGOwhAHwOMoj_QCkJErPCg-BV8");
        return wechatPayParam;
    }
    /**
     * 查询订单商品信息
     *
     * @param orderIdList
     * @return
     */
    //TODO:order-snapshot
    protected Map<String, String> queryGoodsInfo(List<Long> orderIdList) {

        Map<String, String> returnMap = new HashMap<String, String>();

        //查询订单的商品信息
        List<GoodsView> goodsViewList = goodsService.queryGoodsByOrderIdList(orderIdList);
        if (goodsViewList == null || goodsViewList.size() == 0) {
            log.error("queryGoodsInfo:订单号[" + PrintUtils.printList(orderIdList) + "]不存在任何商品!");
            return returnMap;
        }
        String firstGoodsName = goodsViewList.get(0).getGoodsName();
        if (StringUtils.isNotBlank(firstGoodsName)) {
            firstGoodsName = firstGoodsName.trim();
        }
        if (firstGoodsName.length() > 15) {
            firstGoodsName = firstGoodsName.substring(0,13) + "...";
        }
        String desc = firstGoodsName + "等" + goodsViewList.size() + "件商品";
        returnMap.put(goodsTitleKey, desc);
        returnMap.put(goodsDetailKey, desc);

        return returnMap;
    }

}
