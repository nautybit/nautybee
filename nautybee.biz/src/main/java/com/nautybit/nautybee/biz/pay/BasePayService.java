package com.nautybit.nautybee.biz.pay;

import com.nautybit.nautybee.biz.order.OrderService;
import com.nautybit.nautybee.biz.order.PayNotifyService;
import com.nautybit.nautybee.biz.order.PayOrderRelService;
import com.nautybit.nautybee.common.constant.order.OrderStatusEnum;
import com.nautybit.nautybee.common.constant.pay.PayConstants;
import com.nautybit.nautybee.common.param.pay.PayNotifyParam;
import com.nautybit.nautybee.common.utils.PrintUtils;
import com.nautybit.nautybee.entity.order.Order;
import com.nautybit.nautybee.entity.order.PayNotify;
import com.nautybit.nautybee.entity.order.PayOrderRel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 支付
 * Created by Minutch on 15/7/25.
 */
public abstract class BasePayService {

    private static final Logger logger = LoggerFactory.getLogger(BasePayService.class);

    @Autowired
    PayOrderRelService payOrderRelService;
    @Autowired
    OrderService orderService;
    @Autowired
    PayNotifyService payNotifyService;
    /**
     * 保存异步通知信息
     * @param notifyParam
     * @return
     */
    public void savePayNotifyInfo(Map<String,String> notifyParam,PayNotifyParam payNotifyParam){
        //商户订单号
        String tradeNo = payNotifyParam.getTradeNo();
        //支付宝交易号
        String paySystemTradeNo = payNotifyParam.getPaySystemTradeNo();
        //交易状态
        String tradeStatus = payNotifyParam.getStatus();
        //notifyId
        String notifyId = payNotifyParam.getNotifyId();

        PayNotify payNotify = new PayNotify();
        payNotify.setDefaultBizValue();
        payNotify.setTradeNo(tradeNo);
        payNotify.setPaySystemTradeNo(paySystemTradeNo);
        payNotify.setNotifyId(notifyId);
        payNotify.setStatus(tradeStatus);
        payNotify.setRequestParam(PrintUtils.printMap(notifyParam));

        payNotifyService.save(payNotify);
        logger.info("savePayNotifyInfo:save notify info success. notifyId["+notifyId+"],tradeStatus["+tradeStatus+"]");
    }
    /**
     * 更新订单状态
     * @param tradeNo
     * @return
     */
    @Transactional
    public String updateOrderStatus(String tradeNo,String payStatus){

        logger.info("updateOrderStatus:tradeNo["+tradeNo+"],payStatus["+payStatus+"]");
        /**********************************************************************************
         * 1.交易成功，根据TradeNo找到支付的订单列表;更新订单的状态，保存到数据库
         **********************************************************************************/
        List<PayOrderRel> payOrderRelList = payOrderRelService.queryByTradeNo(tradeNo);
        if (payOrderRelList == null || payOrderRelList.size()==0) {
            logger.error("updateOrderStatus:支付流水["+tradeNo+"]未关联任何订单，支付系统可能存在严重问题，请及时排查！");
            throw new RuntimeException("支付流水["+tradeNo+"]未关联任何订单，支付系统可能存在严重问题，请及时排查");
        }
        int orderSize = payOrderRelList.size();
        List<Long> orderIdList = new ArrayList<Long>();
        for (PayOrderRel payOrderRel : payOrderRelList) {
            Long orderId = payOrderRel.getOrderId();
            orderIdList.add(orderId);
        }

        //更新订单状态
//        updateOrderPayId(orderIdList);

        List<Order> orderList =null;
        orderList = orderService.queryByIds(orderIdList);
        if (orderList==null || orderList.size()==0) {
            logger.error("updateOrderStatus:支付流水["+tradeNo+"]未关联任何订单["+ PrintUtils.printList(orderIdList)+"]，支付系统可能存在严重问题，请及时排查！");
            throw new RuntimeException("支付流水["+tradeNo+"]未关联任何订单["+ PrintUtils.printList(orderIdList)+"]，支付系统可能存在严重问题，请及时排查");
        }
        if (orderList.size()!=orderSize) {
            logger.error("updateOrderStatus:支付流水["+tradeNo+"]中的订单数与实际订单["+PrintUtils.printList(orderIdList)+"]不符合！");
            throw new RuntimeException("支付流水["+tradeNo+"]中的订单数与实际订单["+PrintUtils.printList(orderIdList)+"]不符合");
        }



        /**********************************************************************************
         * 2.更新订单的状态，保存到数据库
         **********************************************************************************/
        if (PayConstants.PAYING.equals(payStatus)) {
//            for (Order order : orderList) {
//                order.setOrderStatus(OrderStatusEnum.FKZ.toString());
//                order.setShippingStatus(ShipStatusEnmu.FKZ.toString());
//                order.setPayStatus(PayStatusEnum.FKZ.toString());
//                order.setDefaultBizValue();
//                orderService.save(order);
//            }
        } else if (PayConstants.PAY_SUCCESS.equals(payStatus)) {
            BigDecimal totalFee = new BigDecimal(0);
            //商家订单需要特殊处理
            final List<Long> sellerOrderIdList = new ArrayList<>();
            for (Order order : orderList) {

                boolean firstPayment = false;
                //如果订单状态为待付款
                if (OrderStatusEnum.INIT.name().equals(order.getOrderStatus()) || OrderStatusEnum.DFK.name().equals(order.getOrderStatus())) {
                    firstPayment = true;
                }

                Order upOrder = new Order();
                upOrder.setId(order.getId());
                upOrder.setOrderAmount(order.getOrderAmount());



                orderService.save(upOrder);



            }


        }
        return null;
    }
}



























