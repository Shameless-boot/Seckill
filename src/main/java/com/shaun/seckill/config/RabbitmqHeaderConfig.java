package com.shaun.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Shaun
 * @Date 2022/7/14 14:56
 * @Description: rabbitmq配置类——header模式
 */
// @Configuration
public class RabbitmqHeaderConfig {
    public static final String QUEUE01 = "queue01_header";
    public static final String QUEUE02 = "queue02_header";
    public static final String EXCHANGE = "headerExchange";

    /*@Bean
    public Queue queue01() {
        return new Queue(RabbitmqHeaderConfig.QUEUE01);
    }

    @Bean
    public Queue queue02() {
        return new Queue(RabbitmqHeaderConfig.QUEUE02);
    }

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(RabbitmqHeaderConfig.EXCHANGE);
    }

    @Bean
    public Binding binding01() {
        Map<String, Object> map = new HashMap<>();
        map.put("color", "red");
        map.put("speed", "fast");
        return BindingBuilder.bind(queue01()).to(headersExchange()).whereAny(map).match();
    }

    @Bean
    public Binding binding02() {
        Map<String, Object> map = new HashMap<>();
        map.put("color", "red");
        map.put("speed", "slow");
        return BindingBuilder.bind(queue02()).to(headersExchange()).whereAll(map).match();
    }*/
}
