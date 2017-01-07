package com.nautybit.nautybee.test.web.goods;

import com.nautybit.nautybee.test.web.base.BaseTest;
import org.testng.annotations.Test;

/**
 * Created by Minutch on 15/7/9.
 */
public class GoodsControllerTest extends BaseTest {

    private static final String module = "goods/";
    @Test
    public void testQueryHotGoods() {

        int start = 0;
        int pageSize = 10;

//        String param = "start="+start+"&pageSize="+pageSize;
//        String response = HttpUtils.sendPost(host + module + "queryHotGoods", param);
//        Gson gson = new Gson();
//        Result<List<GoodsView>> result = gson.fromJson(response, new TypeToken<Result<List<GoodsView>>>(){}.getType());
//        //判断接口返回结果是否符合预期
//        Assert.assertEquals(result.isSuccess(), true);
    }
}
