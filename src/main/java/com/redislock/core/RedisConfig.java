package com.redislock.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author zyf
 * @Description
 * @ClassName RedisConfig
 * @Date 2020/3/23 18:22
 **/
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object,Object>redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        //关联
        redisTemplate.setConnectionFactory(factory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer();
        Jackson2JsonRedisSerializer jacksonSerializer = new Jackson2JsonRedisSerializer(Object.class);

        //设置key的序列化器
        redisTemplate.setKeySerializer(stringRedisSerializer);
//        redisTemplate.setKeySerializer(jdkSerializer);
        //设置value的序列化器
//        redisTemplate.setValueSerializer(jdkSerializer);
        redisTemplate.setValueSerializer(jacksonSerializer);

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jacksonSerializer);

        return redisTemplate;
    }



}
