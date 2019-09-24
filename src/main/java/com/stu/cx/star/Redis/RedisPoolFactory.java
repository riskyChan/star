package com.stu.cx.star.Redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: riskychan
 * @Description:to create jedis
 * @Date: Create in 10:07 2019/9/20
 */
@Service
public class RedisPoolFactory {
    @Bean
    public JedisPool JedisPoolFactory(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxWaitMillis(3000);
        jedisPoolConfig.setTestOnBorrow(false);
        JedisPool jp =  new JedisPool(jedisPoolConfig,"192.168.35.188",6379,3000,"ipanel",0);
        return jp;
    }
}
