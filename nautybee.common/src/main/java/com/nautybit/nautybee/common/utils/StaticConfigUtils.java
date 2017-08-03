package com.nautybit.nautybee.common.utils;

import com.nautybit.nautybee.common.config.NautybeeSystemCfg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Minutch on 15/9/14.
 */
@Component
@Slf4j
public class StaticConfigUtils {
    private static String serverUrl;

    @Autowired
    private NautybeeSystemCfg nautybeeSystemCfg;

    @PostConstruct
    public void init() {
        serverUrl = nautybeeSystemCfg.getServerUrl();
    }
    public static String getServerUrl() {return serverUrl;}
}
