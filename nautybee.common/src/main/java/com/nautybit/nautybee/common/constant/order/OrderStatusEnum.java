package com.nautybit.nautybee.common.constant.order;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单状态
 * Created by Minutch on 15/7/6.
 */
public enum OrderStatusEnum {

//            'WFK'
//            'YFK'

    /**
     * 待发货
      */
   MDFH,
    /**
     * 待付款
     */
   DFK,
    /**
     * 神汽待发货
      */
   DFH,
    /**
     * 已取消
     */
    YQX,
    /**
     * 待退款
     */
    DTK,
    /**
     * 二次配送
     */
    ECPS,
    /**
     * 已发货
     */
    YFH,
    /**
     * 部分签收
     */
    BFQS,
    /**
     * 已收货
     */
   YSH ,
    /**
     * 已退货
     */
    YTH ,
    /**
     * 已付款
     */
    YFK,
    /**
     * 未付款
     */
    WFK,
    /**
     * 退款中
     */
    TKZ,
    /**
     * 已退款
     */
    YTK,
    /**
     * 部分退款
     */
    BFTK,
    /**
     * 已关闭
     */
    YGB,
    
    INIT, //初始状态
    
    SHCLZ,//售后处理中
    
    SHCLWC, //售后处理完成

    YWJ, //已完结

    
    /**
     * 已发神汽中转
     */
    DQR,
    /**
     * 神汽中转已收货
     */
    YXT
    ;

    private static Map<String,String> statusNameMap = new HashMap<>();
    static {
        statusNameMap.put("DFK","待付款");
        statusNameMap.put("DFH","神汽待发货");
        statusNameMap.put("YQX","已取消");
        statusNameMap.put("DTK","待退款");
        statusNameMap.put("ECPS","二次配送");
        statusNameMap.put("YFH","已发货");
        statusNameMap.put("BFQS","部分签收");
        statusNameMap.put("YSH","已收货");
        statusNameMap.put("YTH","已拒收");
        statusNameMap.put("TKZ","退款中");
        statusNameMap.put("YTK","已退款");
        statusNameMap.put("BFTK","部分退款");
        statusNameMap.put("YFK","已付款");
        statusNameMap.put("WFK","未发货");
        statusNameMap.put("YGB","已关闭");
        
        statusNameMap.put("INIT","待付款");
        statusNameMap.put("SHCLZ","售后处理中");
        statusNameMap.put("SHCLWC","售后处理完成");
        
        statusNameMap.put("DQR","已发神汽中转");
        statusNameMap.put("YXT","神汽中转已收货");
        statusNameMap.put("MDFH","待发货");
        statusNameMap.put("YWJ","已完结");
    }

    public static String getName(String orderStatus){

        return statusNameMap.get(orderStatus);
    }
}
