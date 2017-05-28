package com.nautybit.nautybee.biz.order.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.order.PayNotifyService;
import com.nautybit.nautybee.dao.order.PayNotifyDao;
import com.nautybit.nautybee.entity.order.PayNotify;


@Service
public class PayNotifyServiceImpl extends BaseServiceImpl  implements PayNotifyService{

  @Autowired
  private PayNotifyDao payNotifyDao;

  public List<PayNotify> getAll() {
    return super.getAll(payNotifyDao);
  }


  public PayNotify getById(Long id) {
    return super.getById(payNotifyDao, id);
  }

  public boolean save(PayNotify payNotify) {
    return super.save(payNotifyDao, payNotify);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(payNotifyDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(payNotifyDao, ids);
  }

    public PayNotify queryByNotifyIdAndStatus(String notifyId,String tradeStatus) {
        return payNotifyDao.queryByNotifyIdAndStatus(notifyId, tradeStatus);
    }
}
