package com.nautybit.nautybee.common.result.order;

import lombok.Data;

/**
 * 微信支付请求参数
 * Created by Minutch on 15/7/24.
 */
@Data
public class WechatPayRequestVO {
    public String appid;
    public String partnerid;
    public String prepayid;
    public String packageValue;
    public String noncestr;
    public Long timestamp;
    public String sign;
    public String tradeNo;
    public String payId;
}
