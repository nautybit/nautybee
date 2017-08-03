package com.nautybit.nautybee.view.goods;


import com.nautybit.nautybee.common.utils.StaticConfigUtils;
import com.nautybit.nautybee.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class GoodsPicView extends BaseEntity {

  private Long spuId;
  private String imgUrl;
  private String imgDesc;
  private Integer imgOrder;
  private Integer isMain;

    public void setImgUrl(String imgUrl) {
        if (imgUrl != null) {
            this.imgUrl = StaticConfigUtils.getServerUrl() + imgUrl;
        }
    }
}
