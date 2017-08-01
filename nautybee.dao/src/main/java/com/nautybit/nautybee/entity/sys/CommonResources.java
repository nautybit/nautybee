package com.nautybit.nautybee.entity.sys;



import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class CommonResources extends BaseEntity {

  private String appName;
  private String type;
    private String key;
  private String value;
  private String value1;
  private String value2;
  private String value3;
  private String value4;
  private String value5;
  private String value6;
  private String value7;
  private String orderIdx;

}
