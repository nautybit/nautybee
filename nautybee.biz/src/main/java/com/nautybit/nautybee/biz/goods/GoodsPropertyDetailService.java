package com.nautybit.nautybee.biz.goods;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.goods.GoodsPropertyDetail;

public interface GoodsPropertyDetailService {
  List<GoodsPropertyDetail> getAll();

  GoodsPropertyDetail getById(Long id);

  boolean save(GoodsPropertyDetail goodsPropertyDetail);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);


}
