package com.nautybit.nautybee.entity.goods;



import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class GoodsPropertyDetail extends BaseEntity {

  private Long propId;
  private String detailName;
  private String goodsImg;
  private String remark;
    private Long showOrder;

}
