package com.nautybit.nautybee.view.recommend;


import com.nautybit.nautybee.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
public class RecommendView extends BaseEntity {

  private String toUser;
    private String toUserName;
    private String fromUser;
    private String fromUserName;
  private String isValid;
  private String remark;

    private Date orderGmtCreate;
    private String goodsName;
    private String gmtCreateStr;
    private String orderGmtCreateStr;
    private String studentName;
}
