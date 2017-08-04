package com.nautybit.nautybee.web.company;

import com.nautybit.nautybee.web.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by UFO on 17/01/19.
 */
@Slf4j
@Controller
@RequestMapping("company")
public class CompanyController extends BaseController {


    @RequestMapping("introduction")
    public String introduction(ModelMap model){
        return "company/introduction";
    }

    @RequestMapping("joinUs")
    public String joinUs(ModelMap model){
        return "company/joinUs";
    }
}















