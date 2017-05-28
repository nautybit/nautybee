package com.nautybit.nautybee.view.goods;

import com.nautybit.nautybee.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
public class GoodsView extends BaseEntity {

  private String goodsSn;
  private BigDecimal goodsPrice;
  private String goodsImg;
  private Integer saleVolume;
  private Long spuId;
  private Long propDetailId1;
  private Long propDetailId2;

    private String goodsName;

}
