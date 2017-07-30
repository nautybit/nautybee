package com.nautybit.nautybee.dao.goods;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.goods.Goods;
import com.nautybit.nautybee.view.goods.GoodsView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface GoodsDao extends BaseDao<Goods> {

    List<GoodsView> queryGoodsByOrderIdList(@Param("list") List<Long> orderId);
    List<GoodsView> queryGoodsBySpuId(Long spuId);

}
