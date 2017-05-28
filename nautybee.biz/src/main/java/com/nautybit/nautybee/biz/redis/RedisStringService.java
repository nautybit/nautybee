package com.nautybit.nautybee.biz.redis;

import com.nautybit.nautybee.common.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * Created by Minutch on 15/6/24.
 */
@Service
public class RedisStringService {

    private static final Logger logger = LoggerFactory.getLogger(RedisStringService.class);

    /**
     * 设置key-value，并对key设置过期时间
     * @param key
     * @param value
     * @param expireTime 单位：秒
     */
    public void setEx(String key,String value,int expireTime) {
        Jedis jedis = RedisUtils.getJedis();
        try {
            jedis.setex(key, expireTime, value);
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
        logger.info("setEx:key=["+key+"],value=["+value+"],expire=["+expireTime+"]");
    }

    /**
     * 通过key获取value
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = RedisUtils.getJedis();
        String result = null;
        try {
            result = jedis.get(key);
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
        return result;
    }

    /**
     * 设置key失效时间
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(String key,int seconds) {
        Jedis jedis = RedisUtils.getJedis();
        Long result = null;
        try {
            result = jedis.expire(key,seconds);
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
        return result;
    }

    /**
     * 删除key
     * @param key
     * @return
     */
    public Long del(String key) {
        Jedis jedis = RedisUtils.getJedis();
        Long result = null;
        try {
            result = jedis.del(key);
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
        logger.info("del:key=["+key+"],result=["+result+"]");
        return result;
    }

    /**
     * 自增key
     * @param key
     * @return
     */
    public Long incr(String key) {
        Jedis jedis = RedisUtils.getJedis();
        Long result = null;
        try {
            result = jedis.incr(key);
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
        logger.info("incr:key=["+key+"],result=["+result+"]");
        return result;
    }

    /**
     * 自增key
     * @param key
     * @return
     */
    public Long incrBy(String key,long val) {
        Jedis jedis = RedisUtils.getJedis();
        Long result = null;
        try {
            result = jedis.incrBy(key,val);
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
        logger.info("incr:key=["+key+"],result=["+result+"]");
        return result;
    }

    /**
     * 自增key
     * @param key
     * @return
     */
    public Double incrByFloat(String key,double val) {
        Jedis jedis = RedisUtils.getJedis();
        Double result = null;
        try {
            result = jedis.incrByFloat(key, val);
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
        logger.info("incr:key=["+key+"],result=["+result+"]");
        return result;
    }


    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean exists(String key) {
        Jedis jedis = RedisUtils.getJedis();
        boolean result;
        try {
            result = jedis.exists(key);
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
        logger.info("exists:key=["+key+"],result=["+result+"]");
        return result;
    }
}
