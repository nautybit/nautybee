package com.nautybit.nautybee.biz.pay;

import com.google.gson.Gson;
import com.nautybit.nautybee.biz.order.PayOrderService;
import com.nautybit.nautybee.common.constant.pay.PayConstants;
import com.nautybit.nautybee.common.constant.pay.WechatPayConstants;
import com.nautybit.nautybee.common.param.pay.PayNotifyParam;
import com.nautybit.nautybee.common.param.pay.PayParam;
import com.nautybit.nautybee.common.param.pay.WechatPayParam;
import com.nautybit.nautybee.common.param.pay.WechatPrePayResult;
import com.nautybit.nautybee.common.utils.Md5Utils;
import com.nautybit.nautybee.common.utils.pay.PayUtils;
import com.nautybit.nautybee.common.utils.pay.SignUtils;
import com.nautybit.nautybee.common.utils.wechatpay.WechatPayUtils;
import com.nautybit.nautybee.entity.order.PayNotify;
import com.nautybit.nautybee.entity.order.PayOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 微信支付服务
 * Created by Minutch on 15/7/20.
 */
@Service("wechatPayService")
@Slf4j
public class WechatPayService extends BasePayService {
    /**
     * 生成预支付订单API
     */
    private static final String prePayOrder = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    @Autowired
    private PayOrderService payOrderService;

    Gson gson = new Gson();

    /**
     * 微信统一支付订单
     */
    public WechatPrePayResult unifiedOrder(PayParam payParam,WechatPayParam wechatPayParam) {

        /***************************************************************************
         * 1.生成预支付订单
         ***************************************************************************/
        SortedMap<String, String> paramMap = PayUtils.payParamToMap(wechatPayParam);
        //生成签名参数
        String resultString = SignUtils.createLinkStringWithEscape(paramMap)+"&key=44ris19u8nblx42vugypcijvca1mgjvl";
        System.out.println(resultString);
        String sign = Md5Utils.getMD5Str(resultString).toUpperCase();
        paramMap.put("sign", sign);
        wechatPayParam.setSign(sign);

        //将map转化成微信预支付订单接口需要的xml格式
        String requestParam = WechatPayUtils.getXml(paramMap);
        log.info("unifiedOrder:微信支付参数="+requestParam);
        //调用微信预支付接口
        String response = WechatPayUtils.httpsRequest(prePayOrder, "POST", requestParam);
        log.info("unifiedOrder:统一下单结果=" + response);
        //处理接口返回信息
        WechatPrePayResult wechatPrePayResult;
        if (StringUtils.isBlank(response)) {
        	wechatPrePayResult = new WechatPrePayResult();
            log.warn("unifiedOrder:请求超时.");
            wechatPrePayResult.setReturn_code(WechatPayConstants.FAIL);
            wechatPrePayResult.setReturn_msg("请求微信统一下单接口超时.");
        } else {
            Map<String, String> responseMap = WechatPayUtils.doXMLParse(response);
            String json = gson.toJson(responseMap);
            wechatPrePayResult = gson.fromJson(json, WechatPrePayResult.class);
            log.info("unifiedOrder:wechatPrePayResult:" + wechatPrePayResult);
        }
        /*******************************************************************************************
         * 2.创建支付订单关联的订单信息
         *******************************************************************************************/
        //payOrderRelService.createOrderList(wechatPayParam.getOut_trade_no(),payParam.getOrderList());
//        payOrderRelService.createOrderList(wechatPayParam.getOut_trade_no(), payParam.getOrderList());
        /*******************************************************************************************
         * 3.发送统一下单消息到消息服务器，异步记录本次支付请求的参数
         *******************************************************************************************/
        PayOrder payOrder = new PayOrder();
        payOrder.setDefaultBizValue();
        payOrder.setTradeNo(wechatPayParam.getOut_trade_no());
        payOrder.setTotalFee(BigDecimal.valueOf(Double.valueOf(wechatPayParam.getTotal_fee()) / 100));
        payOrder.setRequestParam(requestParam);
        payOrder.setResponseParam(response);
        if (wechatPrePayResult.getReturn_code() != null && wechatPrePayResult.getReturn_code().equals(WechatPayConstants.FAIL)) {
            payOrder.setStatus(wechatPrePayResult.getReturn_code());
            payOrder.setStatusMessage(wechatPrePayResult.getReturn_msg());
        } else if (wechatPrePayResult.getResult_code() != null && wechatPrePayResult.getResult_code().equals(WechatPayConstants.FAIL)) {
            payOrder.setStatus(wechatPrePayResult.getResult_code());
            payOrder.setStatusMessage(wechatPrePayResult.getErr_code() + ":" + wechatPrePayResult.getErr_code_des());
        } else {
            payOrder.setStatus(WechatPayConstants.SUCCESE);
        }
        payOrderService.save(payOrder);


        return wechatPrePayResult;
    }

    public String payNotify(Map<String,String> notifyParam){
        //商户订单号
        String tradeNo = notifyParam.get("orderId");
        //交易状态 0000表示成功
        String tradeStatus = notifyParam.get("respCode");
        //transactionId
        String notifyId = notifyParam.get("transactionId");
        //txnSeqId
        String paySystemTradeNo = notifyParam.get("txnSeqId");

        /**********************************************************************************
         * 1.判断该条消息是否已经处理过,如果处理过，直接返回
         **********************************************************************************/
        PayNotify payNotify = payNotifyService.queryByNotifyIdAndStatus(notifyId, tradeStatus);
        if (payNotify != null) {
            log.info("payNotify:the notifyId[" + notifyId + "] has been handle!");
            return null;
        }
        log.info("payNotify:tradeNo[" + tradeNo + "],notifyId[" + notifyId + "] the tradeStatus is " + tradeStatus);



        /**********************************************************************************
         * 2.保存notify信息
         **********************************************************************************/
        PayNotifyParam payNotifyParam = new PayNotifyParam();
        payNotifyParam.setNotifyId(notifyId);
        payNotifyParam.setPaySystemTradeNo(paySystemTradeNo);
        payNotifyParam.setStatus(tradeStatus);
        payNotifyParam.setTradeNo(tradeNo);
        savePayNotifyInfo(notifyParam, payNotifyParam);
        

        /**********************************************************************************
         * 3.如果是付款成功，修改PayOrder状态； AlipayConstants.TRADE_FINISHED.equals(tradeStatus) || AlipayConstants.TRADE_SUCCESS.equals(tradeStatus)
         **********************************************************************************/
        if (WechatPayConstants.CREDIT_SUCCESS_CODE.equals(tradeStatus)) {
            PayOrder payOrder = new PayOrder();
            payOrder.setTradeNo(tradeNo);
            payOrder.setStatus(tradeStatus);
            payOrderService.updateStatusByTradeNo(payOrder.getTradeNo(), payOrder.getStatus());
        }
        /**********************************************************************************
         * 4.交易成功，根据TradeNo找到支付的订单列表;更新订单的状态，保存到数据库
         **********************************************************************************/
        //支付成功
        if (WechatPayConstants.CREDIT_SUCCESS_CODE.equals(tradeStatus)) {
            updateOrderStatus(tradeNo, PayConstants.PAY_SUCCESS);
        }
        return null;
    }


    


}


