package com.nautybit.nautybee.biz.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.goods.GoodsPicService;
import com.nautybit.nautybee.dao.goods.GoodsPicDao;
import com.nautybit.nautybee.entity.goods.GoodsPic;


@Service
public class GoodsPicServiceImpl extends BaseServiceImpl  implements GoodsPicService{

  @Autowired
  private GoodsPicDao goodsPicDao;

  public List<GoodsPic> getAll() {
    return super.getAll(goodsPicDao);
  }


  public GoodsPic getById(Long id) {
    return super.getById(goodsPicDao, id);
  }

  public boolean save(GoodsPic goodsPic) {
    return super.save(goodsPicDao, goodsPic);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(goodsPicDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(goodsPicDao, ids);
  }
}
