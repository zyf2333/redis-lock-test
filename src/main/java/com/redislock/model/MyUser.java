package com.redislock.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author zyf
 * @Description
 * @ClassName User
 * @Date 2020/3/23 18:31
 **/
@Data
public class MyUser implements Serializable {

    private String id;
    private String name;
    private Integer age;



}
