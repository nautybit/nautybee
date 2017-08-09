package com.nautybit.nautybee.common.param.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by UFO on 17/7/31.
 */
@Data
public class OrderParam {
    private Long orderId;
    private String orderSn;
    private Long goodsId;
    private Long storeId;
    private String studentName;
    private String contactMobile;
    private Integer isAtSchool;
    private String schoolName;
    private String schoolGrade;
    private String schoolClass;
    private String teacherName;
    private String teacherMobile;

    private BigDecimal totalFee;
    private String wxOpenid;

    private Long chainHeadId;
}
