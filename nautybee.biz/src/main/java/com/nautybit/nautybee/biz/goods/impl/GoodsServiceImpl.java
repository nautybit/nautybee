package com.nautybit.nautybee.biz.goods.impl;

import java.util.ArrayList;
import java.util.List;

import com.nautybit.nautybee.biz.goods.GoodsAttributeService;
import com.nautybit.nautybee.entity.goods.GoodsAttribute;
import com.nautybit.nautybee.view.goods.GoodsAttributeView;
import com.nautybit.nautybee.view.goods.GoodsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.nautybit.nautybee.biz.base.BaseServiceImpl;
import com.nautybit.nautybee.biz.goods.GoodsService;
import com.nautybit.nautybee.dao.goods.GoodsDao;
import com.nautybit.nautybee.entity.goods.Goods;


@Service
public class GoodsServiceImpl extends BaseServiceImpl  implements GoodsService{

  @Autowired
  private GoodsDao goodsDao;
    @Autowired
    private GoodsAttributeService goodsAttributeService;

  public List<Goods> getAll() {
    return super.getAll(goodsDao);
  }


  public Goods getById(Long id) {
    return super.getById(goodsDao, id);
  }

  public boolean save(Goods goods) {
    return super.save(goodsDao, goods);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(goodsDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(goodsDao, ids);
  }

    @Override
    public List<GoodsView> queryGoodsByOrderIdList(List<Long> orderId){
        return goodsDao.queryGoodsByOrderIdList(orderId);
    }

    @Override
    public List<GoodsView> queryGoodsBySpuId(Long spuId){
        return goodsDao.queryGoodsBySpuId(spuId);
    }

    @Override
    public void fillGoodsAttribute(List<GoodsView> goodsViewList){
        List<Long> goodsIdList = new ArrayList<>();
        for(GoodsView goodsView:goodsViewList){
            Long goodsId = goodsView.getId();
            goodsIdList.add(goodsId);
        }
        List<GoodsAttributeView> goodsAttributeList = new ArrayList<>();
        if(goodsIdList.size() > 0){
            goodsAttributeList = goodsAttributeService.queryByGoodsIdList(goodsIdList);
        }
        for(GoodsView goodsView:goodsViewList){
            List<GoodsAttributeView> tempAttrList = new ArrayList<>();
            Long goodsId = goodsView.getId();
            for(GoodsAttributeView goodsAttributeView:goodsAttributeList){
                if(goodsAttributeView.getGoodsId().equals(goodsId)){
                    tempAttrList.add(goodsAttributeView);
                }
            }
            goodsView.setGoodsAttributeViewList(tempAttrList);
        }

    }

}
