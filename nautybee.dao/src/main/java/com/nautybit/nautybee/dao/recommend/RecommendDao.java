package com.nautybit.nautybee.dao.recommend;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.recommend.Recommend;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface RecommendDao extends BaseDao<Recommend> {

    Recommend selectByToUser(String toUser);

}
