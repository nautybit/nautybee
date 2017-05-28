package com.nautybit.nautybee.common.result.wx;

import lombok.Data;

/**
 * Created by UFO on 17/1/20.
 */
@Data
public class JsapiTicket {
    private String errcode;
    private String errmsg;
    private String ticket;
    private Integer expires_in;
}
