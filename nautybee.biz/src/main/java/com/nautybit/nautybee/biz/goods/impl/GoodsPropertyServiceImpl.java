package com.nautybit.nautybee.biz.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.goods.GoodsPropertyService;
import com.nautybit.nautybee.dao.goods.GoodsPropertyDao;
import com.nautybit.nautybee.entity.goods.GoodsProperty;


@Service
public class GoodsPropertyServiceImpl extends BaseServiceImpl  implements GoodsPropertyService{

  @Autowired
  private GoodsPropertyDao goodsPropertyDao;

  public List<GoodsProperty> getAll() {
    return super.getAll(goodsPropertyDao);
  }


  public GoodsProperty getById(Long id) {
    return super.getById(goodsPropertyDao, id);
  }

  public boolean save(GoodsProperty goodsProperty) {
    return super.save(goodsPropertyDao, goodsProperty);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(goodsPropertyDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(goodsPropertyDao, ids);
  }
}
