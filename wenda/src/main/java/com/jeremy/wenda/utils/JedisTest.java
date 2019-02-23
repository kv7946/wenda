package com.jeremy.wenda.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {
    public static void main (String[] args){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMinIdle(0);
        config.getMaxWaitMillis();
        JedisPool pool = new JedisPool(config,"192.168.25.128",
                6379,2000,"222222",10);
        Jedis jedis = pool.getResource();
        jedis.lpush("Jeremy","Twenty six");
    }
}
