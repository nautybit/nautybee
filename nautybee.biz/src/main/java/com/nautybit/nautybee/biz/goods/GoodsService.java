package com.nautybit.nautybee.biz.goods;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.goods.Goods;
import com.nautybit.nautybee.view.goods.GoodsView;

public interface GoodsService {
  List<Goods> getAll();

  Goods getById(Long id);

  boolean save(Goods goods);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);

    List<GoodsView> queryGoodsByOrderIdList(List<Long> orderId);
    List<GoodsView> queryGoodsBySpuId(Long spuId);
    void fillGoodsAttribute(List<GoodsView> goodsViewList);
    GoodsView queryGoodsById(Long id);


}
