package com.nautybit.nautybee.biz.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.user.UserService;
import com.nautybit.nautybee.dao.user.UserDao;
import com.nautybit.nautybee.entity.user.User;


@Service
public class UserServiceImpl extends BaseServiceImpl  implements UserService{

  @Autowired
  private UserDao userDao;

  public List<User> getAll() {
    return super.getAll(userDao);
  }

  public User getById(Long id) {
    return super.getById(userDao, id);
  }

  public boolean save(User user) {
    return super.save(userDao, user);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(userDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(userDao, ids);
  }
}
