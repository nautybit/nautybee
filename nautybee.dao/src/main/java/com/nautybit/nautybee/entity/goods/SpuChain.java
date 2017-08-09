package com.nautybit.nautybee.entity.goods;



import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class SpuChain extends BaseEntity {

  private Long headId;
  private Long nodeId;
  private String status;
  private String orderStr;
  private String memo;

}
