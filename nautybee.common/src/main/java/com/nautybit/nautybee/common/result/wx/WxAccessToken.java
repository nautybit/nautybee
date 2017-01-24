package com.nautybit.nautybee.common.result.wx;

import lombok.Data;

/**
 * Created by UFO on 17/1/20.
 */
@Data
public class WxAccessToken {
    private String access_token;
    private Integer expires_in;
}
