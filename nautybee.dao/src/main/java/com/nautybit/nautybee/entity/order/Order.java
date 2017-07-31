package com.nautybit.nautybee.entity.order;

import java.math.BigDecimal;


import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class Order extends BaseEntity {

  private String orderSn;
  private Long userId;
    private String wxOpenId;
  private Long storeId;
  private String orderStatus;
  private Integer buyerEvaluate;
  private String payStatus;
  private String payId;
  private BigDecimal orderAmount;
  private BigDecimal baseOrderAmount;
  private String orderAmountType;
  private Integer changeAmountTimes;
  private BigDecimal couponFee;
  private BigDecimal activityDiscountFee;
  private String remark;
  private BigDecimal receiptsAmount;
  private String firstOrder;
  private String cancelReasonType;
  private String cancelReason;

}
