package com.nautybit.nautybee.common.param.pay;

import lombok.Data;

/**
 * Created by Minutch on 16/1/13.
 */
@Data
public class PayNotifyParam {

    //商户交易号
    private String tradeNo;
    //支付平台交易号
    private String paySystemTradeNo;
    //支付状态
    private String status;
    //通知唯一码
    private String notifyId;
}
