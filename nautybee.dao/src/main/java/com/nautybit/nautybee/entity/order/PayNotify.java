package com.nautybit.nautybee.entity.order;



import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class PayNotify extends BaseEntity {

  private String tradeNo;
  private String paySystemTradeNo;
  private String notifyId;
  private String status;
  private String statusMessage;
  private String requestParam;
  private String responseParam;
  private String remark;
  private String userType;

}
