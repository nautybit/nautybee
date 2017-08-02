package com.nautybit.nautybee.entity.order;



import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class OrderExt extends BaseEntity {

  private String orderSn;
  private String studentName;
  private String contactMobile;
  private Integer isAtSchool;
  private String schoolName;
  private String schoolGrade;
  private String schoolClass;
  private String teacherName;
  private String teacherMobile;
    private Long recommendId;

}
