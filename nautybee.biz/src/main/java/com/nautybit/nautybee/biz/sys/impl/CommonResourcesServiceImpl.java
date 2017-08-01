package com.nautybit.nautybee.biz.sys.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.sys.CommonResourcesService;
import com.nautybit.nautybee.dao.sys.CommonResourcesDao;
import com.nautybit.nautybee.entity.sys.CommonResources;


@Service
public class CommonResourcesServiceImpl extends BaseServiceImpl  implements CommonResourcesService{

  @Autowired
  private CommonResourcesDao commonResourcesDao;

  public List<CommonResources> getAll() {
    return super.getAll(commonResourcesDao);
  }


  public CommonResources getById(Long id) {
    return super.getById(commonResourcesDao, id);
  }

  public boolean save(CommonResources commonResources) {
    return super.save(commonResourcesDao, commonResources);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(commonResourcesDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(commonResourcesDao, ids);
  }

    public CommonResources selectByKey(String key){
        return commonResourcesDao.selectByKey(key);
    }

}
