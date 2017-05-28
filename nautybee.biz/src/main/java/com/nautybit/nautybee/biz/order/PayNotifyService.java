package com.nautybit.nautybee.biz.order;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.order.PayNotify;

public interface PayNotifyService {
  List<PayNotify> getAll();

  PayNotify getById(Long id);

  boolean save(PayNotify payNotify);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);

    public PayNotify queryByNotifyIdAndStatus(String notifyId,String tradeStatus);

}
