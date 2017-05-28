package com.nautybit.nautybee.web.pay;

import com.nautybit.nautybee.biz.pay.WechatPayService;
import com.nautybit.nautybee.common.constant.pay.WechatPayConstants;
import com.nautybit.nautybee.common.param.pay.PayParam;
import com.nautybit.nautybee.common.param.pay.WechatPayParam;
import com.nautybit.nautybee.common.param.pay.WechatPrePayResult;
import com.nautybit.nautybee.common.result.ErrorCode;
import com.nautybit.nautybee.common.result.Result;
import com.nautybit.nautybee.common.result.order.WechatPayRequestVO;
import com.nautybit.nautybee.common.utils.Md5Utils;
import com.nautybit.nautybee.common.utils.pay.PayUtils;
import com.nautybit.nautybee.common.utils.pay.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
            wechatPayRequestVO.setAppid(wechatPayParam.getAppid());
            wechatPayRequestVO.setNoncestr(wechatPayParam.getNonce_str());
            wechatPayRequestVO.setPackageValue("Sign=WXPay");
            wechatPayRequestVO.setPartnerid(wechatPayParam.getMch_id());
            wechatPayRequestVO.setPrepayid(wechatPrePayResult.getPrepay_id());
            wechatPayRequestVO.setTimestamp(new Date().getTime() / 1000);
            SortedMap<String, String> paramMap = PayUtils.payParamToMap(wechatPayRequestVO);
            String resultString = SignUtils.createLinkStringWithEscape(paramMap);
            String sign = Md5Utils.getMD5Str(resultString).toUpperCase();
            wechatPayRequestVO.setSign(sign);
            log.info("pay:wechatPay ok");
            return Result.wrapSuccessfulResult(wechatPayRequestVO);
        }
    }
}
















