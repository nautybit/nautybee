package com.nautybit.nautybee.entity.order;

import java.math.BigDecimal;


import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class OrderGoods extends BaseEntity {

  private Long orderId;
  private Long goodsId;
  private Long spuId;
  private Long propDetailId1;
  private Long propDetailId2;
  private String propDetailName1;
  private String propDetailName2;
  private String discountDesc;
  private String goodsName;
  private String unit;
  private String goodsImg;
  private Long goodsNumber;
  private BigDecimal salesAmount;
  private BigDecimal receiptsAmount;
  private BigDecimal avgPrice;
  private BigDecimal oriPrice;
  private BigDecimal alipayDiscount;
  private BigDecimal wechatpayDiscount;
  private BigDecimal unionpayDiscount;
  private BigDecimal basePrice;
  private Integer returnCount;
  private Long storeId;
  private String goodsSn;

}
