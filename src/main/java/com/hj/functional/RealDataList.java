package com.hj.functional;

import java.util.List;

/**
 * @author: hj
 * @date: 2023/1/16
 * @time: 10:40 AM
 */
@FunctionalInterface
public interface RealDataList<T> {
    List<T> get();
}
