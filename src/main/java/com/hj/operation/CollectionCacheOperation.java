package com.hj.operation;

import com.hj.functional.DataCache;
import com.hj.functional.RealData;
import com.hj.functional.RealDataMap;
import com.hj.functional.RealDataSet;
import com.hj.properties.RedissonProperties;
import org.redisson.api.RMapCache;
import org.redisson.api.RSetCache;
import org.redisson.api.RedissonClient;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: hj
 * @date: 2023/1/16
 * @time: 10:44 AM
 */
public class CollectionCacheOperation {
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private RedissonProperties redissonProperties;

    /**
     * 获取map集合
     *
     * @param name
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> RMapCache<K, V> getMapCache(String name) {
        return redissonClient.getMapCache(name);
    }

    /**
     * 获取map集合,如果没有则通过实时数据
     *
     * @param name
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> RMapCache<K, V> getMapCache(String name, RealDataMap<K, V> realDataMap) {
        return getMapCache(name, realDataMap, redissonProperties.getDataValidTime());
    }

    /**
     * 获取map集合,如果没有则通过实时数据
     *
     * @param name
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> RMapCache<K, V> getMapCache(String name, RealDataMap<K, V> realDataMap, Long time) {
        RMapCache<Object, Object> map = redissonClient.getMapCache(name);
        if (map == null || map.size() == 0) {
            Map<K, V> objectObjectMap = realDataMap.get();
            setMapCacheValues(name, objectObjectMap, time);
        }
        return redissonClient.getMapCache(name);
    }

    /**
     * 获取map集合对应的key值
     *
     * @param name
     * @return
     */
    public <T> T getMapCacheValue(String name, String key) {
        RMapCache<Object, T> mapCache = getMapCache(name);
        return mapCache.get(key);
    }

    /**
     * 先从map集合获取数据,如果没有则从接口获取
     *
     * @param name
     * @return
     */
    public <T> T getMapCacheValue(String name, String key, RealData<T> realData) {
        return getMapCacheValue(name, key, realData, redissonProperties.getDataValidTime());
    }

    /**
     * 先从map集合获取数据,如果没有则从接口获取
     *
     * @param name
     * @return
     */
    public <T> T getMapCacheValue(String name, String key, RealData<T> realData, Long time) {
        RMapCache<Object, T> mapCache = getMapCache(name);
        T o = mapCache.get(key);
        if (o == null) {
            o = realData.get();
            if (ObjectUtils.isEmpty(o)) {
                mapCache.remove(key);
            } else {
                setMapCacheValue(name, key, o, time);
            }
        }
        return o;
    }

    /**
     * 先从map集合获取数据,如果没有则从接口获取
     *
     * @param name
     * @return
     */
    public <T> T getMapCacheValue(String name, String key, RealData<T> realData, DataCache<T> dataCache, Long time) {
        RMapCache<Object, T> mapCache = getMapCache(name);
        T o = mapCache.get(key);
        if (o == null) {
            o = realData.get();
            if (ObjectUtils.isEmpty(o)) {
                mapCache.remove(key);
            } else {
                Boolean cache = dataCache.isCache(o);
                if (cache) {
                    setMapCacheValue(name, key, o, time);
                }
            }
        }
        return o;
    }

    /**
     * 设置map集合
     *
     * @param name
     * @param data
     * @param time 缓存时间,单位毫秒 -1永久缓存
     * @return
     */
    public <K, V> void setMapCacheValues(String name, Map<K, V> data, Long time) {
        RMapCache<K, V> map = redissonClient.getMapCache(name);

        if (time == null) {
            time = redissonProperties.getDataValidTime();
        }
        map.putAll(data, time, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置 map值
     *
     * @param name
     * @param time 缓存时间,单位毫秒 0永久缓存
     * @return
     */
    public void setMapCacheValue(String name, String key, Object value, Long time) {
        setMapCacheValue(name, key, value, time, 0L);
    }

    /**
     * 设置 map值
     *
     * @param name
     * @param time 缓存时间,单位毫秒 0永久缓存
     * @return
     */
    public <T> void setMapCacheValue(String name, String key, T value, Long time, Long maxIdleTime) {
        RMapCache<String, T> map = redissonClient.getMapCache(name);
        if (time == null) {
            time = redissonProperties.getDataValidTime();
        }
        map.put(key, value, time, TimeUnit.MILLISECONDS, maxIdleTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置map集合
     *
     * @param name
     * @param data
     * @return
     */
    public <K, V> void setMapCacheValues(String name, Map<K, V> data) {
        setMapCacheValues(name, data, redissonProperties.getDataValidTime());
    }

    /**
     * 设置map集合
     *
     * @param name
     * @return
     */
    public void setMapCacheValue(String name, String key, Object value) {
        setMapCacheValue(name, key, value, redissonProperties.getDataValidTime());
    }


    /**
     * 获取set集合
     *
     * @param name
     * @return
     */
    public <T> RSetCache<T> getSetCache(String name) {
        return redissonClient.getSetCache(name);
    }

    /**
     * 获取List集合 如果没有则通过实时数据获取
     *
     * @param name
     * @return
     */
    public <T> RSetCache<T> getSetCache(String name, RealDataSet<T> realDataSet, Long time) {
        RSetCache<Object> set = getSetCache(name);
        if (set == null || set.size() == 0) {
            Set<T> objects = realDataSet.get();
            setSetCacheValues(name, objects, time);
        }
        return getSetCache(name);
    }

    /**
     * 获取List集合对应的index值
     *
     * @param name
     * @return
     */
    public <T> RSetCache<T> getSetCache(String name, RealDataSet<T> realDataSet) {
        return getSetCache(name, realDataSet, redissonProperties.getDataValidTime());
    }

    /**
     * 设置set集合
     *
     * @param name
     * @param data
     * @param time 缓存时间,单位毫秒 -1永久缓存
     * @return
     */
    public <T> void setSetCacheValues(String name, Set<T> data, Long time) {
        RSetCache<Object> set = redissonClient.getSetCache(name);
        set.addAll(data);
        Long dataValidTime = redissonProperties.getDataValidTime();
        if (time == null) {
            set.expire(Duration.ofMillis(dataValidTime));
        } else if (time != -1) {
            set.expire(Duration.ofMillis(time));
        }
    }

    /**
     * 设置set集合
     *
     * @param name
     * @param data
     * @param time 缓存时间,单位毫秒 -1永久缓存
     * @return
     */
    public void setSetCacheValue(String name, Object data, Long time) {
        RSetCache<Object> set = redissonClient.getSetCache(name);
        if (time == null) {
            time = redissonProperties.getDataValidTime();
        }
        set.add(data, time, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置set集合
     *
     * @param name
     * @param data
     * @return
     */
    public <T> void setSetCacheValues(String name, Set<T> data) {
        setSetCacheValues(name, data, redissonProperties.getDataValidTime());
    }

    /**
     * 设置set集合
     *
     * @param name
     * @param data
     * @return
     */
    public void setSetValues(String name, Object data) {
        setSetCacheValue(name, data, redissonProperties.getDataValidTime());
    }

}