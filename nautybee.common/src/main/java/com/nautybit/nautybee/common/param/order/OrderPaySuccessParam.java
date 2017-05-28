package com.nautybit.nautybee.common.param.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Minutch on 16/5/17.
 */
@Data
public class OrderPaySuccessParam {
    private List<Long> orderIdList;
    private BigDecimal totalFee;
    private String payId;

    public OrderPaySuccessParam(List<Long> orderIdList, BigDecimal totalFee, String payId) {
        this.orderIdList = orderIdList;
        this.totalFee = totalFee;
        this.payId = payId;
    }
}
