package com.hj.functional;

import java.util.Map;

/**
 * @author: hj
 * @date: 2023/1/16
 * @time: 10:40 AM
 */
@FunctionalInterface
public interface RealDataMap<K,V> {
    Map<K,V> get();
}
