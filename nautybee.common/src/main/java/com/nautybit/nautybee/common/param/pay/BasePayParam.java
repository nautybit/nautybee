package com.nautybit.nautybee.common.param.pay;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Created by Minutch on 16/3/10.
 */
@Data
public class BasePayParam {

    private BigDecimal totalFee;
    private Long userId;
    //notifyUrl
    private String notifyUrl;
    private String tradeNo;
    /**
     * 支付来源：H5，PublicNumber,Web
     */
    private String payOrigin;
    /**
     * openid
     */
    private String wxOpenid;
}
