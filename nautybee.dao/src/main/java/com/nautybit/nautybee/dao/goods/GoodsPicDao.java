package com.nautybit.nautybee.dao.goods;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.goods.GoodsPic;
import com.nautybit.nautybee.view.goods.GoodsPicView;

import java.util.List;

@MyBatisRepository
public interface GoodsPicDao extends BaseDao<GoodsPic> {

    List<GoodsPicView> selectBySpuId(Long spuId);

}
