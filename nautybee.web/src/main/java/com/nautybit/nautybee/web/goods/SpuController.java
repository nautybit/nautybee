package com.nautybit.nautybee.web.goods;

import com.nautybit.nautybee.biz.goods.SpuService;
import com.nautybit.nautybee.biz.user.UserService;
import com.nautybit.nautybee.common.result.Result;
import com.nautybit.nautybee.entity.goods.Spu;
import com.nautybit.nautybee.entity.user.User;
import com.nautybit.nautybee.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by UFO on 17/01/12.
 */
@Slf4j
@Controller
@RequestMapping("goods")
public class SpuController extends BaseController {
    @Autowired
    private SpuService spuService;


    @RequestMapping("getSpuList")
    @ResponseBody
    public Result<?> getSpuList() {
        Spu spu = spuService.getById(1000l);
        return Result.wrapSuccessfulResult(spu);
    }


}















