package com.nautybit.nautybee.common.result.wx;

import lombok.Data;

/**
 * Created by UFO on 17/1/20.
 */
@Data
public class JssdkConfig {
    public String appId;
    public Long timestamp;
    public String nonceStr;
    public String signature;
}
