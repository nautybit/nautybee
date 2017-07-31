package com.nautybit.nautybee.biz.prize.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.prize.PrizeService;
import com.nautybit.nautybee.dao.prize.PrizeDao;
import com.nautybit.nautybee.entity.prize.Prize;


@Service
public class PrizeServiceImpl extends BaseServiceImpl  implements PrizeService{

  @Autowired
  private PrizeDao prizeDao;

  public List<Prize> getAll() {
    return super.getAll(prizeDao);
  }


  public Prize getById(Long id) {
    return super.getById(prizeDao, id);
  }

  public boolean save(Prize prize) {
    return super.save(prizeDao, prize);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(prizeDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(prizeDao, ids);
  }
}
