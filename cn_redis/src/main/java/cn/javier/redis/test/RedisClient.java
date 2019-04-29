package cn.javier.redis.test;

import cn.javier.redis.factories.RedisClientFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2019/4/28 14:39
 * @Version 1.0
 */
public class RedisClient {
    private static Jedis jedis = RedisClientFactory.getJedis();

    public static void testStr(){
        //添加數據
        jedis.set("name","lucy");
        System.out.println(jedis.get("name"));
        //拼接數據
        jedis.append("name"," pege");
        System.out.println(jedis.get("name"));
        //刪除某個key
        jedis.del("name");
        System.out.println(jedis.get("name"));
        //設置多個鍵值對
        jedis.mset("name","lucky","age","11","host","1.1.1.1");
        jedis.incr("age");
        System.out.println(jedis.get("name")+":"+jedis.get("age")+":"+jedis.get("host"));
    }

    /**
     * jedis.lpush在頭部插入
     * jedis.rpush在尾部插入
     * 如果键不存在，则在追加操作之前创建一个空列表
     * 如果键存在但不是列表，则返回错误。
     */
    public static void testList(){
        //開始前，先移除所有的內容
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework",0,-1));
        //先向key java framework中存入三條數據
        jedis.lpush("java framework","spring");
        jedis.lpush("java framework","mybatis");
        jedis.lpush("java framework","mvc");
        //再取出所有數據jedis.lrange是按範圍取出
        //第一個是key，第二個是起始位置，第三個是結束位置，jedis.llen獲取長度，-1表示獲取所有
        System.out.println(jedis.lrange("java framework",0,-1));

        jedis.del("java framework");
        jedis.rpush("java framework","spring");
        jedis.rpush("java framework","mybatis");
        jedis.rpush("java framework","mvc");
        System.out.println(jedis.lrange("java framework",0,-1));
    }

    public static void testSet(){
        //添加
        jedis.sadd("users","lucy");
        jedis.sadd("users","lily");
        jedis.sadd("users","tom");
        //添加多個值
        jedis.sadd("users","fun","fill","jackson");
        //獲取所有加入的value
        System.out.println(jedis.smembers("users"));
        //判斷是否是set的元素
        System.out.println(jedis.sismember("users","fun"));
        System.out.println(jedis.sismember("users","hill"));
        //从集合中返回一个随机元素，但不删除该元素。如果集合为空或键不存在，则返回null对象。
        System.out.println(jedis.srandmember("users"));
        //返回集合的元素的個數
        System.out.println(jedis.scard("users"));
    }
    public static void main(String[] args) {
//        testStr();
//        testList();
//        jedis.del("tableNameList");
//        for (int i = 0; i < 1000; i++){
//            jedis.rpush("tableNameList","tableName_"+i);
//        }
//        System.out.println(jedis.lrange("tableNameList",0,-1));
//        for (int i=0;i<100;i++){
//            RedisConsumer redisConsumer = new RedisConsumer("thread-"+i);
//            redisConsumer.start();
//        }
        testSet();

    }
}
