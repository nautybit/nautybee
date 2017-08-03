package com.nautybit.nautybee.view.order;

import com.nautybit.nautybee.entity.base.BaseEntity;
import com.nautybit.nautybee.entity.goods.GoodsAttribute;
import com.nautybit.nautybee.entity.goods.GoodsPropertyDetail;
import com.nautybit.nautybee.entity.order.OrderExt;
import com.nautybit.nautybee.view.goods.GoodsAttributeView;
import com.nautybit.nautybee.view.goods.GoodsPropertyDetailView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class OrderView extends BaseEntity {

  private String orderSn;
  private Long userId;
    private String wxOpenId;
    private String wxNickName;
    private Long storeId;
  private String orderStatus;
  private Integer buyerEvaluate;
  private String payStatus;
  private String payId;
  private BigDecimal orderAmount;
  private BigDecimal baseOrderAmount;
  private String orderAmountType;
  private Integer changeAmountTimes;
  private BigDecimal couponFee;
  private BigDecimal activityDiscountFee;
  private String remark;
  private BigDecimal receiptsAmount;
  private String firstOrder;
  private String cancelReasonType;
  private String cancelReason;

    private String goodsName;
    private List<GoodsPropertyDetailView> goodsPropertyDetailList;
    private List<GoodsAttributeView> goodsAttributeList;
    private OrderExt orderExt;
}
