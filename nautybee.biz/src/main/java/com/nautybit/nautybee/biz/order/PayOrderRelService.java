package com.nautybit.nautybee.biz.order;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.order.PayOrderRel;

public interface PayOrderRelService {
  List<PayOrderRel> getAll();

  PayOrderRel getById(Long id);

  boolean save(PayOrderRel payOrderRel);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);


}
