package com.nautybit.nautybee.dao.goods;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.goods.Stock;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface StockDao extends BaseDao<Stock> {
    boolean updateGoodsNum(@Param("goodsNum")Long goodsNum,@Param("goodsId")Long goodsId);
    Stock selectByGoodsId(Long goodsId);

}
