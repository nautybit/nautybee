package com.nautybit.nautybee.dao.goods;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.goods.GoodsPropertyDetail;

import java.util.List;

@MyBatisRepository
public interface GoodsPropertyDetailDao extends BaseDao<GoodsPropertyDetail> {
    List<GoodsPropertyDetail> selectByPropId(Long propId);

}
