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
  private String email;
  private Integer sex;
  private Date birthday;
  private Long addressId;
  private String nickName;
  private String qq;
  private String wechat;
  private String mobilePhone;
  private String userPhoto;

}
