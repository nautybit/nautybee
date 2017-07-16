package com.nautybit.nautybee.common.result.wx;

import lombok.Data;

import java.util.List;

/**
 * Created by UFO on 17/7/16.
 */
@Data
public class UserInfo {
    private int subscribe;
    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private int subscribe_time;
    private String unionid;
    private String remark;
    private int groupid;
    private List<Integer> tagid_list;
}
