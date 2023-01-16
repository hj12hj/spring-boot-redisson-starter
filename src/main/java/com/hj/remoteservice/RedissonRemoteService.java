package com.hj.remoteservice;

import java.lang.reflect.InvocationTargetException;

/**
 * 标记接口
 *
 * @author: hj
 * @date: 2023/1/16
 * @time: 1:34 PM
 */
public interface RedissonRemoteService {
    void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
