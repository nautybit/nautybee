package com.nautybit.nautybee.biz.goods;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.goods.Goods;

public interface GoodsService {
  List<Goods> getAll();

  Goods getById(Long id);

  boolean save(Goods goods);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);


}
