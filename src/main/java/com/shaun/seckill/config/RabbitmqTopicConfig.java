package com.shaun.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Shaun
 * @Date 2022/7/14 14:39
 * @Description: rabbitmq配置类——topic模式
 */
@Configuration
public class RabbitmqTopicConfig {
    public static final String QUEUE01 = "queue01_topic";
    public static final String QUEUE02 = "queue02_topic";
    public static final String SECKILL_QUEUE = "seckillQueue";
    public static final String EXCHANGE = "topicExchange";
    public static final String ROUTING_KEY_01 = "*.queue.#";
    public static final String ROUTING_KEY_02 = "#.queue.#";

   /* @Bean
    public Queue queue01() {
        return new Queue(RabbitmqTopicConfig.QUEUE01);
    }

    @Bean
    public Queue queue02() {
        return new Queue(RabbitmqTopicConfig.QUEUE02);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitmqTopicConfig.EXCHANGE);
    }

    @Bean
    public Binding binding01() {
        return BindingBuilder.bind(queue01()).to(topicExchange()).with(RabbitmqTopicConfig.ROUTING_KEY_01);
    }

    @Bean
    public Binding binding02() {
        return BindingBuilder.bind(queue02()).to(topicExchange()).with(RabbitmqTopicConfig.ROUTING_KEY_02);
    }*/

    @Bean
    public Queue queue() {
        return new Queue(SECKILL_QUEUE);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("seckill.#");
    }
}
