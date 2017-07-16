package com.nautybit.nautybee.common.param.wx;

import lombok.Data;

/**
 * Created by UFO on 17/7/15.
 */
@Data
public class BaseMessage {
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
}
