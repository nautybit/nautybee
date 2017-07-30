package com.nautybit.nautybee.web.order;

import com.google.gson.Gson;
import com.nautybit.nautybee.biz.goods.GoodsPropertyDetailService;
import com.nautybit.nautybee.biz.goods.GoodsPropertyService;
import com.nautybit.nautybee.biz.goods.GoodsService;
import com.nautybit.nautybee.biz.goods.SpuService;
import com.nautybit.nautybee.entity.goods.GoodsProperty;
import com.nautybit.nautybee.entity.goods.GoodsPropertyDetail;
import com.nautybit.nautybee.entity.goods.Spu;
import com.nautybit.nautybee.view.goods.GoodsView;
import com.nautybit.nautybee.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by UFO on 17/01/12.
 */
@Slf4j
@Controller
@RequestMapping("wx/order")
public class OrderController extends BaseController {

    @Value("${nautybee.wechat.appid}")
    private String wechatappid;
    @Value("${nautybee.wechat.secret}")
    private String wechatsecret;
    @Autowired
    private SpuService spuService;
    @Autowired
    private GoodsPropertyService goodsPropertyService;
    @Autowired
    private GoodsPropertyDetailService goodsPropertyDetailService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("confirmOrder")
    public String confirm(ModelMap model,Long spuId) {


        Spu spu = spuService.getById(spuId);
        List<GoodsProperty> goodsPropertyList = goodsPropertyService.getBySpuId(spuId);
        List<GoodsView> goodsViewList = goodsService.queryGoodsBySpuId(spuId);
        goodsService.fillGoodsAttribute(goodsViewList);
        if(goodsPropertyList.size()==1){
            model.addAttribute("propLength",1);
            model.addAttribute("propName",goodsPropertyList.get(0).getPropName());
            model.addAttribute("goodsViewList",goodsViewList);
        }else{
            model.addAttribute("propLength",2);
            model.addAttribute("propName1",goodsPropertyList.get(0).getPropName());
            model.addAttribute("propName2",goodsPropertyList.get(1).getPropName());

            Long mainPropId = goodsPropertyList.get(0).getId();
            List<GoodsPropertyDetail> mainPropertyDetailList = goodsPropertyDetailService.selectByPropId(mainPropId);
            model.addAttribute("mainPropertyDetailList",mainPropertyDetailList);
            Map<String,List<GoodsView>> mainDetailGoodsListMap = new HashMap<>();
            for(GoodsView goodsView:goodsViewList){
                String mainDetailName = goodsView.getDetailName1();
                if(mainDetailGoodsListMap.containsKey(mainDetailName)){
                    mainDetailGoodsListMap.get(mainDetailName).add(goodsView);
                }else {
                    List<GoodsView> tempList = new ArrayList<>();
                    tempList.add(goodsView);
                    mainDetailGoodsListMap.put(mainDetailName,tempList);
                }
            }
            model.addAttribute("mainDetailGoodsListMap",mainDetailGoodsListMap);
        }

        return "order/confirmOrder";
    }




}















