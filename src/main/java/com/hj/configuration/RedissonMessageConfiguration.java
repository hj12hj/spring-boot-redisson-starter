package com.hj.configuration;

import com.hj.aop.RedissonLockAop;
import com.hj.aop.RedissonSenderAop;
import com.hj.message.RedissonMessageListenerPostProcessor;
import com.hj.properties.RedissonProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @Author：hj
 * @Date：2023/1/15 18:58
 */
public class RedissonMessageConfiguration {
    @Bean
    @ConditionalOnMissingBean(RedissonMessageListenerPostProcessor.class)
    public RedissonMessageListenerPostProcessor RedissonMQListener() {
        return new RedissonMessageListenerPostProcessor();
    }

    @Bean
    @ConditionalOnMissingBean(RedissonSenderAop.class)
    public RedissonSenderAop redissonSenderAop() {
        return new RedissonSenderAop();
    }

}
