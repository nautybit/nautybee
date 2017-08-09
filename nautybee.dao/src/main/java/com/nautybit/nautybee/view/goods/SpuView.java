package com.nautybit.nautybee.view.goods;

import com.nautybit.nautybee.common.config.NautybeeSystemCfg;
import com.nautybit.nautybee.common.utils.StaticConfigUtils;
import com.nautybit.nautybee.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class SpuView extends BaseEntity {

  private String spuSn;
  private Long storeId;
  private Long catId;
  private String goodsName;
  private BigDecimal goodsPrice;
  private BigDecimal alipayDiscount;
  private BigDecimal wechatpayDiscount;
  private BigDecimal unionpayDiscount;
  private String unit;
  private String spuImg;
    private String spuIcon;
  private String goodsType;
    private Integer nodeNum;
  private String goodsNickName;
  private Integer saleVolume;
  private Integer visitNum;
  private String goodsBrief;
  private String goodsDesc;
  private String keywords;
  private Integer buyMin;
  private Integer buyStep;
  private String stepPriceCfg;
    private Long showOrder;
    private String remark;

    private String catName;
    private List<GoodsPicView> picList;

    public void setSpuIcon(String spuIcon) {
        if (spuIcon != null) {
            this.spuIcon = StaticConfigUtils.getServerUrl() + spuIcon;
        }
    }
}
