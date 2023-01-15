package com.hj.annotation.message;



import com.hj.enums.MQModel;

import java.lang.annotation.*;

/**
 * 消息订阅注解
 *
 * @Author：hj
 * @Date：2023/1/15 18:38
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedissonMessageListener {
    /**
     * topic name
     * @return
     */
    String name();

    /**
     * 匹配模式 <br />
     * PRECISE精准的匹配 如:name="myTopic" 那么发送者的topic name也一定要等于myTopic  <br />
     * PATTERN模糊匹配 如: name="myTopic.*" 那么发送者的topic name 可以是 myTopic.name1 myTopic.name2.尾缀不限定
     * @return
     */
    MQModel model() default MQModel.PRECISE;
}
