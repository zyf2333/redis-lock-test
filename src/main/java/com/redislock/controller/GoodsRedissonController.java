package com.redislock.controller;

import com.redislock.model.GoodsOrder;
import com.redislock.utils.RedisUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author zyf
 * @Description redis的redisson实现分布式锁
 * @ClassName GoodsController
 * @Date 2020/3/23 21:45
 **/
@RestController
@RequestMapping("/goods")
public class GoodsRedissonController {


    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisUtil redisUtil;


    @PostMapping("/orderRedisson")
    public Map<String, Object> order(@RequestBody GoodsOrder order) {
        Map<String, Object> result = new HashMap<>();
        order.setId(UUID.randomUUID().toString().replace("-", ""));
        String goodsId = order.getGoodsId();
        String lockKey = "flag_goods" + goodsId;
        //获取锁RLock
        RLock lock = redissonClient.getLock(lockKey);
        try {
            //加锁,和ReentrantLock类似
            lock.lock();
            Object numO = redisUtil.hget("num", "goods_" + goodsId);
            //判断库存
            if (numO instanceof Integer && (Integer) numO > 0) {
                Integer num = (Integer) numO;
                num = num - order.getNum();//减库存
                if (num >= 0) {
                    redisUtil.hset("num", "goods_" + goodsId, num);
                    result.put("库存", num);
                    redisUtil.hset("order", order.getId(), order);
                    result.put("result", "下单成功");
                } else {
                    result.put("result", "下单失败,库存不足");
                }
            } else {
                result.put("result", "下单失败,已售罄");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //解锁
            lock.unlock();
        }
        return result;
    }

}
