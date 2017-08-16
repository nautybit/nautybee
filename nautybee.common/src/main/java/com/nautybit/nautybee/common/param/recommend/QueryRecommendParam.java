package com.nautybit.nautybee.common.param.recommend;

import com.nautybit.nautybee.common.param.PageParam;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by UFO on 17/7/31.
 */
@Data
public class QueryRecommendParam extends PageParam {
    private String openid;
    private Integer beDeal;
    private Long currentOrderId;
    private Long currentRecommendId;
}
