package com.nautybit.nautybee.dao.sys;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.sys.CommonResources;

@MyBatisRepository
public interface CommonResourcesDao extends BaseDao<CommonResources> {
    CommonResources selectByKey(String key);


}
