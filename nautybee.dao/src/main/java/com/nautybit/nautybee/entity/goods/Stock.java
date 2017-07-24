package com.nautybit.nautybee.entity.goods;



import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class Stock extends BaseEntity {

  private Long goodsId;
  private Long spuId;
  private Long goodsNum;

}
