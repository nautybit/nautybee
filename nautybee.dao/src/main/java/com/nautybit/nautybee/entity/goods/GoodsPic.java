package com.nautybit.nautybee.entity.goods;



import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class GoodsPic extends BaseEntity {

  private Long spuId;
  private String imgUrl;
  private String imgDesc;
  private Integer imgOrder;
  private Integer isMain;

}
