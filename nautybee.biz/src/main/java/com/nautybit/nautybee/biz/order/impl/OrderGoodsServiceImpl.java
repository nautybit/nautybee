package com.nautybit.nautybee.biz.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.order.OrderGoodsService;
import com.nautybit.nautybee.dao.order.OrderGoodsDao;
import com.nautybit.nautybee.entity.order.OrderGoods;


@Service
public class OrderGoodsServiceImpl extends BaseServiceImpl  implements OrderGoodsService{

  @Autowired
  private OrderGoodsDao orderGoodsDao;

  public List<OrderGoods> getAll() {
    return super.getAll(orderGoodsDao);
  }


  public OrderGoods getById(Long id) {
    return super.getById(orderGoodsDao, id);
  }

  public boolean save(OrderGoods orderGoods) {
    return super.save(orderGoodsDao, orderGoods);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(orderGoodsDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(orderGoodsDao, ids);
  }
}
