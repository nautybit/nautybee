package com.nautybit.nautybee.dao.order;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.order.PayOrder;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface PayOrderDao extends BaseDao<PayOrder> {

    public int updateStatusByTradeNo(@Param("tradeNo")String tradeNo,@Param("status")String status);

}
