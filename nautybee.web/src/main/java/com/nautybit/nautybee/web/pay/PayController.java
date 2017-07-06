package com.nautybit.nautybee.web.pay;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.nautybit.nautybee.biz.pay.WechatPayService;
import com.nautybit.nautybee.common.constant.pay.WechatPayConstants;
import com.nautybit.nautybee.common.param.pay.PayParam;
import com.nautybit.nautybee.common.param.pay.WechatPayNotifyResult;
import com.nautybit.nautybee.common.param.pay.WechatPayParam;
import com.nautybit.nautybee.common.param.pay.WechatPrePayResult;
import com.nautybit.nautybee.common.result.ErrorCode;
import com.nautybit.nautybee.common.result.Result;
import com.nautybit.nautybee.common.result.order.WechatPayRequestVO;
import com.nautybit.nautybee.common.utils.Base64Utils;
import com.nautybit.nautybee.common.utils.Md5Utils;
import com.nautybit.nautybee.common.utils.pay.PayUtils;
import com.nautybit.nautybee.common.utils.pay.SignUtils;
import com.nautybit.nautybee.common.utils.wechatpay.WechatPayUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Minutch on 15/7/21.
 */
@Controller
@RequestMapping("pay")
@Slf4j
public class PayController extends BasePayController {

    @Autowired
    private WechatPayService wechatPayService;

    @RequestMapping("auth")
    @ResponseBody
    public Result<?> pay(@RequestBody PayParam payParam) {
        try {
            return payHandler(payParam);
        } catch (Exception e) {
            log.error("pay:handler error.", e);
            return Result.wrapErrorResult("", e.getMessage());
        }
    }
    private Result<?> payHandler(PayParam payParam) {
        payParam.setUserId(-1l);
        /*******************************************************
         * 1.微信支付
         *******************************************************/
        //构造微信支付参数
        WechatPayParam wechatPayParam = makeWechatPayParam(payParam);
        //调用微信统一支付接口
        WechatPrePayResult wechatPrePayResult = wechatPayService.unifiedOrder(payParam, wechatPayParam);

        //返回统一下单的结果到客户端
        if (wechatPrePayResult.getReturn_code() != null && wechatPrePayResult.getReturn_code().equals(WechatPayConstants.FAIL)) {
            log.error("pay:" + wechatPrePayResult.getReturn_code() + "=" + wechatPrePayResult.getReturn_msg());
            return Result.wrapErrorResult(wechatPrePayResult.getReturn_code(), wechatPrePayResult.getReturn_msg());
        } else if (wechatPrePayResult.getResult_code() != null && wechatPrePayResult.getResult_code().equals(WechatPayConstants.FAIL)) {
            log.error("pay:" + wechatPrePayResult.getResult_code() + "=" + wechatPrePayResult.getErr_code_des());
            return Result.wrapErrorResult(wechatPrePayResult.getResult_code(), wechatPrePayResult.getErr_code_des());
        } else {
            //构造APP客户端微信支付需要的参数
            WechatPayRequestVO wechatPayRequestVO = new WechatPayRequestVO();
            wechatPayRequestVO.setAppId(wechatPayParam.getAppid());
            wechatPayRequestVO.setNonceStr(wechatPayParam.getNonce_str());
            wechatPayRequestVO.setPackageValue("prepay_id="+wechatPrePayResult.getPrepay_id());
            wechatPayRequestVO.setTimeStamp(new Date().getTime() / 1000);
            wechatPayRequestVO.setSignType("MD5");

            SortedMap<String, String> paramMap = PayUtils.payParamToMap(wechatPayRequestVO,false);
            String resultString = SignUtils.createLinkStringWithEscape(paramMap);

            resultString = resultString.replace("packageValue","package");

            String sign = Md5Utils.getMD5Str(resultString+"&key=44ris19u8nblx42vugypcijvca1mgjvl").toUpperCase();
            wechatPayRequestVO.setPartnerid(wechatPayParam.getMch_id());
            wechatPayRequestVO.setPrepayid(wechatPrePayResult.getPrepay_id());
            wechatPayRequestVO.setSign(sign);
            log.info("pay:wechatPay ok");
            return Result.wrapSuccessfulResult(wechatPayRequestVO);
        }
    }


    /**
     * 微信支付notifyUrl
     *
     * @return
     */
    @RequestMapping("wechatPayNotify")
    @ResponseBody
    public String wechatPayNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("wechatPayNotify: 微信支付通知返回结果！");
        Map<String,String> notifyMap = new HashMap<>();
        try {
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String resultStr = new String(outSteam.toByteArray(), "utf-8");

            log.info("wechatPayNotify:resultStr=" + resultStr);


            notifyMap = WechatPayUtils.doXMLParse(resultStr);
            if (notifyMap != null && notifyMap.size() > 0) {
                //交易状态 0000表示成功
                String respCode = notifyMap.get("result_code");
                if (WechatPayConstants.SUCCESE.equals(respCode)) {
//                    wechatPayService.payNotify(notifyMap);
                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    log.info("wechatPayNotify:responseMap="+resultStr);


                    String resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                    BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                    out.write(resXml.getBytes());
                    out.flush();
                    out.close();


                    return resXml;
                }
            }
        } catch (Exception e) {
            log.error("wechatPayNotify:handle error.", e);
        }
        return null;
    }


//    public boolean verifyWeixinNotify(Map<Object, Object> map) {
//        SortedMap<String, String> parameterMap = new TreeMap<String, String>();
//        String sign = (String) map.get("sign");
//        for (Object keyValue : map.keySet()) {
//            if (!keyValue.toString().equals("sign")) {
//                parameterMap.put(keyValue.toString(), map.get(keyValue).toString());
//            }
//        }
//        String createSign = pay.getSign(parameterMap);
//        if (createSign.equals(sign)) {
//            return true;
//        } else {
//            log.error("微信支付  ~~~~~~~~~~~~~~~~验证签名失败");
//            return false;
//        }
//    }

}
















