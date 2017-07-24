package com.nautybit.nautybee.biz.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.goods.GoodsAttributeService;
import com.nautybit.nautybee.dao.goods.GoodsAttributeDao;
import com.nautybit.nautybee.entity.goods.GoodsAttribute;


@Service
public class GoodsAttributeServiceImpl extends BaseServiceImpl  implements GoodsAttributeService{

  @Autowired
  private GoodsAttributeDao goodsAttributeDao;

  public List<GoodsAttribute> getAll() {
    return super.getAll(goodsAttributeDao);
  }


  public GoodsAttribute getById(Long id) {
    return super.getById(goodsAttributeDao, id);
  }

  public boolean save(GoodsAttribute goodsAttribute) {
    return super.save(goodsAttributeDao, goodsAttribute);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(goodsAttributeDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(goodsAttributeDao, ids);
  }
}
