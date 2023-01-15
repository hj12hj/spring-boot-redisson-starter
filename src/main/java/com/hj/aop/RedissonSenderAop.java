package com.hj.aop;

import com.hj.annotation.message.RedissonMessageSender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 消息发送者aop
 *
 * @Author：hj
 * @Date：2023/1/15 19:15
 */
@Aspect
public class RedissonSenderAop {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(redissonMessageSender)")
    public void aspect(RedissonMessageSender redissonMessageSender) {
    }

    @Around("aspect(redissonMessageSender)")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint, RedissonMessageSender redissonMessageSender) {
        try {
            Object obj = proceedingJoinPoint.proceed();
            RTopic topic = redissonClient.getTopic(redissonMessageSender.name());
            logger.debug("redissonMessageSender发送消息主题: {} ,消息 {}", redissonMessageSender.name(), obj);
            topic.publish(obj);
            return obj;
        } catch (Throwable e) {
            logger.error("redissonMessageSender发送消息异常", e);
            throw new RuntimeException(e);
        }

    }

}
