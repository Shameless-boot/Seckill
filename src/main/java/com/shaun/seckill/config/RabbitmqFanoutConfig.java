package com.shaun.seckill.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Shaun
 * @Date 2022/7/14 10:19
 * @Description:
 */

// @Configuration
public class RabbitmqFanoutConfig {
    /*public static final String QUEUE01 = "queue01_fanout";
    public static final String QUEUE02 = "queue02_fanout";
    public static final String EXCHANGE = "fanoutExchange";

    @Bean
    public Queue queue() {
        return new Queue("queue", true);
    }

    @Bean
    public Queue queue01() {
        return new Queue(RabbitmqConfig.QUEUE01);
    }

    @Bean
    public Queue queue02() {
        return new Queue(RabbitmqConfig.QUEUE02);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitmqConfig.EXCHANGE);
    }

    @Bean
    public Binding binding01() {
        //队列A绑定到FanoutExchange交换机
        return BindingBuilder.bind(queue01()).to(fanoutExchange());
    }

    @Bean
    public Binding binding02() {
        return BindingBuilder.bind(queue02()).to(fanoutExchange());
    }*/
}
