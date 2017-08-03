package com.nautybit.nautybee.biz.goods;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.goods.GoodsPic;
import com.nautybit.nautybee.view.goods.GoodsPicView;

public interface GoodsPicService {
  List<GoodsPic> getAll();

  GoodsPic getById(Long id);

  boolean save(GoodsPic goodsPic);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);
  List<GoodsPicView> selectBySpuId(Long spuId);

}
