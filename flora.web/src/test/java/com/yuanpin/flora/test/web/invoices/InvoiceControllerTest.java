package com.yuanpin.flora.test.web.invoices;

import com.yuanpin.flora.common.constant.PurchaseConstants;
import com.yuanpin.flora.common.utils.DateUtils;
import com.yuanpin.flora.common.utils.GenerationUtils;
import com.yuanpin.flora.redis.RedisStringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Created by lixinlong on 15/12/14.
 */
public class InvoiceControllerTest {
    @Test
    public void testGetApplySn() throws Exception {
        String yyMMdd = DateUtils.formatDate(new Date(), DateUtils.YYMMDD);
        
        System.out.println("时间:时间:时间:时间:"+yyMMdd);
    }
}
