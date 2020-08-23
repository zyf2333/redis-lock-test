package com.redislock.core;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author zyf
 * @Description
 * @ClassName RedissonConfig
 * @Date 2020/3/23 18:09
 **/

@Configuration
public class RedissonConfig {
    @Value("${redisson.address}")
    private String addressUrl;
    @Value("${redisson.password}")
    private String password;

    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(addressUrl)
                //.setPassword(password)
                .setReconnectionTimeout(10000)
                .setRetryInterval(5000)
                .setTimeout(10000)
                .setConnectTimeout(10000);
        return Redisson.create(config);
    }
    /**
     * @return
     * @Author huangwb
     * @Description //TODO 主从模式
     * @Date  20203/19 10:54
     * @Param
     *
     **/
   /* @Bean
    public RedissonClient getRedisson() {
        RedissonClient redisson;
        Config config = new Config();
        config.useMasterSlaveServers()
                //可以用"rediss://"来启用SSL连接
                .setMasterAddress("redis://***(主服务器IP):6379").setPassword("web2017")
                .addSlaveAddress("redis://***（从服务器IP）:6379")
                .setReconnectionTimeout(10000)
                .setRetryInterval(5000)
                .setTimeout(10000)
                .setConnectTimeout(10000);//（连接超时，单位：毫秒 默认值：3000）;

        //  哨兵模式config.useSentinelServers().setMasterName("mymaster").setPassword("web2017").addSentinelAddress("***(哨兵IP):26379", "***(哨兵IP):26379", "***(哨兵IP):26380");
        redisson = Redisson.create(config);
        return redisson;
    }*/


}
