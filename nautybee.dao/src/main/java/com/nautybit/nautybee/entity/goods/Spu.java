package com.nautybit.nautybee.entity.goods;

import java.math.BigDecimal;


import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class Spu extends BaseEntity {

  private String spuSn;
  private Long storeId;
  private Long catId;
  private String goodsName;
  private BigDecimal goodsPrice;
  private BigDecimal alipayDiscount;
  private BigDecimal wechatpayDiscount;
  private BigDecimal unionpayDiscount;
  private String unit;
  private String spuImg;
  private String goodsType;
  private String goodsNickName;
  private Integer saleVolume;
  private Integer visitNum;
  private String goodsBrief;
  private String goodsDesc;
  private String keywords;
  private Integer buyMin;
  private Integer buyStep;
  private String stepPriceCfg;
  private String remark;

}
