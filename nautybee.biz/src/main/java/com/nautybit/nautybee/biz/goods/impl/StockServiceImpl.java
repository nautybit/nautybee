package com.nautybit.nautybee.biz.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.goods.StockService;
import com.nautybit.nautybee.dao.goods.StockDao;
import com.nautybit.nautybee.entity.goods.Stock;


@Service
public class StockServiceImpl extends BaseServiceImpl  implements StockService{

  @Autowired
  private StockDao stockDao;

  public List<Stock> getAll() {
    return super.getAll(stockDao);
  }


  public Stock getById(Long id) {
    return super.getById(stockDao, id);
  }

  public boolean save(Stock stock) {
    return super.save(stockDao, stock);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(stockDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(stockDao, ids);
  }
}
