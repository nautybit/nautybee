package com.nautybit.nautybee.entity.goods;



import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class Category extends BaseEntity {

  private Long storeId;
  private String catName;
  private String catDesc;
  private Long catOrder;

}