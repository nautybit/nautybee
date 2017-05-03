package com.nautybit.nautybee.biz.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.order.OrderService;
import com.nautybit.nautybee.dao.order.OrderDao;
import com.nautybit.nautybee.entity.order.Order;


@Service
public class OrderServiceImpl extends BaseServiceImpl  implements OrderService{

  @Autowired
  private OrderDao orderDao;

  public List<Order> getAll() {
    return super.getAll(orderDao);
  }


  public Order getById(Long id) {
    return super.getById(orderDao, id);
  }

  public boolean save(Order order) {
    return super.save(orderDao, order);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(orderDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(orderDao, ids);
  }
}
