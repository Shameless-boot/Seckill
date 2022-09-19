package com.shaun.seckill.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Shaun
 * @Date 2022/9/14 23:19
 * @Description: 通用限流接口
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    int seconds(); // 过期时间

    int maxCount(); // 最大访问次数

    boolean needLogin() default true;
}
