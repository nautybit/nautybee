package com.nautybit.nautybee.biz.order.impl;

import java.util.Date;
import java.util.List;

import com.nautybit.nautybee.biz.redis.RedisStringService;
import com.nautybit.nautybee.common.constant.OrderConstants;
import com.nautybit.nautybee.common.constant.order.OrderStatusEnum;
import com.nautybit.nautybee.common.param.order.OrderParam;
import com.nautybit.nautybee.common.utils.DateUtils;
import com.nautybit.nautybee.common.utils.GenerationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.order.OrderService;
import com.nautybit.nautybee.dao.order.OrderDao;
import com.nautybit.nautybee.entity.order.Order;


@Service
@Slf4j
public class OrderServiceImpl extends BaseServiceImpl  implements OrderService{

  @Autowired
  private OrderDao orderDao;
    @Autowired
    private RedisStringService redisStringService;

  public List<Order> getAll() {
    return super.getAll(orderDao);
  }


  public Order getById(Long id) {
    return super.getById(orderDao, id);
  }

  public boolean save(Order order) {
    return super.save(orderDao, order);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(orderDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(orderDao, ids);
  }

    /**
     * tradeNo生成：该no用于标识支付订单的唯一性
     * YP20150722444409837287
     * (YP)+(yyyyMMdd)+(3位随机数)+(每日序列，每天凌晨从0开始计数，10位)
     *
     * @return
     */
    public String generateTradeNo() {
        String yyyyMMdd = DateUtils.dateFormat(new Date(), DateUtils.YMD);
        String randomDigital = GenerationUtils.generateRandomCode(3);
        String sequence = GenerationUtils.generateFixedLengthDigital(getSequence(OrderConstants.REDIS_ORDER_GEN_TRADE_NO_SEQUENCE + yyyyMMdd), 10);
        String tradeNo = OrderConstants.TRADE_NO_PREFIX + yyyyMMdd + randomDigital + sequence;
        log.info("generateTradeNo:生成一枚TradeNo[" + tradeNo + "]");
        return tradeNo;
    }

    public long getSequence(String sequenceKey) {
        Long sequenceId = redisStringService.incr(sequenceKey);
        log.info("getSequence:sequence=" + sequenceId);
        return sequenceId;
    }

    public List<Order> queryByIds(List<Long> orderIdList) {
        List<Order> list = orderDao.queryByIds(orderIdList);
        return list;
    }

    @Override
    public Order createOrder(OrderParam orderParam){
        Order order = new Order();
        order.setDefaultBizValue();
        order.setOrderSn(generateTradeNo());
        order.setWxOpenId(orderParam.getWxOpenid());
        order.setUserId(-1l);
        order.setStoreId(orderParam.getStoreId());
        order.setPayStatus(OrderStatusEnum.WFK.name());
        order.setOrderAmount(orderParam.getTotalFee());
        save(order);
        return order;
    }

    @Override
    public void updatePayStatus(String orderSn,String status){
        orderDao.updatePayStatus(orderSn,status);
    }

    @Override
    public Order queryByOrderSn(String orderSn){
        return orderDao.queryByOrderSn(orderSn);
    }
}
