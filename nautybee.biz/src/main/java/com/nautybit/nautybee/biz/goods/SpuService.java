package com.nautybit.nautybee.biz.goods;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.goods.Spu;
import com.nautybit.nautybee.view.goods.SpuView;

public interface SpuService {
  List<Spu> getAll();

  Spu getById(Long id);

  boolean save(Spu spu);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);

    List<SpuView> queryList();

    List<SpuView> queryListOfCategory();

}
