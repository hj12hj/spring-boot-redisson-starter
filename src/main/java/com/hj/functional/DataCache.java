package com.hj.functional;

/**
 * @author: hj
 * @date: 2023/1/16
 * @time: 10:38 AM
 */
@FunctionalInterface
public interface DataCache<T> {
    /**
     * 是否缓存数据
     */
    Boolean isCache(T t);
}
