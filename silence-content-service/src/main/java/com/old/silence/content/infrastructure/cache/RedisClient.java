package com.old.silence.content.infrastructure.cache;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.json.JacksonMapper;
import com.old.silence.json.JacksonUtils;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author YANGWENCHANG983
 */
@Component
public class RedisClient {

    private final StringRedisTemplate stringRedisTemplate;

    private final JacksonMapper jacksonMapper;

    private final Logger logger = LoggerFactory.getLogger(RedisClient.class);

    public RedisClient(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        ObjectMapper copiedMapper = objectMapper.copy();
        copiedMapper.setDefaultTyping(JacksonUtils.getDefaultTypeResolverBuilder());
        this.jacksonMapper = new JacksonMapper(copiedMapper);
    }

    /**
     * 先去缓存查询,如果没有的话 则查db,并且加入到缓存中
     * 忽略缓存穿透的场景，查不到的话不会设置空值
     */
    public <R, ID> R get(String key, ID id, Class<R> type, Function<ID, R> callback, Duration duration) {
        String cache = null;
        try {
            cache = stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(cache)) {
                if (String.class == type) {
                    return (R) cache;
                }
                return jacksonMapper.fromJson(cache, type);
            }
        } catch (Exception e) {
            // 记录异常,方便排查
            logger.error("error to fromJson,cache:" + cache, e);
        }
        R r = callback.apply(id);
        try {
            if (r != null) {
                stringRedisTemplate.opsForValue().set(key, jacksonMapper.toJson(r), duration);
            }
        } catch (Exception e) {
            // 记录异常,方便排查
            logger.error("error to toJson,key:" + key, e);
        }
        return r;
    }

    public Boolean delete(String key) {
        logger.info("clear cache,key:{}", key);
        return stringRedisTemplate.expire(key, Duration.ZERO);
    }

    public Long delete(Collection<String> keys) {
        logger.info("clear cache,keys:{}", JSON.toJSONString(keys));
        return stringRedisTemplate.delete(keys);
    }

    public Integer get(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        logger.info("根据key获取缓存成功,key={},value:{}", key, value);
        return Integer.valueOf((value == null || StringUtils.isBlank(value)) ? "-1" : value);
    }

    public List<String> get(List<String> keys) {
        List<String> values = stringRedisTemplate.opsForValue().multiGet(keys);
        logger.info("根据key获取缓存成功,keys={},values:{}", keys, values);
        return values;
    }

    public boolean checkAllExist(List<String> keys) {
        List<String> values = get(keys);
        return values.stream().allMatch(Objects::nonNull);
    }

    /**
     *
     */
    public List execute(RedisScript<List> script, List<String> keys, Object... args) {
        List list = stringRedisTemplate.execute(script, keys, args);
        logger.info("redisClient execute,keys:{},args:{},result:{}", JSON.toJSONString(keys), JSON.toJSONString(args),
                JSON.toJSONString(list));
        return list;
    }

    /**
     * 多keys扣减
     */
    public Boolean deductStock(List<String> keys, Object[] quantities) {
        RedisScript<List> script = RedisScript.of(LuaScriptUtils.getMultiStockDeductionLua(), List.class);
        List list = stringRedisTemplate.execute(script, keys, quantities);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("扣减成功,list:{}", list);
            return true;
        } else {
            logger.info("扣减失败,list:{}", list);
            return false;
        }
    }

    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void multiSetIfAbsent(Map<String, String> map) {
        stringRedisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    public Long increment(String key, int value) {
        return stringRedisTemplate.opsForValue().increment(key, value);
    }

    public Boolean addStock(List<String> keys, Object[] quantities) {
        RedisScript<List> script = RedisScript.of(LuaScriptUtils.getMultiStockAddLua(), List.class);
        List list = stringRedisTemplate.execute(script, keys, quantities);
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        return false;
    }

    public boolean setIfAbsent(String key, String value, Long timeout, TimeUnit unit) {
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
        return Boolean.TRUE.equals(result);
    }

    public void batchIncrement(Map<String, Long> data) {

        stringRedisTemplate.executePipelined(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
                for (var entry : data.entrySet()) {
                    stringRedisConn.incrBy(entry.getKey(), entry.getValue());
                }
                return null;
            }
        });
    }

    public void set(String key, String value, Long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public String getStrValue(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        logger.info("getStrValue,key={},value:{}", key, value);
        return value;
    }

    public Set<ZSetOperations.TypedTuple<String>> getTopNRank(String key, long count) {
        try {
            Set<ZSetOperations.TypedTuple<String>> result = stringRedisTemplate.opsForZSet().reverseRangeWithScores(key,
                    0, count - 1);

            logger.info("getTopNRank success, key={}, count={}, size={}", key, count,
                    result != null ? result.size() : 0);
            return result;
        } catch (Exception e) {
            logger.error("getTopNRank error, key={}, count={}", key, count, e);
            return Collections.emptySet();
        }
    }
}
