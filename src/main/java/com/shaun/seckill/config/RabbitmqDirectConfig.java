package com.shaun.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Shaun
 * @Date 2022/7/14 14:17
 * @Description: rabbitmq配置类 - Direct模式
 */

// @Configuration
public class RabbitmqDirectConfig {
    public static final String QUEUE01 = "queue01_direct";
    public static final String QUEUE02 = "queue02_direct";
    public static final String EXCHANGE = "directExchange";
    public static final String ROUTING_KEY_01 = "queue.red";
    public static final String ROUTING_KEY_02 = "queue.green";

    /*@Bean
    public Queue queue01() {
        return new Queue(RabbitmqDirectConfig.QUEUE01);
    }

    @Bean
    public Queue queue02() {
        return new Queue(RabbitmqDirectConfig.QUEUE02);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitmqDirectConfig.EXCHANGE);
    }

    @Bean
    public Binding binding01() {
        return BindingBuilder.bind(queue01()).to(directExchange()).with(RabbitmqDirectConfig.ROUTING_KEY_01);
    }

    // 将queue2队列绑定到directExchange上去，并设置对应的routingKey
    @Bean
    public Binding binding02() {
        return BindingBuilder.bind(queue02()).to(directExchange()).with(RabbitmqDirectConfig.ROUTING_KEY_02);
    }*/
}
