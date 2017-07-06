package com.nautybit.nautybee.common.result.order;

import lombok.Data;

/**
 * 微信支付请求参数
 * Created by Minutch on 15/7/24.
 */
@Data
public class WechatPayRequestVO {
    public String appId;
    public String partnerid;
    public String prepayid;
    public String packageValue;
    public String nonceStr;
    public Long timeStamp;
    public String sign;
    public String tradeNo;
    public String payId;
    public String signType;
}
