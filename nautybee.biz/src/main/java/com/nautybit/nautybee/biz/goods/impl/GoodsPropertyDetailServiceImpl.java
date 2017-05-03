package com.nautybit.nautybee.biz.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.goods.GoodsPropertyDetailService;
import com.nautybit.nautybee.dao.goods.GoodsPropertyDetailDao;
import com.nautybit.nautybee.entity.goods.GoodsPropertyDetail;


@Service
public class GoodsPropertyDetailServiceImpl extends BaseServiceImpl  implements GoodsPropertyDetailService{

  @Autowired
  private GoodsPropertyDetailDao goodsPropertyDetailDao;

  public List<GoodsPropertyDetail> getAll() {
    return super.getAll(goodsPropertyDetailDao);
  }


  public GoodsPropertyDetail getById(Long id) {
    return super.getById(goodsPropertyDetailDao, id);
  }

  public boolean save(GoodsPropertyDetail goodsPropertyDetail) {
    return super.save(goodsPropertyDetailDao, goodsPropertyDetail);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(goodsPropertyDetailDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(goodsPropertyDetailDao, ids);
  }
}
