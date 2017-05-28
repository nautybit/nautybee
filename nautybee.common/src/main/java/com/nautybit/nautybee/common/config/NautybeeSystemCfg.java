package com.nautybit.nautybee.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Minutch on 15/7/28.
 */
@Data
@Service
public class NautybeeSystemCfg {


    /**
     * 当前Flora环境
     */
    @Value("${nautybee.run.env}")
    private String nautybeeEnv;
    /**********************************************************************************
     *  Redis配置信息
     **********************************************************************************/
    @Value("${nautybee.master.redis.ip}")
    private String redisIp;
    @Value("${nautybee.master.redis.port}")
    private int redisPort;
    @Value("${nautybee.master.redis.password}")
    private String redisPassword;
    @Value("${nautybee.master.redis.timeout}")
    private int timeout;
    /**********************************************************************************
     * 接口密钥配置信息
     **********************************************************************************/
    @Value("${nautybee.security.interface.secret.code}")
    private String securityInterfaceSecretCode;
    /**
     * 接口安全认证开关(开启:on，关闭:off)
     */
    @Value("${nautybee.security.interface.switch}")
    private String securityInterfaceSwitch;
    /**********************************************************************************
     * 微信公众号配置信息
     **********************************************************************************/
    @Value("${nautybee.wechat.access.token.url}")
    private String wechatAccessTokenUrl;
    @Value("${nautybee.wechat.appid}")
    private String wechatAppid;
    @Value("${nautybee.wechat.secret}")
    private String wechatAppSecret;
    /**********************************************************************************
     * Flora服务器地址配置信息
     **********************************************************************************/
    @Value("${nautybee.server.url}")
    private String serverUrl;
    @Value("${nautybee.server.contextName}")
    private String contextName;
    /**********************************************************************************
     * Session UserId key
     **********************************************************************************/
    @Value("${openid.session.user.id.key:userId}")
    private String sessionUserIdKey;
    /**********************************************************************************
     * openId & comet
     */
    @Value("${openid.comet.enabled}")
    private Boolean cometEnabled;
    @Value("${openid.comet.server.url}")
    private String cometServerUrl;
    @Value("${openid.server.url}")
    private String openIdServerUrl;
    @Value("${openid.server.usessl}")
    private Boolean openIdServerUsessl;
    /**********************************************************************************
     * 配置文件目录
     **********************************************************************************/
    @Value("${nautybee.sec.data.dir}")
    private String secDataDir;
}












