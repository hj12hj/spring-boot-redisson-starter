package com.hj.annotation.filter;

import com.hj.configuration.RedissonLockConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * bloom filter
 *
 * @Author：hj
 * @Date：2023/1/15 18:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface BloomFilter {
    String name();

    String key() default "";


}
