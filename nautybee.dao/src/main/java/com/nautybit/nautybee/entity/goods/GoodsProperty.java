package com.nautybit.nautybee.entity.goods;



import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class GoodsProperty extends BaseEntity {

  private Long spuId;
  private String propName;
  private Integer propLevel;

}
