package com.nautybit.nautybee.biz.goods;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.goods.Attribute;

public interface AttributeService {
  List<Attribute> getAll();

  Attribute getById(Long id);

  boolean save(Attribute attribute);

  boolean deleteById( Long id);

  int deleteByIds(Long[] ids);


}
