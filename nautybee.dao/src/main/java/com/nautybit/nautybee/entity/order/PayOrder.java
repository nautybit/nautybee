package com.nautybit.nautybee.entity.order;

import java.math.BigDecimal;


import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class PayOrder extends BaseEntity {

  private String tradeNo;
  private BigDecimal totalFee;
  private String payId;
  private String status;
  private String statusMessage;
  private String requestParam;
  private String responseParam;
  private String remark;
  private String userType;

}
