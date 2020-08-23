package com.redislock.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author zyf
 * @Description
 * @ClassName GoodsOrder
 * @Date 2020/3/23 21:47
 **/
@Data
public class GoodsOrder implements Serializable {
    private String id;

    private String goodsId;

    private Integer num;
}
