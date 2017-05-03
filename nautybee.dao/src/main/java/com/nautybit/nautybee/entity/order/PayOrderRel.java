package com.nautybit.nautybee.entity.order;



import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class PayOrderRel extends BaseEntity {

  private String tradeNo;
  private Long orderId;
  private String status;
  private String remark;
  private String userType;

}
