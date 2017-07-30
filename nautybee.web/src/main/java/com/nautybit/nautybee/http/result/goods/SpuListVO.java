package com.nautybit.nautybee.http.result.goods;

import com.nautybit.nautybee.view.goods.SpuView;
import lombok.Data;

import java.util.List;

@Data
public class SpuListVO {
    private String catName;
    private List<SpuView> spuViewList;
}
