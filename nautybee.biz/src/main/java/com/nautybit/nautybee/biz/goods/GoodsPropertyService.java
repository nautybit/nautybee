package com.nautybit.nautybee.biz.goods;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.goods.GoodsProperty;

public interface GoodsPropertyService {
  List<GoodsProperty> getAll();

  GoodsProperty getById(Long id);

  boolean save(GoodsProperty goodsProperty);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);

    List<GoodsProperty> getBySpuId(Long spuId);
}
