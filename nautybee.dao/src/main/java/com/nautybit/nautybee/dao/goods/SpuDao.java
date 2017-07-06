package com.nautybit.nautybee.dao.goods;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.goods.Spu;
import com.nautybit.nautybee.view.goods.SpuView;

import java.util.List;

@MyBatisRepository
public interface SpuDao extends BaseDao<Spu> {

    List<SpuView> queryList();

}
