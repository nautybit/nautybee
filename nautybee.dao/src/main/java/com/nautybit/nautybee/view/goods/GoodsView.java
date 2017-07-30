package com.nautybit.nautybee.view.goods;

import com.nautybit.nautybee.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

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
    private String propName1;
    private String detailName1;
    private String propName2;
    private String detailName2;
    private Integer goodsNum;

    private List<GoodsAttributeView> goodsAttributeViewList;
}
