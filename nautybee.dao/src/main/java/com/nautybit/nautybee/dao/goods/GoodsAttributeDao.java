package com.nautybit.nautybee.dao.goods;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.goods.GoodsAttribute;
import com.nautybit.nautybee.view.goods.GoodsAttributeView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface GoodsAttributeDao extends BaseDao<GoodsAttribute> {

    List<GoodsAttributeView> queryByGoodsIdList(@Param("list") List<Long> goodsIdList);


}
