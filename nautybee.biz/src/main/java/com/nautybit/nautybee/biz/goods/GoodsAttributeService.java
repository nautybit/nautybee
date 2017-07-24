package com.nautybit.nautybee.biz.goods;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.goods.GoodsAttribute;

public interface GoodsAttributeService {
  List<GoodsAttribute> getAll();

  GoodsAttribute getById(Long id);

  boolean save(GoodsAttribute goodsAttribute);

  boolean deleteById( Long id);

  int deleteByIds(Long[] ids);


}
