package com.nautybit.nautybee.dao.goods;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.goods.SpuChain;

@MyBatisRepository
public interface SpuChainDao extends BaseDao<SpuChain> {
    SpuChain selectCurrentNode(Long headId);


}
