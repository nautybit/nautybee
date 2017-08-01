package com.nautybit.nautybee.common.constant;

import java.math.BigDecimal;

/**
 * Created by Minutch on 15/7/3.
 */
public interface OrderConstants {
    /**
     * 商品没有店铺
     */
    String goodsHaveNoStore = "goodsHaveNoStore";
    /**
     * 生成OrderSN需要的序列
     */
    String REDIS_ORDER_GEN_SN_SEQUENCE = "Order_GenSnSequence_";
    /**
     * 生成TradeNo需要的序列
     */
    String REDIS_ORDER_GEN_TRADE_NO_SEQUENCE = "Order_GenTradeNoSequence_";
    /**
     * 生成mch_billno需要的序列
     */
    String REDIS_GEN_MCH_BILL_NO = "GenMchBillNo_";
    /**
     * 生成CmbTradeNo需要的序列
     */
    String REDIS_ORDER_GEN_CMB_TRADE_NO_SEQUENCE = "Order_GenCmbTradeNoSequence_";
    /**
     * OrderSN 前缀
     */
    String ORDER_SN_PREFIX = "D";
    /**
     * ReturnOrderSN 前缀
     */
    String RETURN_ORDER_SN_PREFIX = "R";
    /**
     * TradeNo前缀
     */
    String TRADE_NO_PREFIX = "NB";

    /**
     * 活动商品限购
     */
    String ACTIVITY_ORDER_GOODS_BUY_LIMIT = "goodsBuyLimit_";
    /**
     * 团购商品限购
     */
    String BULK_ORDER_GOODS_BUY_LIMIT = "bulkBuyLimit_";

    /**
     * 活动商品限购总量
     */
    String ACTIVITY_ORDER_GOODS_TOTAL_BUY_LIMIT = "goodsTotalBuyLimit_";
    /**
     * 拼团商品限购总量
     */
    String BULK_ORDER_GOODS_TOTAL_BUY_LIMIT = "bulkGoodsTotalBuyLimit_";
    /**
     * 积分抵扣10%
     */
    BigDecimal DISCOUNT_POINT_MUL = new BigDecimal(0.1);
    /**
     * 100积分可抵扣1元
     */
    BigDecimal AMOUNT_DISCOUNT_POINT = new BigDecimal(100);
    /**
     * 下单10元可获得1积分
     */
    BigDecimal ORDER_AMOUNT_TO_POINT = new BigDecimal(10);

    /**
     * 发送订单佣金消息常量
     */
    String MSG_KEY_ORDER_PAY = "orderPay";

    /**
     * 修改订单支付方式由COD到线上支付
     */
    String MSG_KEY_ORDER_REPAY = "orderRepay";

    /*******************************************************
     * 订单改价相关字段
     *******************************************************/
    String PRICE_INC = "priceInc";
    String PRICE_INC_NAME = "价格上调";

    String PRICE_RED = "priceRed";
    String PRICE_RED_NAME = "订单优惠";

    String ORDER_AMOUNT_HIS_SPLIT = "|";

    /*******************************************************
     * 自营订单和商城订单的编码标志
     *******************************************************/
    String ORDER_SELF_SN = "0";
    String ORDER_SELLER_SN = "1";


    /*******************************************************
     * 订单活动开关
     *******************************************************/
    String ON = "ON";
    String OFF = "OFF";

    /*******************************************************
     * 超时未发货订单提醒
     *******************************************************/
    String REMIND_OVER_TIME_PUSH_MSG = "您的订单长时间未发货，请尽快处理以免对你的信誉产生不良影响。";
    String REMIND_OVER_TIME_PUSH_TITLE = "快去发货吧！";


    /*******************************************************
     * 2016双十一活动
     *******************************************************/
    String DOUBLE_ELEVEN_KEY = "DoubleEleven2016";
    String DoubleElevenTypeFirst = "First";
    String DoubleElevenTypeSecond = "Second";

    /********************************************************
     * 团购[bulkOrder],活动[activityOrder]
     ********************************************************/
    String ACTIVITY_ORDER = "activityOrder";
    String BULK_ORDER = "bulkOrder";

    String SHOW_ORDER_Y = "Y";
    String SHOW_ORDER_N = "N";

    String BULK_SALE_SUCCESS_PUSH_TITLE_2_SELLER = "您有一批拼团订单需要发货";
    String BULK_SALE_SUCCESS_PUSH_MSG_2_SELLER_PART_1 = "您之前发布的拼团商品已经拼团成功啦，共有";
    String BULK_SALE_SUCCESS_PUSH_MSG_2_SELLER_PART_2 = "个订单，快去发货吧！";

    String BULK_SALE_SUCCESS_PUSH_TITLE_2_BUYER = "您有一个拼团成功啦";
    String BULK_SALE_SUCCESS_PUSH_MSG_2_BUYER = "点击查看订单详情";
}
