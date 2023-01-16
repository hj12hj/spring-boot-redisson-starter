package com.hj.annotation.remoteservice;

import java.lang.annotation.*;

/**
 * 远程服务 调用接口
 *
 * @author: hj
 * @date: 2023/1/16
 * @time: 1:16 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RedissonServiceCaller {
}
