package com.nautybit.nautybee.biz.goods;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.goods.SpuChain;

public interface SpuChainService {
  List<SpuChain> getAll();

  SpuChain getById(Long id);

  boolean save(SpuChain spuChain);

  boolean deleteById( Long id);

  int deleteByIds(Long[] ids);

    SpuChain selectCurrentNode(Long headId);
}
