package com.hj.annotation.remoteservice;

import java.lang.annotation.*;

/**
 * 远程服务 注册
 *
 * @author: hj
 * @date: 2023/1/16
 * @time: 1:16 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface RedissonService {

}
