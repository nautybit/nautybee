package com.nautybit.nautybee.biz.redis;

import com.nautybit.nautybee.common.utils.PrintUtils;
import com.nautybit.nautybee.common.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by Minutch on 15/11/25.
 */
@Service
@Slf4j
public class RedisListService {

    public Long lpush(String key,String... member){
        Jedis jedis = RedisUtils.getJedis();
        try {
            Long num = jedis.lpush(key, member);
            log.debug("lpush:key=[" + key + "],member=[" + PrintUtils.printArray(member) + "],num=[" + num + "]");
            return num;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public Long llen(String key){
        Jedis jedis = RedisUtils.getJedis();
        try {
            Long num = jedis.llen(key);
            log.debug("llen:key=[" + key + "],num=[" + num + "]");
            return num;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public String lindex(String key,long index){
        Jedis jedis = RedisUtils.getJedis();
        try {
            String result = jedis.lindex(key, index);
            log.debug("lindex:key=[" + key + "],result=[" + result + "]");
            return result;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public long lrem(String key,long count,String value){
        Jedis jedis = RedisUtils.getJedis();
        try {
            long result = jedis.lrem(key, count, value);
            log.debug("lrem:key=[" + key + "],result=[" + result + "]");
            return result;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }

    public List<String> lrange(String key,long start,long end){
        Jedis jedis = RedisUtils.getJedis();
        try {
            List<String> resultList = jedis.lrange(key, start, end);
            log.debug("lrange:key=[" + key + "],start=[" + start + "], end=["+end+"]");
            return resultList;
        } finally {
            RedisUtils.returnRedisToPool(jedis);
        }
    }
}
