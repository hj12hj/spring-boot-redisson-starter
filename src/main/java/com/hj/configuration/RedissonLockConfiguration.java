package com.hj.configuration;

import com.hj.aop.RedissonLockAop;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @Author：hj
 * @Date：2023/1/15 18:58
 */
public class RedissonLockConfiguration {

    @Bean
    @ConditionalOnMissingBean(RedissonLockAop.class)
    public RedissonLockAop redissonLockAop() {
        return new RedissonLockAop();
    }

}
