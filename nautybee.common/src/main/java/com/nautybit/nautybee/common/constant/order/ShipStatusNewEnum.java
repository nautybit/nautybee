package com.nautybit.nautybee.common.constant.order;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixinlong on 15/11/12.
 */
public enum ShipStatusNewEnum {
    MDFH("待发货", OrderStatusEnum.MDFH.toString()),
    YXD("已下单", OrderStatusEnum.DFK.toString()),
    DQR("待确认", OrderStatusEnum.DFH.toString()),
    YXT("已下推", OrderStatusEnum.DFH.toString()),
    CKDSH("出库待审核", OrderStatusEnum.DFH.toString()),
    YCK("已出库", OrderStatusEnum.DFH.toString()),
    DFH("神汽待发货", OrderStatusEnum.DFH.toString()),
    YFH("已发货", OrderStatusEnum.YFH.toString()),
    YSH("已收货", OrderStatusEnum.YSH.toString()),
    BFQS("部分收货", OrderStatusEnum.BFQS.toString()),
    YTH("已拒收", OrderStatusEnum.YTH.toString()),
    ECPS("二次配送", OrderStatusEnum.ECPS.toString()),
    YQX("已取消", OrderStatusEnum.YQX.toString()),
    INIT("待付款", OrderStatusEnum.INIT.toString()),
    SHCLZ("售后处理中", OrderStatusEnum.SHCLZ.toString()),
    SHCLWC("售后处理完成", OrderStatusEnum.SHCLWC.toString()),
    TKZ("退款中", OrderStatusEnum.TKZ.toString()),
    YTK("已退款", OrderStatusEnum.YTK.toString()),
    BFTK("部分退款", OrderStatusEnum.BFTK.toString()),
    YWJ("已完结", OrderStatusEnum.YWJ.toString());

    private String name;
    private String orderStatusName;

    ShipStatusNewEnum(String name, String orderStatusName) {
        this.name = name;
        this.orderStatusName = orderStatusName;
    }

//    public String getName() {
//        return name;
//    }
//    public String getOrderStatusName() {
//        return orderStatusName;
//    }

    public static String getName(String str) {
        return statusNameMap.get(str);
    }

    public static String getOrderStatusName(String str) {
        return statusOrderStatusMap.get(str);
    }

    private static Map<String, String> statusNameMap = new HashMap<>();

    static {
        statusNameMap.put("MDFH", "待发货");
        statusNameMap.put("YXD", "已下单");
        statusNameMap.put("DQR", "待确认");
        statusNameMap.put("YXT", "已下推");
        statusNameMap.put("CKDSH", "出库待审核");
        statusNameMap.put("YCK", "已出库");
        statusNameMap.put("DFH", "神汽待发货");
        statusNameMap.put("YFH", "已发货");
        statusNameMap.put("YSH", "已收货");
        statusNameMap.put("BFQS", "部分收货");
        statusNameMap.put("YTH", "已拒收");
        statusNameMap.put("ECPS", "二次配送");
        statusNameMap.put("YQX", "已取消");
        statusNameMap.put("INIT", "待付款");
        statusNameMap.put("SHCLZ", "售后处理中");
        statusNameMap.put("SHCLWC", "售后处理完成");
        statusNameMap.put("TKZ", "退款中");
        statusNameMap.put("YTK", "已退款");
        statusNameMap.put("BFTK", "部分退款");
        statusNameMap.put("YWJ", "已完结");
    }

    private static Map<String, String> statusOrderStatusMap = new HashMap<>();

    static {
        statusOrderStatusMap.put("MDFH", "MDFH");
        statusOrderStatusMap.put("YXD", "DFK");
        statusOrderStatusMap.put("DQR", "DFH");
        statusOrderStatusMap.put("YXT", "DFH");
        statusOrderStatusMap.put("CKDSH", "DFH");
        statusOrderStatusMap.put("YCK", "DFH");
        statusOrderStatusMap.put("DFH", "DFH");
        statusOrderStatusMap.put("YFH", "YFH");
        statusOrderStatusMap.put("YSH", "YSH");
        statusOrderStatusMap.put("BFQS", "BFQS");
        statusOrderStatusMap.put("YTH", "YTH");
        statusOrderStatusMap.put("ECPS", "ECPS");
        statusOrderStatusMap.put("YQX", "YQX");
        statusOrderStatusMap.put("INIT", "INIT");
        statusOrderStatusMap.put("SHCLZ", "SHCLZ");
        statusOrderStatusMap.put("SHCLWC", "SHCLWC");
        statusOrderStatusMap.put("TKZ", "TKZ");
        statusOrderStatusMap.put("YTK", "YTK");
        statusOrderStatusMap.put("BFTK", "BFTK");
        statusOrderStatusMap.put("YWJ", "YWJ");
    }
}
