package com.nautybit.nautybee.common.result.wx;

import lombok.Data;

/**
 * Created by UFO on 17/1/20.
 */
@Data
public class WxAuthToken {
    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
}
