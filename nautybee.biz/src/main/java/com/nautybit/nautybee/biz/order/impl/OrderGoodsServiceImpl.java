package com.nautybit.nautybee.biz.order.impl;

import java.util.List;

import com.nautybit.nautybee.biz.goods.GoodsService;
import com.nautybit.nautybee.common.param.order.OrderParam;
import com.nautybit.nautybee.view.goods.GoodsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.order.OrderGoodsService;
import com.nautybit.nautybee.dao.order.OrderGoodsDao;
import com.nautybit.nautybee.entity.order.OrderGoods;


@Service
public class OrderGoodsServiceImpl extends BaseServiceImpl  implements OrderGoodsService{

  @Autowired
  private OrderGoodsDao orderGoodsDao;
    @Autowired
    private GoodsService goodsService;

  public List<OrderGoods> getAll() {
    return super.getAll(orderGoodsDao);
  }


  public OrderGoods getById(Long id) {
    return super.getById(orderGoodsDao, id);
  }

  public boolean save(OrderGoods orderGoods) {
    return super.save(orderGoodsDao, orderGoods);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(orderGoodsDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(orderGoodsDao, ids);
  }

    public void createOrderGoods(OrderParam orderParam){
        Long goodsId = orderParam.getGoodsId();
        GoodsView goodsView = goodsService.queryGoodsById(goodsId);

        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setDefaultBizValue();
        orderGoods.setOrderId(orderParam.getOrderId());
        orderGoods.setGoodsId(orderParam.getGoodsId());
        orderGoods.setSpuId(goodsView.getSpuId());
        orderGoods.setPropDetailId1(goodsView.getPropDetailId1());
        orderGoods.setPropDetailId2(goodsView.getPropDetailId2());
        orderGoods.setPropDetailName1(goodsView.getDetailName1());
        orderGoods.setPropDetailName2(goodsView.getDetailName2());
        orderGoods.setGoodsName(goodsView.getGoodsName());
        orderGoods.setGoodsImg(goodsView.getGoodsImg());
        orderGoods.setGoodsNumber(1l);
        orderGoods.setStoreId(orderParam.getStoreId());
        orderGoods.setGoodsSn(goodsView.getGoodsSn());
        save(orderGoods);
    }
}
