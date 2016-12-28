package com.yuanpin.flora.test.web.es;


import com.yuanpin.flora.biz.goods.GoodsService;
import com.yuanpin.flora.dao.goods.GoodsDao;
import com.yuanpin.shared.entity.goods.Goods;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 16/1/11.
 */
@Test
@ContextConfiguration(locations = { "classpath:**/application-servlet.xml" })
public class SearchTest extends AbstractTestNGSpringContextTests {

    @Autowired
    BasicDataSource dataSource;

    @Autowired
    GoodsDao goodsDao;

    @Test
    void searchTest(){
        List<Long> ids = new ArrayList<>();
        ids.add(1001l);
        List<Goods> goods = goodsDao.selectByIds(ids);
        int a = 0;

    }
}
