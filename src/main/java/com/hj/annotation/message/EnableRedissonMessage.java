package com.hj.annotation.message;

import com.hj.configuration.RedissonMessageConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 *
 * @Author：hj
 * @Date：2023/1/15 18:38
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.TYPE })
@Documented
@Import(RedissonMessageConfiguration.class)
public @interface EnableRedissonMessage {

}
