package com.nautybit.nautybee.entity.recommend;



import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class Recommend extends BaseEntity {

  private String toUser;
  private String fromUser;
  private String isValid;
  private String remark;

}