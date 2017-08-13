package com.nautybit.nautybee.web.order;

import com.google.gson.Gson;
import com.nautybit.nautybee.biz.goods.*;
import com.nautybit.nautybee.biz.order.OrderExtService;
import com.nautybit.nautybee.biz.order.OrderGoodsService;
import com.nautybit.nautybee.biz.order.OrderService;
import com.nautybit.nautybee.biz.recommend.RecommendService;
import com.nautybit.nautybee.biz.wx.WxService;
import com.nautybit.nautybee.common.constant.goods.GoodsTypeEnum;
import com.nautybit.nautybee.common.param.order.OrderParam;
import com.nautybit.nautybee.common.result.Result;
import com.nautybit.nautybee.common.utils.DateUtils;
import com.nautybit.nautybee.entity.goods.GoodsProperty;
import com.nautybit.nautybee.entity.goods.GoodsPropertyDetail;
import com.nautybit.nautybee.entity.goods.Spu;
import com.nautybit.nautybee.entity.goods.SpuChain;
import com.nautybit.nautybee.entity.order.Order;
import com.nautybit.nautybee.view.goods.GoodsView;
import com.nautybit.nautybee.view.order.OrderView;
import com.nautybit.nautybee.view.recommend.RecommendView;
import com.nautybit.nautybee.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


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
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderGoodsService orderGoodsService;
    @Autowired
    private OrderExtService orderExtService;
    @Autowired
    private RecommendService recommendService;
    @Autowired
    private WxService wxService;
    @Autowired
    private SpuChainService spuChainService;

    @RequestMapping("confirmOrder")
    public String confirm(ModelMap model,Long spuId) {


        Spu spu = spuService.getById(spuId);
        model.addAttribute("storeId",spu.getStoreId());
        model.addAttribute("goodsPrice",spu.getGoodsPrice());
        model.addAttribute("goodsName",spu.getGoodsName());
        //分期商品下单，查出当前有效的spu，并查处对应的goods及相关信息
        if(GoodsTypeEnum.Chain.name().equals(spu.getGoodsType())){
            model.addAttribute("chainHeadId",spu.getId());
            SpuChain spuChain = spuChainService.selectCurrentNode(spuId);
            spuId = spuChain.getNodeId();
        }
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

    @RequestMapping("createOrder")
    @ResponseBody
    @Transactional
    public Result<?> createOrder(@RequestBody OrderParam orderParam) throws RuntimeException{

        String wxOpenid = orderParam.getWxOpenid();
        if(StringUtils.isEmpty(wxOpenid)){
            log.error("报名时openid为空");
            return Result.wrapErrorResult("","网络异常");
        }
        try {
            Order order = orderService.createOrder(orderParam);
            orderParam.setOrderId(order.getId());
            orderParam.setOrderSn(order.getOrderSn());
            orderGoodsService.createOrderGoods(orderParam);
            orderExtService.createOrderExt(orderParam);
            return Result.wrapSuccessfulResult(orderParam);
        }catch (RuntimeException e){
            log.error("创建订单失败",e);
            throw new RuntimeException();
        }
    }

    @RequestMapping("orderList")
    public String orderList(ModelMap modelMap,String code,String state) {

        String openid = wxService.getOpenId(authUrl,code);
        System.out.println("openid from orderList:"+openid);
        if(StringUtils.isEmpty(openid)){
            Map cookieMap = this.getCookieMap();
            /*获取openId*/
            openid = (String) this.getViewRequestParam(cookieMap, "wxOpenId", "");
        }
        modelMap.addAttribute("openid", openid);

        List<Order> orderList = orderService.queryByOpenId(openid);
        List<OrderView> orderViewList = new ArrayList<>();
        for(Order order:orderList){
            OrderView orderView = orderService.makeOrderView(order);
            orderViewList.add(orderView);
        }
        modelMap.addAttribute("orderViewList",orderViewList);
        return "order/orderList";
    }

    @RequestMapping("recommendList")
    public String recommendList(ModelMap modelMap,String code,String state) {

        String openid = wxService.getOpenId(authUrl,code);
        System.out.println("openid from recommendList:"+openid);
        if(StringUtils.isEmpty(openid)){
            Map cookieMap = this.getCookieMap();
            /*获取openId*/
            openid = (String) this.getViewRequestParam(cookieMap, "wxOpenId", "");
        }
        modelMap.addAttribute("openid", openid);

        List<RecommendView> recommendViewList = recommendService.selectByFromUser(openid);
        for(RecommendView recommendView:recommendViewList){
            Date gmtCreate = recommendView.getGmtCreate();
            recommendView.setGmtCreateStr(DateUtils.dateFormat(gmtCreate,DateUtils.Y_M_D_HMS));
            Date orderGmtCreate = recommendView.getOrderGmtCreate();
            recommendView.setOrderGmtCreateStr(DateUtils.dateFormat(orderGmtCreate,DateUtils.Y_M_D_HMS));
        }
        modelMap.addAttribute("recommendViewList",recommendViewList);
        return "order/recommendList";
    }

}















