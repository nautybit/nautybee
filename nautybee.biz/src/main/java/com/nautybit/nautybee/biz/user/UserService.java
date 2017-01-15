package com.nautybit.nautybee.biz.user;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.user.User;

public interface UserService {
  List<User> getAll();

  User getById(Long id);

  boolean save(User user);

  boolean deleteById( Long id);

  int deleteByIds(Long[] ids);


}
