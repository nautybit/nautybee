package com.yuanpin.flora.common.result;

/**
 * Created by Minutch on 15/6/26.
 */
public enum ErrorCode {

    UNKNOW_ERROR("-999", "未知异常"),
    NO_CITY_STATION("-998", "未选择城市站"),

//    VERSION_ERROR("-900", "请到[http://www.sqmall.com/dd]升级到最新版本！"),
    VERSION_ERROR("-900", "请升级到最新版本！"),

    /************************************************
     * 登录注册相关错误码(业务级错误码)
     ************************************************/
    LR_BLANK_MOBILE("-1000", "手机号码为空！"),
    LR_BLANK_VERIFY_CODE("-1001", "图形验证码为空！"),
    LR_INVALID_MOBILE("-1002", "无效手机号码！"),
    LR_BLANK_PASSWORD("-1003", "密码不能为空！"),
    LR_INVALID_PASSWORD("-1004", "密码长度至少6位数！"),
    LR_EXPIRE_VERIFY_CODE("-1005", "图形验证码已过期！"),
    LR_ERROR_VERIFY_CODE("-1006", "图形验证码错误！"),
    LR_BLANK_MESSAGE_VERIFY_CODE("-1007", "短信验证码为空！"),
    LR_EXPIRE_MESSAGE_VERIFY_CODE("-1008", "短信验证码已过期！"),
    LR_ERROR_MESSAGE_VERIFY_CODE("-1009", "短信验证码错误！"),
    LR_EXIST_REGISTER_MOBILE_PHONE("-1010", "已经注册过的手机号码！"),
    LR_BLANK_USER("-1011", "用户名或手机号为空！"),
    LR_UN_REGISTER_USER("-1012", "未注册过用户名或手机号！"),
    LR_ERROR_PASSWORD("-1013", "登录密码错误！"),
    LR_ERROR_MESSAGE_NOT_SENT("-1014", "未发送短信验证码！"),
    /************************************************
     * 商品相关错误码(业务级错误码)
     ************************************************/
    GOODS_BLANK_GOODS_ID("-1100", "商品编号为空！"),
    GOODS_NOT_EXIST_GOODS_ID("-1101", "不存在的商品ID！"),
    GOODS_ERROR_GOODS_ID("-1102", "商品ID错误！"),
    GOODS_ERROR_GOODS_PRICE("-1103", "商品价格错误！"),
    GOODS_HAVE_NO_STORE("-1104", "商品不存在店铺！"),
    GOODS_UN_SALES("-1105", "商品已下架"),
    GOODS_BASE_PRICE_ERROR("-1106", "商品在当前城市站暂不售卖."),
    GOODS_NOT_EXIST_OR_UN_SALE("-1107", "商品不存在或已下架."),
    GOODS_IMG_UPLOAD_ERROR("-1913", "上传图片出错"),
    /************************************************
     * 订单相关错误码(业务级错误码)
     ************************************************/
    ORDER_BLANK_LOGISTICS("-1200", "未选择物流类型！"),
    ORDER_ERROR_LOGISTICS("-1201", "物流类型不正确！"),
    ORDER_EMPTY_GOODS("-1202", "订单商品为空！"),
    ORDER_ERROR_GOODS_NUM("-1203", "订单商品数量错误！"),
    ORDER_BLANK_SHIP_FEE("-1204", "订单物流费不能为null！"),
    ORDER_ERROR_SHIP_FEE("-1205", "运费校验失败，前后端运费不一致！！"),
    ORDER_BLANK_AMOUNT("-1206", "商品金额为空！"),
    ORDER_ERROR_AMOUNT("-1207", "订单校验失败，前后端订单金额不一致！！"),
    ORDER_NOT_MATCH_USER_ID("-1208", "下单用户Id和收货地址用户Id不匹配！"),
    ORDER_BLANK_GOODS_AMOUNT("-1209", "商品金额为空！"),
    ORDER_ERROR_GOODS_AMOUNT("-1210", "订单校验失败，前后端商品金额不一致！！"),
    ORDER_STORE_GOODS_NOT_MATCH("-1211", "店铺不存在的商品！"),
    ORDER_NO_PHY_STORE("-1212", "自提店铺信息为空！"),
    ORDER_NOT_EXIST("-1213", "订单不存在！"),
    ORDER_ID_IS_NULL("-1214", "订单ID为空！"),
    ORDER_NOT_CHOOSE_PAY_ID("-1215", "订单未选择支付方式！！"),
    ORDER_NOT_MATCH_PAY_ID("-1216", "当前选择的订单支付方式不一致！！"),
    ORDER_HAS_PAY_OR_CANCEL("-1217", "存在已经支付或取消的订单！"),
    ORDER_BUYER_STORE_ERROR("-1218", "代下单的客户信息有误！"),
    ORDER_CITY_STATION_NOT_MATCH("-1219", "收货地址与城市站不一致！"),
    ORDER_USER_NOT_B_CUSTOMER("-1220", "请联系客户经理认证后下单."),
    ORDER_CITY_HAS_NO_WH("-1221", "当前城市站找不到对应的仓库."),
    ORDER_EMPTY_USE_DATE("-1222", "预约日期未填写"),
    ORDER_EMPTY_USE_TIME("-1223", "预约时间段未填写"),
    ORDER_USE_DATE_ERROR("-1224", "预约日期不正确."),
    ORDER_STATUS_CANNOT_CANCEL("-1225", "当前订单状态无法取消订单."),
    ORDER_FINISH_KEY_IS_NULL("-1226", "订单确认码为空."),
    ORDER_FINISH_KEY_EXPIRE("-1227", "订单确认码已过期，请重新生成二维码."),
    ORDER_STATUS_CANNOT_FINISH("-1228", "当前订单状态无法将订单状态改为服务完成."),
    ORDER_STATUS_CANNOT_REFUND("-1229", "当前订单状态无法申请退款."),
    ORDER_NOT_MATCH_STORE_ID("-1230", "订单门店ID和当前操作门店Id不匹配！"),
    ORDER_OTHER_SERVICE_NEED_GOODS_ID("-1231", "自定义服务需要指定goodsId！"),
    ORDER_AMOUNT_LESS_THEN_ZERO("-1232", "订单金额不能小于0！"),
    ORDER_HAS_INVOICE_APPLY("-1233", "该订单已开票，不能取消，请联系业务经理办理退票后再取消！"),
    /************************************************
     * 用户相关错误码(业务级错误码)
     ************************************************/
    USER_BLANK_ADDRESS_ID("-1300", "用户收货地址为空！"),
    USER_ERROR_ADDRESS_ID("-1301", "用户收货地址不存在！"),
    USER_NOT_LOGIN("-1302", "用户未登录，请先登录！"),
    USER_ID_NOT_EXIST("-1303", "用户不存在！"),
    USER_HAS_NO_STORE("-1304", "不存在的店铺."),
    USER_HAS_NO_COUPONS("-1305", "用户没有可使用的优惠券"),
    USER_TYPE_IS_NULL("-1306", "用户类型为空."),
    ADDRESS_USER_ID_NOT_MATCH("-1307", "收货地址的userId和当前登录者不一致."),
    USER_NOT_SELLER_S("-1308", "用户非入驻商户."),

    /************************************************
     * 店铺相关错误码(业务级错误码)
     ************************************************/
    STORE_ID_NOT_EXIST("-1400", "StoreId不存在！"),
    STORE_ID_NOT_MATCH("-1401", "前后端StoreId不匹配！"),
    STORE_ID_IS_NULL("-1402", "StoreId为null！"),
    STORE_INVA_CODE_IS_NULL("-1403", "门店邀请码不能为空！"),
    STORE_NOT_B_USER("-1404", "该用户不是B客户，不存在Store"),

    /************************************************
     * 商品点赞相关错误码(业务级错误码)
     ************************************************/
    APPLAUD_GOODS_USER_ERROR("-1500", "点赞信息错误，userID和deviceId至少有其中一个！"),

    /************************************************
     * 实体店铺相关错误码(业务级错误码)
     ************************************************/
    PHY_STORE_NOT_EXIST("-1600", "不存在该StoreId的实体店铺！"),

    /************************************************
     * 购物车相关错误码(业务级错误码)
     ************************************************/
    CART_IS_NULL("-1700", "购物车对象为空！"),
    CART_GOODS_NOT_EXIST("-1701", "购物车商品为空！"),
    CART_GOODS_NUMBER_ERROR("-1702", "购物车商品数量有误！"),
    CART_ID_IS_NULL("-1703", "购物车ID为空！"),
    CART_ID_NOT_EXIST("-1704", "购物车ID不存在！"),
    CART_USER_ID_NOT_MATCH("-1705", "购物车的userId与当前登录者不匹配！"),

    /************************************************
     * StoreEmployee错误码(业务级错误码)
     ************************************************/
    STORE_EMPLOYEE_IS_NOT_EXIST("-1801", "该员工不存在"),
    STORE_EMPLOYEE_NOT_CUSTOMER("-1802", "选择的客户不属于当前销售"),

    /************************************************
     * BuyerStore相关错误码(业务级错误码)
     ************************************************/
    BUYER_STORE_NOT_B_USER("-1901", "该用户不是B客户，不存在BuyerStore"),
    BUYER_STORE_NAME_EMPTY("-1902", "门店名称不能为空."),
    BUYER_STORE_CONTACT_NAME_EMPTY("-1903", "联系人不能为空."),
    BUYER_STORE_CONTACT_MOBILE_EMPTY("-1904", "联系人手机不能为空."),
    BUYER_STORE_CONTACT_MOBILE_NOT_MATCH("-1905", "联系人手机号不正确."),
    BUYER_STORE_PROVINCE_ERROR("-1906", "门店省份不能为空."),
    BUYER_STORE_CITY_ERROR("-1907", "门店城市不能为空."),
    BUYER_STORE_DISTRICT_ERROR("-1908", "门店区域不能为空."),
    BUYER_STORE_STREET_ERROR("-1909", "门店街道不能为空."),
    BUYER_STORE_ADDRESS_ERROR("-1910", "门店地址不能为空."),
    BUYER_STORE_ID_EMPTY("-1911", "buyerStoreId不能为空"),
    BUYER_STORE_ID_NOT_MATCH("-1912", "前端buyerStoreId和数据库的id不匹配"),
    BUYER_STORE_PHOTO_ERROR("-1913", "上传图片出错"),
    BUYER_STORE_NO_RECOMMEND("-1914", "附近无合适门店."),
    BUYER_STORE_NO_CITY_NAME("-1915", "未知的城市."),

    /************************************************
     * StoreFixCar相关错误码(业务级错误码)
     ************************************************/
    FIX_CAR_BRAND_EMPTY("-2001", "必须选择汽车品牌"),
    FIX_CAR_ID_EMPTY("-2002", "不存在的主修车型"),
    FIX_CAR_USER_NOT_MATCH("-2003", "用户不匹配"),

    /************************************************
     * StoreService相关错误码(业务级错误码)
     ************************************************/
    STORE_SERVICE_EMPTY("-2101", "未选择服务项目"),
    STORE_SERVICE_ID_EMPTY("-2102", "当前用户的服务项目ID为空"),
    STORE_SERVICE_USER_NOT_MATCH("-2103", "用户不匹配"),

    /************************************************
     * StoreUseGoods相关错误码(业务级错误码)
     ************************************************/
    STORE_USE_GOODS_CAT_EMPTY("-2201", "未选择类目！"),
    STORE_USE_GOODS_ID_EMPTY("-2202", "当前用户的常用商品ID为空"),
    STORE_USE_GOODS_USER_NOT_MATCH("-2203", "用户不匹配"),

    /************************************************
     * OrderExt相关错误码(业务级错误码)
     ************************************************/
    ORDER_EXT_ID_EMPTY("-2301", "订单ID为空"),
    ORDER_EXT_NOT_EXIST("-2302", "不存在该订单"),

    /************************************************
     * 城市站相关错误码(业务级错误码)
     ************************************************/
    CITY_STATION_EMPTY("-2401", "未配置城市站信息."),
    CITY_STATION_ID_ERROR("-2402", "城市站ID错误."),

    /************************************************
     * UserCar相关错误码(业务及错误码)
     ************************************************/
    USER_CAR_BRAND_ID_EMPTY("-2501", "汽车品牌不能为空."),
    USER_CAR_SERIES_ID_EMPTY("-2502", "车系不能为空."),
    USER_CAR_YEAR_ID_EMPTY("-2503", "年款不能为空."),
    USER_CAR_MODEL_ID_EMPTY("-2504", "车型不能为空."),
    USER_CAR_NO_SERVICE_GOODS("-2505", "该车型信息没有可服务商品."),
    USER_CAR_NOT_MATCH("-2506", "用户没有此车型."),
    USER_CAR_ID_IS_NULL("-2507", "用户车型ID为空."),
    USER_CAR_NOT_EXIST("-2508", "不存在的车型."),
    USER_CAR_CANNOT_REMOVE_DEFAULT("-2509", "用户默认车型不能删除."),

    /************************************************
     * Bom相关错误码(业务及错误码)
     ************************************************/
    BOM_NO_BUYER_STORE("-2601", "无门店提供服务."),
    BOM_CAT_SETTING_ERROR("-2602", "未配置可选的保养服务."),
    BOM_CAT_ID_EMPTY("-2603", "未知的保养类型."),
    BOM_NAME_EMPTY("-2604", "保养名称不能为空."),
    BOM_PRICE_EMPTY("-2605", "保养价格不能为空."),
    BOM_GOODS_ID_ERROR("-2606", "其他服务的GoodsId未配置,或配置出错"),
    BOM_IS_NULL("-2607", "未填写套餐信息"),
    BOM_NOT_EXIST("-2608", "不存在的套餐."),
    BOM_ITEM_NOT_EXIST("-2609", "不存在的套餐项."),

    /************************************************
     * Coupons相关错误码(业务及错误码)
     ************************************************/
    COUPONS_COUNT_ERROR("-2701", "优惠券数量有误！"),
    COUPONS_UNVALID("-2702", "存在不可使用的优惠券."),
    COUPONS_VALUE_EMPTY("-2703", "优惠券面值不能低于0."),
    USER_COUPONS_ID_IS_NULL("-2704", "未选择用户优惠券."),
    USER_COUPONS_NOT_EXIST("-2705", "用户不存在的优惠券."),
    STORE_COUPONS_ID_IS_NULL("-2706", "不存在的门店优惠券."),
    STORE_COUPONS_NOT_EXIST("-2707", "不存在的门店优惠券."),
    STORE_COUPONS_STORE_NOT_MATCH("-2708", "券的门店与登录者门店不一致."),
    STORE_COUPONS_STATUS_ERROR("-2709", "门店优惠券的状态不正确."),
    COUPONS_HAS_BENN_USED("-2710", "优惠券."),
    STORE_COUPONS_STOREID_IS_NULL("-2711", "券的门店不存在"),
    STORE_COUPONS_ALREADY_RECEIVE("-2712", "已领取过该优惠券"),

    /************************************************
     * Coupons相关错误码(业务及错误码)
     ************************************************/
    C_ORDER_UNKNOW_ORDER_QUERY_TYPE("-2801", "未知的订单查询类型."),

    /************************************************
    * 活动相关错误码(业务及错误码)
    ************************************************/
    ACTIVITY_ID_IS_NULL("-2901","活动Id不能为空."),
    ACTIVITY_NOT_EXIST("-2902","活动已结束或暂未开放."),
    ACTIVITY_RULE_IS_NULL("-2903","不存在的活动规则."),
    ACTIVITY_GOODS_ID_IS_NULL("-2904","活动商品ID为空."),
    ACTIVITY_GOODS_EMPTY("-2905","订单中不存在活动商品."),
    ACTIVITY_ERROR_GOODS("-2906","订单存在非活动商品."),
    ACTIVITY_GOODS_NOT_EXIST_OR_UN_SALE("-2907", "部分活动商品不存在或已下架."),
    ACTIVITY_KEY_IS_NULL("-2908","活动KEY不能为空."),
    ACTIVITY_RULE_SCRIPT_EXECUTE_ERROR("-2909","活动规则脚本执行出错."),
    ACTIVITY_CAN_NOT_BUY("-2920","不满足活动下单条件."),
    ACTIVITY_ORDER_EMPTY("-2921","活动订单数据为空！"),
    ACTIVITY_ORDER_HAS_NO_GOODS("-2922","活动订单不存在任何商品！"),
    ACTIVITY_ORDER_PRICE_SCRIPT_ERROR("-2923","计算订单金额结果为空."),
    ACTIVITY_ORDER_SPLIT_SCRIPT_ERROR("-2924","订单脚本分单结果为空."),
    ACTIVITY_ORDER_GOODS_SCRIPT_ERROR("-2925","订单脚本中未获取到商品信息."),
    ACTIVITY_ORDER_GOODS_NUM_NOT_MATCH("-2926","脚本中的商品数量和前端商品数量不一致."),

    /************************************************
     * 商户创建商品(业务级错误码)
     ************************************************/
    GOODS_DRAFT_IMG_EMPTY("-3001","商品图片不能为空！"),
    GOODS_DRAFT_NAME_EMPTY("-3002","商品名称不能为空！"),
    GOODS_DRAFT_QUALITY_EMPTY("-3003","商品品质不能为空！"),
    GOODS_DRAFT_BRAND_EMPTY("-3004","商品品牌不能为空！"),
    GOODS_DRAFT_PRICE_EMPTY("-3005","商品必须定价！"),
    GOODS_DRAFT_CATEGORY_EMPTY("-3006","必须选择类目！"),
    GOODS_DRAFT_QUALITY_ASSURE_EMPTY("-3007","质保选项必填！"),
    GOODS_DRAFT_ID_IS_NULL("-3008","商品id为null"),
    GOODS_DRAFT_NOT_EXIST("-3009","商品不存在！"),
    GOODS_DRAFT_SALE_PARAM_ERROR("-3010","参数错误，上下架操作状态有误！"),
    GOODS_DRAFT_BUY_MIN_EMPTY("-3011","商品起售数量不能为空！"),
    GOODS_DRAFT_BUY_STEP_EMPTY("-3012","商品售卖增量不能为空！"),

    /************************************************
     * WX Login(业务级错误码)
     ************************************************/
    WX_OPENID_EMPTY("-3101","微信openid不能为空！"),
    WX_REGISTER_USER_FAIL("-3102","微信创建用户失败！"),
    WX_UNKOWN_USER("-3103","未知的微信用户"),

    /************************************************
     *
     ***********************************************/
    WO_IMG_UPLOAD_ERROR("-3201", "上传图片出错"),


    /************************************************
     * 支付相关错误码(业务级错误码)
     ************************************************/
    PAY_ID_IS_NULL("-3300","未选择支付方式！"),
    PAY_ID_IS_NOT_EXIST("-3301","不存在的支付方式！"),
    PAY_NOT_EXIST_ORDER("-3302","不存在需要支付的订单！"),
    PAY_TOTAL_FEE_ERROR("-3303","支付金额不能小于0.00！"),
    PAY_SIZE_ERROR("-3304","可支付订单数量错误!"),
    /************************************************
     * 虚拟货币相关错误码(业务级错误码)
     ************************************************/
    COIN_BALANCE_NOT_ENOUGH("-3401","余额不足请充值。"),


    /************************************************
     * 接口安全验证失败(系统级错误码)
     ************************************************/
    ERROR_SECURITY_CODE("-1", "接口安全认证失败！");

    private String code;
    private String message;

    private ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
