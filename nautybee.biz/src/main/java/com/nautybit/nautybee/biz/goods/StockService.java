package com.nautybit.nautybee.biz.goods;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.goods.Stock;

public interface StockService {
  List<Stock> getAll();

  Stock getById(Long id);

  boolean save(Stock stock);

  boolean deleteById( Long id);

  int deleteByIds(Long[] ids);

  boolean updateGoodsNum(Long goodsNum,Long goodsId);

    Stock selectByGoodsId(Long goodsId);
}
