package com.nautybit.nautybee.view.goods;


import com.nautybit.nautybee.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class GoodsPropertyDetailView extends BaseEntity {

  private Long propId;
  private String detailName;
  private String goodsImg;
  private String remark;
    private Long showOrder;

    private String propName;
}
