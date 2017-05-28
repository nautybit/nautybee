package com.nautybit.nautybee.common.param.pay;

import lombok.Data;

import java.util.List;

/**
 * 支付参数
 * Created by Minutch on 15/7/21.
 */
@Data
public class PayParam extends BasePayParam{

    private List<Long> orderList;

}
