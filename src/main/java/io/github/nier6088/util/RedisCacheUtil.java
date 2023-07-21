package io.github.nier6088.util;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisCacheUtil<T> {

    @Autowired
    @Qualifier("redisTemplate")
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value, long time) {
        time += RandomUtil.randomLong(1, 30);
        log.info("[setObject-key] = " + key + " [value] = " + value);

        ValueOperations<String, T> operation = redisTemplate.opsForValue();

        operation.set(key, value, time, TimeUnit.SECONDS);

        return operation;

    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(String key) {

        log.info("[getObject-key] = " + key);

        ValueOperations<String, T> operation = redisTemplate.opsForValue();

        T value = operation.get(key);

        log.info("[getObject-key] = " + key + " [getObject-value] = " + value);
        return value;

    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */

    public <T> ListOperations<String, T> setCacheList(String key, List<T> dataList) {

        ListOperations listOperation = redisTemplate.opsForList();

        if (null != dataList) {

            int size = dataList.size();

            for (int i = 0; i < size; i++) {
                listOperation.rightPush(key, dataList.get(i));
            }

        }

        return listOperation;

    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */

    public <T> List<T> getCacheList(String key) {

        List<T> dataList = new ArrayList<T>();

        ListOperations<String, T> listOperation = redisTemplate.opsForList();

        Long size = listOperation.size(key);

        for (int i = 0; i < size; i++) {

            dataList.add(listOperation.leftPop(key));

        }

        return dataList;

    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {

        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);

        Iterator<T> it = dataSet.iterator();

        while (it.hasNext()) {

            setOperation.add(it.next());

        }

        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public Set<T> getCacheSet(String key) {

        Set<T> dataSet = new HashSet<T>();

        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);

        Long size = operation.size();

        for (int i = 0; i < size; i++) {

            dataSet.add(operation.pop());

        }

        return dataSet;

    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    public <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap) {

        HashOperations hashOperations = redisTemplate.opsForHash();

        if (null != dataMap) {

            for (Map.Entry<String, T> entry : dataMap.entrySet()) {

                hashOperations.put(key, entry.getKey(), entry.getValue());

            }
        }

        return hashOperations;

    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(String key) {

        Map<String, T> map = redisTemplate.opsForHash().entries(key);

        return map;

    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    public <T> HashOperations<String, Integer, T> setCacheIntegerMap(String key, Map<Integer, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<Integer, T> entry : dataMap.entrySet()) {

                hashOperations.put(key, entry.getKey(), entry.getValue());

            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<Integer, T> getCacheIntegerMap(String key) {

        Map<Integer, T> map = redisTemplate.opsForHash().entries(key);

        return map;

    }

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    public boolean removeSingleCache(String key) {
        try {
            log.info("[removeSingleCache] = " + key);
            redisTemplate.delete(key);
            return true;
        } catch (Throwable t) {
            log.error("获取缓存失败key[" + key + ", error[" + t + "]");
        }

        return false;
    }

    /**
     * 随机获得缓存的set
     *
     * @param key
     * @return
     */
    public Set<T> getCacheSetForRandomEle(String key, long count) {

        log.info("[getCacheSetForRandomEle] = " + key);
        Set<T> dataSet = redisTemplate.opsForSet().distinctRandomMembers(key, count);

        return dataSet;
    }

    /**
     * 往Set中添加元素
     *
     * @param key
     * @param value
     * @return
     */
    public Long addEleToSet(String key, T value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 删除set中的元素
     *
     * @param key
     * @param o
     * @return
     */
    public boolean removeEleInSet(String key, Object o) {
        Long result = redisTemplate.opsForSet().remove(key, o);
        checkResult(result);
        return result == 1;
    }

    protected void checkResult(@Nullable Object obj) {

        if (obj == null) {
            throw new IllegalStateException("Cannot read collection with Redis connection in pipeline/multi-exec mode");
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {

        return redisTemplate.getExpire(key, TimeUnit.SECONDS);

    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null，为redis缓存key存储的map数据结构的key
     * @return true 存在 false不存在
     */
    public boolean hashHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null，为redis缓存key存储的map数据结构的key
     */
    public void hashdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    public Cursor hashCursor(String key, long count) {

        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.scanOptions().count(1000).build());

        return cursor;
    }

    /**
     * 获取hash中field对应的值
     *
     * @param key
     * @param item
     * @return
     */
    public Object hashget(String key, String item) {

        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * hash递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long hashIncr(String key, String field, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    /**
     * @param key
     * @param data
     * @return org.springframework.data.redis.core.ListOperations<java.lang.String, T>
     * @author Czhao
     * @date 2021-12-06 19:27
     * @version 1.5.5
     */
    public <T> ListOperations<String, T> setSingleCacheToList(String key, T data, long time) {

        ListOperations listOperation = redisTemplate.opsForList();

        if (null != data) {
            if (redisTemplate.hasKey(key)) {
                listOperation.rightPush(key, data);
            } else {
                listOperation.rightPush(key, data);
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        }

        return listOperation;

    }

    /**
     * 获取列表（不删除数据）
     *
     * @param key
     * @return java.util.List<T>
     * @author Czhao
     * @date 2021-12-07 09:09
     * @version 1.5.5
     */
    public <T> List<T> getCacheListNoDel(String key) {

        ListOperations<String, T> listOperation = redisTemplate.opsForList();

        List<T> dataList = listOperation.range(key, 0, -1);

        return dataList;

    }


    /**
     * @param key
     * @param data
     * @return org.springframework.data.redis.core.ListOperations<java.lang.String, T>
     * @author dingjiahui
     * @date 2022-07-26 19:27
     * @version 2.3.2
     */
    public <T> SetOperations<String, T> setSingleCacheToSet(String key, T data, long time) {
        SetOperations setOperations = redisTemplate.opsForSet();

        if (null != data) {
            if (redisTemplate.hasKey(key)) {
                setOperations.add(key, data);
            } else {
                setOperations.add(key, data);
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        }
        return setOperations;
    }

    /**
     * 获取列表（不删除数据）
     *
     * @param key
     * @return java.util.List<T>
     * @author Czhao
     * @date 2021-12-07 09:09
     * @version 1.5.5
     */
    public <T> Set<T> getCacheSetNoDel(String key) {

        SetOperations<String, T> setOperation = redisTemplate.opsForSet();

        Set<T> dataSet = setOperation.members(key);

        return dataSet;

    }
}
