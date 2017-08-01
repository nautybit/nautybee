package com.nautybit.nautybee.common.param.pay;

import lombok.Data;

/**
 * 发放红包接口参数
 * Created by UFO on 17/8/1.
 */
@Data
public class SendRedBagParam {
    private String nonce_str;//随机字符串，不长于32位。推荐随机数生成算法
    private String sign;//签名，详见签名生成算法
    private String mch_billno;//商户订单号
    private String mch_id;//微信支付分配的商户号
    private String wxappid;//微信分配的公众账号ID（企业号corpid即为此appId）
    private String send_name;//红包发送者名称
    private String re_openid;//trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。下单前需要调用【网页授权获取用户信息】接口获取到用户的Openid。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换。
    private Integer total_amount;//付款金额，单位分
    private Integer total_num;//红包发放总人数
    private String wishing;//红包祝福语
    private String client_ip;//调用接口的机器Ip地址
    private String act_name;//活动名称
    private String remark;//备注
}
