package com.hj.aop;

import com.hj.annotation.filter.BloomFilter;
import com.hj.properties.RedissonProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 布隆过滤器
 *
 * @author: hj
 * @date: 2023/1/16
 * @time: 11:24 AM
 */
@Aspect
public class BloomFilterAop {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedissonProperties redissonProperties;
    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(bloomFilter)")
    public void controllerAspect(BloomFilter bloomFilter) {
    }


    @Around("controllerAspect(bloomFilter)")
    public Object around(ProceedingJoinPoint joinPoint, BloomFilter bloomFilter) throws Throwable {
        logger.debug("布隆过滤器开始 ,name: {} , key: {}", bloomFilter.name(), bloomFilter.key());
        RBloomFilter<String> filter = redissonClient.getBloomFilter(bloomFilter.name());
        if (!filter.isExists()) {
            filter.tryInit(redissonProperties.getExpectedInsertions(), redissonProperties.getFalseProbability());
        }
        boolean contains = filter.contains(bloomFilter.key());
        logger.debug("布隆过滤器结束 ,name: {} , key: {} , contains: {}", bloomFilter.name(), bloomFilter.key(), contains);
        Object[] args = joinPoint.getArgs();
        args[args.length - 1] = contains;
        return joinPoint.proceed(args);
    }

}
