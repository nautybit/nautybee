package com.nautybit.nautybee.web.wx;

import com.nautybit.nautybee.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by UFO on 17/01/24.
 */
@Slf4j
@Controller
@RequestMapping("wx/store")
public class StoreController extends BaseController {

    @RequestMapping("/getStoreList")
    public String getStoreList(){
        return "wx/storeList";
    }
}















