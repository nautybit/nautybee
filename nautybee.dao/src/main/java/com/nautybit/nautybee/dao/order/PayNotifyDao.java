package com.nautybit.nautybee.dao.order;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.order.PayNotify;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface PayNotifyDao extends BaseDao<PayNotify> {

    public PayNotify queryByNotifyIdAndStatus(@Param("notifyId")String notifyId,@Param("status")String status);

}
