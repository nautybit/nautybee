package com.nautybit.nautybee.common.result.wx;

import lombok.Data;

/**
 * Created by UFO on 17/7/16.
 */
@Data
public class CreateQRCodeTicket {
    private String ticket;
    private Integer expire_seconds;
    private String url;
}
