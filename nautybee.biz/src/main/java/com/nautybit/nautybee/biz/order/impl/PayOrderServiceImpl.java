package com.nautybit.nautybee.biz.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.order.PayOrderService;
import com.nautybit.nautybee.dao.order.PayOrderDao;
import com.nautybit.nautybee.entity.order.PayOrder;


@Service
public class PayOrderServiceImpl extends BaseServiceImpl  implements PayOrderService{

  @Autowired
  private PayOrderDao payOrderDao;

  public List<PayOrder> getAll() {
    return super.getAll(payOrderDao);
  }


  public PayOrder getById(Long id) {
    return super.getById(payOrderDao, id);
  }

  public boolean save(PayOrder payOrder) {
    return super.save(payOrderDao, payOrder);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(payOrderDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(payOrderDao, ids);
  }
}
