package com.hj.postprocessor;

import com.hj.annotation.remoteservice.RedissonService;
import com.hj.remoteservice.RedissonRemoteService;
import org.redisson.api.RedissonClient;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

/**
 * @author: hj
 * @date: 2023/1/16
 * @time: 1:22 PM
 */
public class RemoteServicePostProcessor implements BeanPostProcessor {

    @Autowired
    private RedissonClient redissonClient;


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        // 扫描注册
        RedissonService annotation = aClass.getAnnotation(RedissonService.class);
        if (annotation != null) {
            RedissonRemoteService redissonRemoteService = (RedissonRemoteService) bean;
//            redissonClient.getRemoteService().register(annotation, redissonRemoteService);
        }
        return bean;
    }
}
