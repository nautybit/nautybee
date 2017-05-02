package com.nautybit.nautybee.entity.user;

import java.util.Date;


import lombok.Data;
import lombok.EqualsAndHashCode;

import com.nautybit.nautybee.entity.base.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class User extends BaseEntity {

  private String userName;
  private String password;
  private Integer sex;
  private Date birthday;
  private String nickName;
  private String wechat;
  private String mobilePhone;
  private Integer isSeller;
  private Integer memberLevel;
  private String userPhoto;
  private String remark;

}
