package com.nautybit.nautybee.biz.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.order.OrderExtService;
import com.nautybit.nautybee.dao.order.OrderExtDao;
import com.nautybit.nautybee.entity.order.OrderExt;


@Service
public class OrderExtServiceImpl extends BaseServiceImpl  implements OrderExtService{

  @Autowired
  private OrderExtDao orderExtDao;

  public List<OrderExt> getAll() {
    return super.getAll(orderExtDao);
  }


  public OrderExt getById(Long id) {
    return super.getById(orderExtDao, id);
  }

  public boolean save(OrderExt orderExt) {
    return super.save(orderExtDao, orderExt);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(orderExtDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(orderExtDao, ids);
  }
}
