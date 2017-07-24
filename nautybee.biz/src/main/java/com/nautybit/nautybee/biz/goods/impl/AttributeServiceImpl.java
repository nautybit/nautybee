package com.nautybit.nautybee.biz.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.goods.AttributeService;
import com.nautybit.nautybee.dao.goods.AttributeDao;
import com.nautybit.nautybee.entity.goods.Attribute;


@Service
public class AttributeServiceImpl extends BaseServiceImpl  implements AttributeService{

  @Autowired
  private AttributeDao attributeDao;

  public List<Attribute> getAll() {
    return super.getAll(attributeDao);
  }


  public Attribute getById(Long id) {
    return super.getById(attributeDao, id);
  }

  public boolean save(Attribute attribute) {
    return super.save(attributeDao, attribute);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(attributeDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(attributeDao, ids);
  }
}
