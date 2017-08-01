package com.nautybit.nautybee.common.param.pay;

import lombok.Data;

/**
 * 红包发放返回结果
 * Created by UFO on 17/8/1.
 */
@Data
public class RedBagPayResult {

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
    /**
     * 商户订单号（每个订单号必须唯一）组成：mch_id+yyyymmdd+10位一天内不能重复的数字
     */
    private String mch_billno;
    /**
     * 调用接口提交的商户号
     */
    private String mch_id;
    /**
     * 调用接口提交的公众账号ID（企业号corpid即为此appId）
     */
    private String wxappid;
    /**
     * 接受收红包的用户,用户在wxappid下的openid
     */
    private String re_openid;
    /**
     * 付款金额，单位分
     */
    private Integer total_amount;
    /**
     * 红包订单的微信单号
     */
    private String send_listid;

}
