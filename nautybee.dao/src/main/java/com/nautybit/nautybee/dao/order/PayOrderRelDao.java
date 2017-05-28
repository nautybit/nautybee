package com.nautybit.nautybee.dao.order;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.order.PayOrderRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface PayOrderRelDao extends BaseDao<PayOrderRel> {
    public List<PayOrderRel> queryByTradeNo(@Param("tradeNo")String tradeNo);


}
