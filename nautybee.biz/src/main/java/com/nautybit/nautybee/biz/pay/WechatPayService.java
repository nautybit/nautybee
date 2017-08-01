package com.nautybit.nautybee.biz.pay;

import com.google.gson.Gson;
import com.nautybit.nautybee.biz.order.PayOrderService;
import com.nautybit.nautybee.biz.redis.RedisStringService;
import com.nautybit.nautybee.common.constant.OrderConstants;
import com.nautybit.nautybee.common.constant.pay.PayConstants;
import com.nautybit.nautybee.common.constant.pay.WechatPayConstants;
import com.nautybit.nautybee.common.param.pay.*;
import com.nautybit.nautybee.common.result.Result;
import com.nautybit.nautybee.common.utils.DateUtils;
import com.nautybit.nautybee.common.utils.GenerationUtils;
import com.nautybit.nautybee.common.utils.Md5Utils;
import com.nautybit.nautybee.common.utils.pay.PayUtils;
import com.nautybit.nautybee.common.utils.pay.SignUtils;
import com.nautybit.nautybee.common.utils.wechatpay.WechatPayUtils;
import com.nautybit.nautybee.entity.order.PayNotify;
import com.nautybit.nautybee.entity.order.PayOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.KeyStore;
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
    /**
     * 发放红包API
     */
    private static final String sendRedBag = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";

    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private RedisStringService redisStringService;
    @Value("${nautybee.wechat.mchkey}")
    private String mchkey;
    @Value("${nautybee.wechat.mchid}")
    private String wechatmchid;
    @Value("${nautybee.wechat.appid}")
    private String wechatappid;

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
        String resultString = SignUtils.createLinkStringWithEscape(paramMap)+"&key="+mchkey;
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
        payOrderRelService.createOrderList(wechatPayParam.getOut_trade_no(), payParam.getOrderList());
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

    /**
     * 发放微信红包
     */
    public RedBagPayResult sendRedBag(SendRedBagParam sendRedBagParam) throws Exception{

        sendRedBagParam.setNonce_str(GenerationUtils.generateRandomCode(16));
        sendRedBagParam.setMch_billno(generateMch_billno());
        sendRedBagParam.setMch_id(wechatmchid);
        sendRedBagParam.setWxappid(wechatappid);
//        sendRedBagParam.setSend_name("红包发放测试sendName");
//        sendRedBagParam.setRe_openid("oE1wbwoqKSISaaDyoV_VFBl9oXnw");
//        sendRedBagParam.setTotal_amount(100);
        sendRedBagParam.setTotal_num(1);
//        sendRedBagParam.setWishing("红包发放测试wishing");
        sendRedBagParam.setClient_ip(null);
//        sendRedBagParam.setAct_name("红包发放测试actName");
//        sendRedBagParam.setRemark("红包发放测试remark");


        SortedMap<String, String> paramMap = PayUtils.payParamToMap(sendRedBagParam);
        //生成签名参数
        String resultString = SignUtils.createLinkStringWithEscape(paramMap)+"&key="+mchkey;
        System.out.println(resultString);
        String sign = Md5Utils.getMD5Str(resultString).toUpperCase();
        paramMap.put("sign", sign);
        //将map转化成微信预支付订单接口需要的xml格式
        String requestParam = WechatPayUtils.getXml(paramMap);
        log.info("红包发放参数="+requestParam);


        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File("/usr/local/cert/apiclient_cert.p12"));
        try {
            keyStore.load(instream, wechatmchid.toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, wechatmchid.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

        RedBagPayResult redBagPayResult = new RedBagPayResult();
        try {

            HttpPost httppost = new HttpPost(sendRedBag);
            StringEntity s = new StringEntity(requestParam,"UTF-8");
            s.setContentEncoding("UTF-8");
            s.setContentType("text/xml");//发送json数据需要设置contentType
            httppost.setEntity(s);
            CloseableHttpResponse resp = httpclient.execute(httppost);
            try {
                HttpEntity entity = resp.getEntity();

                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"utf-8"));
                    String text;
                    String xmlString = "";
                    while ((text = bufferedReader.readLine()) != null) {
                        xmlString += text;
                    }

                    //return_code与result_code都为success代表发放红包成功
                    log.info("红包发放结果=" + xmlString);
                    Map<String, String> responseMap = WechatPayUtils.doXMLParse(xmlString);
                    String json = gson.toJson(responseMap);
                    redBagPayResult = gson.fromJson(json, RedBagPayResult.class);
                }
                EntityUtils.consume(entity);
            } finally {
                resp.close();
            }
        } finally {
            httpclient.close();
        }
        return redBagPayResult;
    }


    /**
     * tradeNo生成：该no用于标识支付订单的唯一性
     * YP20150722444409837287
     * (YP)+(yyyyMMdd)+(3位随机数)+(每日序列，每天凌晨从0开始计数，10位)
     *
     * @return
     */
    public String generateMch_billno() {
        String yyyyMMdd = DateUtils.dateFormat(new Date(), DateUtils.YMD);
        String sequence = GenerationUtils.generateFixedLengthDigital(getSequence(OrderConstants.REDIS_GEN_MCH_BILL_NO + yyyyMMdd), 10);
        String mch_billno = wechatmchid + yyyyMMdd + sequence;
        log.info("generateMch_billno:生成一枚mch_billno[" + mch_billno + "]");
        return mch_billno;
    }

    public long getSequence(String sequenceKey) {
        Long sequenceId = redisStringService.incr(sequenceKey);
        log.info("getSequence:sequence=" + sequenceId);
        return sequenceId;
    }


}


