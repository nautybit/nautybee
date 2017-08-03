package com.nautybit.nautybee.entity.prize;



import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class Prize extends BaseEntity {
    private String mchBillno;
  private String prizeOrigin;
  private Long originId;
  private Long userId;
    private String wxOpenId;
    private String wxNickName;
    private String prizeType;
  private String prizeValue;
  private String prizeStatus;
  private String prizeRemark;

}
