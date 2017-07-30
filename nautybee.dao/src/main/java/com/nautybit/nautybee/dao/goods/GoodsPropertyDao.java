package com.nautybit.nautybee.dao.goods;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.goods.GoodsProperty;

import java.util.List;

@MyBatisRepository
public interface GoodsPropertyDao extends BaseDao<GoodsProperty> {
    List<GoodsProperty> getBySpuId(Long spuId);

}
