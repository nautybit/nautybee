package com.nautybit.nautybee.biz.sys;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.sys.CommonResources;

public interface CommonResourcesService {
  List<CommonResources> getAll();

  CommonResources getById(Long id);

  boolean save(CommonResources commonResources);

  boolean deleteById( Long id);

  int deleteByIds(Long[] ids);

    CommonResources selectByKey(String key);

}
