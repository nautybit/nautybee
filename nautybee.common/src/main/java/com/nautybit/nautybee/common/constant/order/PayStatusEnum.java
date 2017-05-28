//package com.nautybit.nautybee.common.constant.order;
//
//import com.yuanpin.base.util.annotation.Display;
//import com.yuanpin.base.util.enu.EnumStringInterface;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Minutch on 15/7/6.
// */
//public enum PayStatusEnum implements EnumStringInterface{
//    /**
//     * 未付款
//     */
//    @Display("未付款")
//    WFK("WFK"),
//    /**
//     * 已付款
//     */
//    @Display("已付款")
//    YFK("YFK"),
//    /**
//     * 已退款
//     */
//    @Display("已退款")
//    YTK("YTK"),
//
//
//    @Display("退款中")
//    TKZ("TKZ"),
//
//    @Display("退款失败")
//    TKSB("TKSB")
//
//    ;
//
//    private String code;
//
//    private PayStatusEnum(String code){
//        this.code = code;
//    }
//
//
//
//
//    private static Map<String,String> statusNameMap = new HashMap<>();
//
//    static {
//        statusNameMap.put("WFK","未付款");
//        statusNameMap.put("YFK","已付款");
//        statusNameMap.put("YTK","已退款");
//    }
//
//    public static String getName(String orderStatus){
//
//        return statusNameMap.get(orderStatus);
//    }
//
//    @Override
//    public String code() {
//        return code;
//    }
//}
