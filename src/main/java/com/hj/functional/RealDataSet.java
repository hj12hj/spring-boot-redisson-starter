package com.hj.functional;

import java.util.Set;

/**
 * @author: hj
 * @date: 2023/1/16
 * @time: 10:40 AM
 */
@FunctionalInterface
public interface RealDataSet<T> {
    Set<T> get();
}
