package com.nautybit.nautybee.common.utils;

import com.nautybit.nautybee.common.config.NautybeeSystemCfg;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

/**
 * Created by Minutch on 15/6/23.
 */
@Component
public class RedisUtils {

    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    private NautybeeSystemCfg nautybeeSystemCfg;

    private String redisIp;
    private int redisPort;
    private String password;
    private int timeout;

    private static RedisUtils redisUtils;
    
    @PostConstruct
    public void init() {
        redisUtils = this;
        redisUtils.redisIp = this.nautybeeSystemCfg.getRedisIp();
        redisUtils.redisPort = this.nautybeeSystemCfg.getRedisPort();
        redisUtils.password = this.nautybeeSystemCfg.getRedisPassword();
        redisUtils.timeout = this.nautybeeSystemCfg.getTimeout();
    }


    private static JedisPool jedisPool = null;

    /**
     * 初始化Redis连接池
     */
    private synchronized static void initPool() {

        logger.info("register redis connection pool : " + redisUtils.redisIp);
        if (jedisPool == null) {
            try {
                JedisPoolConfig config = new JedisPoolConfig();
                jedisPool = new JedisPool(config, redisUtils.redisIp, redisUtils.redisPort,redisUtils.timeout, null,1);
            } catch (Exception e) {
                logger.error("First create JedisPool error : "+e);
            }
        }
    }

    /**
     * 获取Redis实例
     * @return
     */
    public static Jedis getJedis() {
        if (jedisPool == null) {
            initPool();
        }
        return  jedisPool.getResource();
    }

    /**
     * 将Redis实例返回给Redis池
     * @param jedis
     */
    public static void returnRedisToPool(Jedis jedis) {
        if(jedis != null){
            jedis.close();
        }
    }
}
