package com.nautybit.nautybee.biz.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.goods.SpuChainService;
import com.nautybit.nautybee.dao.goods.SpuChainDao;
import com.nautybit.nautybee.entity.goods.SpuChain;


@Service
public class SpuChainServiceImpl extends BaseServiceImpl  implements SpuChainService{

  @Autowired
  private SpuChainDao spuChainDao;

  public List<SpuChain> getAll() {
    return super.getAll(spuChainDao);
  }


  public SpuChain getById(Long id) {
    return super.getById(spuChainDao, id);
  }

  public boolean save(SpuChain spuChain) {
    return super.save(spuChainDao, spuChain);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(spuChainDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(spuChainDao, ids);
  }

    @Override
  public SpuChain selectCurrentNode(Long headId){
        return spuChainDao.selectCurrentNode(headId);
    }
}
