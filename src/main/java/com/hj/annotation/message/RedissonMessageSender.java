package com.hj.annotation.message;

import java.lang.annotation.*;

/**
 * 发送消息注解
 *
 * @Author：hj
 * @Date：2023/1/15 18:38
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedissonMessageSender {
    /**
     * topic name
     *
     * @return
     */
    String name();
}
