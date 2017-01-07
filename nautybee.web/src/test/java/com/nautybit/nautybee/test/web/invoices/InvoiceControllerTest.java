package com.nautybit.nautybee.test.web.invoices;

import com.nautybit.nautybee.common.constant.PurchaseConstants;
import com.nautybit.nautybee.common.utils.DateUtils;
import com.nautybit.nautybee.common.utils.GenerationUtils;
import com.nautybit.nautybee.redis.RedisStringService;
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
