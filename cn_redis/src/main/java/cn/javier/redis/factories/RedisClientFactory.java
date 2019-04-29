package cn.javier.redis.factories;

import cn.javier.redis.common.RedisConfig;
import redis.clients.jedis.Jedis;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2019/4/29 13:53
 * @Version 1.0
 */
public class RedisClientFactory {
    private static volatile Jedis jedis = null;
    private RedisClientFactory(){}
    public static Jedis getJedis(){
        if (null == jedis){
            synchronized (RedisClientFactory.class){
                if (null == jedis){
                    jedis = new Jedis(RedisConfig.redisHostName,RedisConfig.redisHostPort);

                }
            }
        }
        return jedis;
    }
}
