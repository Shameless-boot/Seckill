package com.shaun.seckill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author Shaun
 * @Date 2022/7/10 16:40
 * @Description: Redis配置类
 */

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplateInit(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // key 序列器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // value 序列器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // hash key 序列器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // hash value 序列器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
