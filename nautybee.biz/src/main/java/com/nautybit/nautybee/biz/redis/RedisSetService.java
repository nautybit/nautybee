package com.nautybit.nautybee.biz.redis;

import com.nautybit.nautybee.common.utils.PrintUtils;
import com.nautybit.nautybee.common.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Created by Minutch on 15/7/8.
 */
@Service
public class RedisSetService {

    private static final Logger logger = LoggerFactory.getLogger(RedisSetService.class);

    public Long sadd(String key,String... member){
        Jedis jedis = RedisUtils.getJedis();
        try {
            Long addNum = jedis.sadd(key, member);
            logger.debug("sadd:key=[" + key + "],member=[" + PrintUtils.printArray(member) + "],addNum=[" + addNum + "]");
            return addNum;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public Long scard(String key) {
        Jedis jedis = RedisUtils.getJedis();
        try {
            Long addNum = jedis.scard(key);
            logger.debug("scard:key=[" + key + "],num=[" + addNum + "]");
            return addNum;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public Long srem(String key,String... member){
        Jedis jedis = RedisUtils.getJedis();
        try {
            Long addNum = jedis.srem(key, member);
            logger.debug("srem:key=[" + key + "],member=[" + PrintUtils.printArray(member) + "],addNum=[" + addNum + "]");
            return addNum;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public boolean sismember(String key,String member){
        Jedis jedis = RedisUtils.getJedis();
        try {
            boolean result = jedis.sismember(key, member);
            logger.debug("sismember:key=[" + key + "],member=[" + member + "],result=[" + result + "]");
            return result;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }
    public Set<String> smembers(String key){
        Jedis jedis = RedisUtils.getJedis();
        try {
            Set<String> values = jedis.smembers(key);
            logger.debug("smembers:key=[" + key + "],result=[" + values + "]");
            return values;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }
}
