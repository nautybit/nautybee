package com.nautybit.nautybee.entity.goods;

import java.math.BigDecimal;


import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class Goods extends BaseEntity {

  private String goodsSn;
  private BigDecimal goodsPrice;
  private String goodsImg;
  private Integer saleVolume;
  private Long spuId;
  private Long propDetailId1;
  private Long propDetailId2;
    private Long showOrder;

}
