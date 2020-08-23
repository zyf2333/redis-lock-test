package com.redislock;

import com.redislock.model.MyUser;
import com.redislock.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class MqTestApplicationTests {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;


    @Test
    void threadTest(){
        boolean k = redisUtil.setnx("k", "1", 5);
        System.out.println(k);
        boolean j = redisUtil.setnx("k", "1", 5);
        System.out.println(j);
    }


}
