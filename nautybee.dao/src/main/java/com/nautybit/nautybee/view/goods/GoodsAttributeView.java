package com.nautybit.nautybee.view.goods;

import com.nautybit.nautybee.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
public class GoodsAttributeView extends BaseEntity {

    private Long goodsId;
    private Long attrId;
    private String attrValue;
    private String attrName;

}
