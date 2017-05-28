package com.nautybit.nautybee.biz.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.order.PayOrderRelService;
import com.nautybit.nautybee.dao.order.PayOrderRelDao;
import com.nautybit.nautybee.entity.order.PayOrderRel;


@Service
public class PayOrderRelServiceImpl extends BaseServiceImpl  implements PayOrderRelService{

  @Autowired
  private PayOrderRelDao payOrderRelDao;

  public List<PayOrderRel> getAll() {
    return super.getAll(payOrderRelDao);
  }


  public PayOrderRel getById(Long id) {
    return super.getById(payOrderRelDao, id);
  }

  public boolean save(PayOrderRel payOrderRel) {
    return super.save(payOrderRelDao, payOrderRel);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(payOrderRelDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(payOrderRelDao, ids);
  }

    public List<PayOrderRel> queryByTradeNo(String tradeNo) {
        return payOrderRelDao.queryByTradeNo(tradeNo);
    }
    public void createOrderList(String tradeNo, List<Long> orderIdList) {
        for (Long orderId : orderIdList) {
            PayOrderRel payOrderRel = new PayOrderRel();
            payOrderRel.setDefaultBizValue();
            payOrderRel.setTradeNo(tradeNo);
            payOrderRel.setOrderId(orderId);
            payOrderRelDao.insert(payOrderRel);
        }
    }


}
