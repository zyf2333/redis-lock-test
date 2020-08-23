package com.redislock.controller;

import com.redislock.model.GoodsOrder;
import com.redislock.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author zyf
 * @Description redis的setnx实现分布式锁
 * @ClassName GoodsController
 * @Date 2020/3/23 21:45
 **/
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/order")
    public Map<String, Object> order(@RequestBody GoodsOrder order) {
        Map<String, Object> result = new HashMap<>();
        order.setId(UUID.randomUUID().toString().replace("-", ""));
        String goodsId = order.getGoodsId();
        String lockKey = "flag_goods" + goodsId;
        boolean setnx = redisUtil.setnx(lockKey, order.getId(), 5);
        //获取分布式锁，setnx方式
        if (setnx) {
            try {
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
                //释放锁
                String key = (String) redisUtil.get(lockKey);
                if (key != null && key.equals(order.getId())) {
                    redisUtil.del(lockKey);
                }
            }
        } else {
            result.put("result", "下单失败,未获取锁");
            //重新获取锁
        }
        return result;
    }

    @PostMapping("/addGoodsNum/{goodsId}/{num}")
    public Map<String, Object> addGoodsNum(@PathVariable String goodsId,
                                           @PathVariable Integer num) {
        boolean flag = redisUtil.hset("num", "goods_" + goodsId, num);
        Map<String, Object> result = new HashMap<>();
        result.put("result", flag);
        return result;
    }
}
