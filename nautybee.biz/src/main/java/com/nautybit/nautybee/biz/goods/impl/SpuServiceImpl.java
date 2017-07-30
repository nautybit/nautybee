package com.nautybit.nautybee.biz.goods.impl;

import java.util.List;

import com.nautybit.nautybee.view.goods.SpuView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.goods.SpuService;
import com.nautybit.nautybee.dao.goods.SpuDao;
import com.nautybit.nautybee.entity.goods.Spu;


@Service
public class SpuServiceImpl extends BaseServiceImpl  implements SpuService{

  @Autowired
  private SpuDao spuDao;

  public List<Spu> getAll() {
    return super.getAll(spuDao);
  }

  public Spu getById(Long id) {
    return super.getById(spuDao, id);
  }

  public boolean save(Spu spu) {
    return super.save(spuDao, spu);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(spuDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(spuDao, ids);
  }

  public List<SpuView> queryList(){
        return spuDao.queryList();
    }

  public List<SpuView> queryListOfCategory(){
        return spuDao.queryListOfCategory();
    }
}
