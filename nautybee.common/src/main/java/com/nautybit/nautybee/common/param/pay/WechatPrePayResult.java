package com.nautybit.nautybee.common.param.pay;

import lombok.Data;

/**
 * 微信预支付返回结果
 * Created by Minutch on 15/7/22.
 */
@Data
public class WechatPrePayResult {

    /**
     * SUCCESS/FAIL
     * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     */
    private String return_code;
    /**
     * 返回信息，如非空，为错误原因
     * 签名失败
     * 参数格式校验错误
     */
    private String return_msg;

    //以下字段在return_code为SUCCESS的时候有返回
    /**
     * 调用接口提交的公众账号ID（企业号corpid即为此appId）
     */
    private String appid;
    /**
     * 调用接口提交的商户号
     */
    private String mch_id;
    /**
     * 调用接口提交的终端设备号
     */
    private String device_info;
    /**
     * 微信返回的随机字符串
     */
    private String nonce_str;
    /**
     * 微信返回的签名
     */
    private String sign;
    /**
     * SUCCESS/FAIL
     * 业务结果
     */
    private String result_code;
    /**
     * 错误码
     */
    private String err_code;
    /**
     * 错误返回的信息描述
     */
    private String err_code_des;

    //以下字段在return_code 和result_code都为SUCCESS的时候有返回
    /**
     * 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP
     */
    private String trade_type;
    /**
     * 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
     */
    private String prepay_id;
    /**
     * trade_type为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付
     */
    private String code_url;

}
