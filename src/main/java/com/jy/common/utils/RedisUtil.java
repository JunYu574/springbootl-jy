package com.jy.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: JunYu
 * @Date: 2024/3/6 19:36
 * @Description:
 * @Version: V1.0.0
 */
@Component
public class RedisUtil {

    /**
     * 如果使用 @Autowired 注解完成自动装配，那么
     * RedisTemplate要么不指定泛型,要么泛型为<Stirng,String>或者<Object,Object>
     * 如果你使用其他类型的 比如RedisTemplate<String,Object>
     * 那么请使用 @Resource 注解
     */
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 判断缓存是否存在
     *
     * @param key
     * @return
     */
    public Boolean containsKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Throwable e) {
            logger.error("判断缓存是否存在时失败key[" + key + "]", "err[" + e.getMessage() + "]");
        }
        return false;
    }

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    public boolean removeValue(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Throwable e) {
            logger.error("移除缓存时失败key[" + key + "]", "err[" + e.getMessage() + "]");
        }
        return false;
    }

    /**
     * 删除缓存
     *
     * @param key 键（一个或者多个）
     * @SuppressWarnings("unchecked") 忽略类型转换警告
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                //传入一个 Collection<String> 集合
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }


//    ============================== String ==============================

    /**
     * 根据key，获取缓存
     *
     * @param key
     * @return
     */
    public Object getValue(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Throwable e) {
            logger.error("获取缓存时失败key[" + key + "]", "err[" + e.getMessage() + "]");
        }
        return null;
    }

    /**
     * 缓存value
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean cacheValue(String key, Object value, long time) {
        try {
            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value);
            if (time > 0) {
                // 如果有设置超时时间的话
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败, value[" + value + "] " + e.getMessage());
        }
        return false;
    }

    /**
     * 缓存value，没有设置超时时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean cacheValue(String key, Object value) {
        return cacheValue(key, value, -1);
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 递增大小
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于 0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 递减大小
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于 0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

//    ============================== Hash ==============================

    /**
     * HashGet
     *
     * @param key  键（no null）
     * @param item 项（no null）
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 向一张 Hash表 中放入数据，并设置时间，如不存在则创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间（如果原来的 Hash表 设置了时间，这里会覆盖）
     * @return true / false
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
            hashOperations.put(key, item, value);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            logger.error("缓存[" + key + "]失败, item[" + item + "] " + e.getMessage());
        }
        return false;
    }

    /**
     * 向一张 Hash表 中放入数据，如不存在则创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true / false
     */
    public boolean hset(String key, String item, Object value) {
        return hset(key, item, value, -1);
    }

    /**
     * 删除 Hash表 中的值
     *
     * @param key  键
     * @param item 项（可以多个，no null）
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断 Hash表 中是否有该键的值
     *
     * @param key  键（no null）
     * @param item 值（no null）
     * @return true / false
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * Hash递增，如果不存在则创建一个，并把新增的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   递增大小 > 0
     * @return
     */
    public Double hincr(String key, String item, Double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * Hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   递减大小
     * @return
     */
    public Double hdecr(String key, String item, Double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

//    ============================== Map ==============================

    /**
     * 获取 key 对应的 map
     *
     * @param key 键（no null）
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Throwable e) {
            logger.error("获取缓存时失败key[" + key + "]", "err[" + e.getMessage() + "]");
        }
        return null;
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  值
     * @param time 时间
     * @return true / false
     */
    public boolean hmset(String key, Map<Object, Object> map, long time) {
        try {
            HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
            hashOperations.putAll(key, map);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败，" + e.getMessage());
        }
        return false;
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 值
     * @return true / false
     */
    public boolean hmset(String key, Map<Object, Object> map) {
        return hmset(key, map, -1);
    }

//    ============================== Set ==============================

    /**
     * 根据 key 获取 set 中的所有值
     *
     * @param key 键
     * @return 值
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Throwable e) {
            logger.error("获取缓存时失败key[" + key + "]", "err[" + e.getMessage() + "]");
        }
        return null;
    }

    /**
     * 从键为 key 的 set 中，根据 value 查询是否存在
     *
     * @param key   键
     * @param value 值
     * @return true / false
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Throwable e) {
            logger.error("从键为key[" + key + "]的set中，根据value[" + value + "]查询是否存在时失败", "err[" + e.getMessage() + "]");
        }
        return false;
    }

    /**
     * 将数据放入 set缓存，并设置时间
     *
     * @param key    键
     * @param time   时间
     * @param values 值（可以多个）
     * @return 成功放入个数
     */
    public long sSet(String key, long time, Object... values) {
        try {
            SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
            long count = setOperations.add(key, values);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return count;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败, " + e.getMessage());
        }
        return 0;
    }

    /**
     * 将数据放入 set缓存
     *
     * @param key    键值
     * @param values 值（可以多个）
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        return sSet(key, values, -1);
    }

    /**
     * 获取 set缓存的长度
     *
     * @param key 键
     * @return 长度
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Throwable e) {
            logger.error("获取[" + key + "]的缓存的长度失败, " + e.getMessage());
        }
        return 0;
    }

    /**
     * 移除 set缓存中，值为 value 的
     *
     * @param key    键
     * @param values 值
     * @return 成功移除个数
     */
    public long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            logger.error("移除[" + key + "]失败, " + e.getMessage());
        }
        return 0;
    }

//    ============================== List ==============================

    /**
     * 获取 list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束（0 到 -1 代表所有值）
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            logger.error("获取key[" + key + "]的缓存时失败", "err[" + e.getMessage() + "]");
        }
        return null;
    }

    /**
     * 获取 list缓存的长度
     *
     * @param key 键
     * @return 长度
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            logger.error("获取key[" + key + "]的list缓存的长度时失败", "err[" + e.getMessage() + "]");
        }
        return 0;
    }

    /**
     * 根据索引 index 获取键为 key 的 list 中的元素
     *
     * @param key   键
     * @param index 索引
     *              当 index >= 0 时 {0:表头, 1:第二个元素}
     *              当 index < 0 时 {-1:表尾, -2:倒数第二个元素}
     * @return 值
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            logger.error("根据索引 index[" + index + "] 获取键为 key[" + key + "]的 list 中的元素时失败, err[" + e.getMessage() + "]");
        }
        return null;
    }

    /**
     * 将值 value 插入键为 key 的 list 中，并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间
     * @return true / false
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            ListOperations<String, Object> listOperations = redisTemplate.opsForList();
            listOperations.rightPush(key, value);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            logger.error("将值 value 插入键为 key [" + key + "]的 list 中时失败, " + e.getMessage());
        }
        return false;
    }

    /**
     * 将值 value 插入键为 key 的 list 中，如果 list 不存在则创建空 list
     *
     * @param key   键
     * @param value 值
     * @return true / false
     */
    public boolean lSet(String key, Object value) {
        return lSet(key, value, -1);
    }

    /**
     * 将 values 插入键为 key 的 list 中，并设置时间
     *
     * @param key    键
     * @param values 值
     * @param time   时间
     * @return true / false
     */
    public boolean lSetList(String key, List<Object> values, long time) {
        try {
            ListOperations<String, Object> listOperations = redisTemplate.opsForList();
            listOperations.rightPushAll(key, values);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            logger.error("将 values 插入键为 key[" + key + "]的 list 中时失败, " + e.getMessage());
        }
        return false;
    }

    /**
     * 将 values 插入键为 key 的 list 中
     *
     * @param key    键
     * @param values 值
     * @return true / false
     */
    public boolean lSetList(String key, List<Object> values) {
        return lSetList(key, values, -1);
    }

    /**
     * 根据索引 index 修改键为 key 的值
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return true / false
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            logger.error("根据索引 index[" + index + "]修改键为 key [" + key + "]的值时失败, " + e.getMessage());
        }
        return false;
    }

    /**
     * 在键为 key 的 list 中删除值为 value 的元素
     *
     * @param key   键
     * @param count 如果 count == 0 则删除 list 中所有值为 value 的元素
     *              如果 count > 0 则删除 list 中最左边那个值为 value 的元素
     *              如果 count < 0 则删除 list 中最右边那个值为 value 的元素
     * @param value
     * @return
     */
    public long lRemove(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            logger.error("删除键为 key [" + key + "]的 list 中删除值为value [" + value + "]的元素时失败, " + e.getMessage());
        }
        return 0;
    }
}
