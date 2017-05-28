package com.nautybit.nautybee.biz.redis;

import com.nautybit.nautybee.common.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * Created by Minutch on 15/6/24.
 */
@Service
public class RedisHashService {

    private static final Logger logger = LoggerFactory.getLogger(RedisHashService.class);

    public Long hset(String key,String field,String value){
        Jedis jedis = RedisUtils.getJedis();
        try {
            Long result = jedis.hset(key, field, value);
            if(logger.isDebugEnabled()){
            	logger.debug("hset:key=[" + key + "],field=[" + field + "],value=[" + value + "]result=[" + result + "]");
            }
            return result;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public Long hincrBy(String key,String field,long value) {
        Jedis jedis = RedisUtils.getJedis();
        try {
            Long result = jedis.hincrBy(key, field, value);
            if(logger.isDebugEnabled()){
                logger.debug("hset:key=[" + key + "],field=[" + field + "],value=[" + value + "]result=[" + result + "]");
            }
            return result;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public Long hsetexp(String key,String field,String value,Integer lifeSec){
        Jedis jedis = RedisUtils.getJedis();
        try {
            Long result = jedis.hset(key, field, value);
            jedis.expire(key,lifeSec);
            if(logger.isDebugEnabled()){
                logger.debug("hset:key=[" + key + "],field=[" + field + "],value=[" + value + "]result=[" + result + "]");
            }
            return result;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }


    public String hget(String key,String field){
        Jedis jedis = RedisUtils.getJedis();
        try {
            String result = jedis.hget(key, field);
            if(logger.isDebugEnabled()){
            	logger.debug("hget:key=[" + key + "],field=[" + field + "]result=[" + result + "]");
            }
            return result;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public Map<String,String> hgetall(String key){
        Jedis jedis = RedisUtils.getJedis();
        try {
            Map<String,String> result = jedis.hgetAll(key);
            if(logger.isDebugEnabled()){
                logger.debug("hgetall:key=[" + key + "],result=[" + result + "]");
            }
            return result;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public String hmset(String key,Map<String,String>hash){
        Jedis jedis = RedisUtils.getJedis();
        try {
            String result = jedis.hmset(key, hash);
            if(logger.isDebugEnabled()){
            	logger.debug("hmset:key=[" + key + "],hash=[" + hash + "]");
            }
            return result;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public String hmsetexp(String key,Map<String,String>hash,Integer lifeSec){
        Jedis jedis = RedisUtils.getJedis();
        try {
            String result = jedis.hmset(key, hash);
            jedis.expire(key,lifeSec);
            if(logger.isDebugEnabled()){
                logger.debug("hmset:key=[" + key + "],hash=[" + hash + "]");
            }
            return result;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public List<String> hmget(String key,String... field){
        Jedis jedis = RedisUtils.getJedis();
        try {
            List<String> result = jedis.hmget(key,field);
            if(logger.isDebugEnabled()){
            	logger.debug("hmget:key=[" + key + "],field=[" + field  + "]");
            }
            return result;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public Boolean hexists(String key,String field){
        Jedis jedis = RedisUtils.getJedis();
        try {
            Boolean result = jedis.hexists(key,field);
            if(logger.isDebugEnabled()){
            	logger.debug("hexists:key=[" + key + "],field=[" + field  + "]");
            }
            return result;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public Long hdel(String key,String... field){
        Jedis jedis = RedisUtils.getJedis();
        try {
            Long result = jedis.hdel(key,field);
            if(logger.isDebugEnabled()){
            	logger.debug("hdel:key=[" + key + "],field=[" + field  + "]");
            }
            return result;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

}