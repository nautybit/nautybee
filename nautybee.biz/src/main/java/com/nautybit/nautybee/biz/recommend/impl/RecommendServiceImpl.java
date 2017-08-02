package com.nautybit.nautybee.biz.recommend.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.recommend.RecommendService;
import com.nautybit.nautybee.dao.recommend.RecommendDao;
import com.nautybit.nautybee.entity.recommend.Recommend;


@Service
public class RecommendServiceImpl extends BaseServiceImpl  implements RecommendService{

  @Autowired
  private RecommendDao recommendDao;

  public List<Recommend> getAll() {
    return super.getAll(recommendDao);
  }


  public Recommend getById(Long id) {
    return super.getById(recommendDao, id);
  }

  public boolean save(Recommend recommend) {
    return super.save(recommendDao, recommend);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(recommendDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(recommendDao, ids);
  }

    public Recommend selectByToUser(String toUser){
        return recommendDao.selectByToUser(toUser);
    }
}
