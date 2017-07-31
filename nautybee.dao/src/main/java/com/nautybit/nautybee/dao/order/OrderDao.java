package com.nautybit.nautybee.dao.order;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.order.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface OrderDao extends BaseDao<Order> {

    List<Order> queryByIds(@Param("list") List<Long> orderIdList);
    void updatePayStatus(@Param("orderSn")String orderSn,@Param("status")String status);

}
