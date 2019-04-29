package cn.javier.redis.test;

import com.javier.http.client.HttpClientDemo;
import redis.clients.jedis.Jedis;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2019/4/28 15:57
 * @Version 1.0
 */
public class RedisConsumer extends Thread {
    private Jedis jedis = new Jedis("127.0.0.1",6379);
    String threadId;
    public RedisConsumer(String threadId){
        this.threadId = threadId;
    }
    @Override
    public void run() {
        Long len = jedis.llen("tableNameList");
        while (len > 0){
            len = jedis.llen("tableNameList");
            String tableName = jedis.lpop("tableNameList");
            if (tableName == null || tableName.equals("")){
                break;
            }
            System.out.println(threadId + ":" +tableName+"---------"+len);
        }
        /*while (true){
            String tableName = HttpClientDemo.doGet("http://localhost:8080/app/get");
            if (tableName == null || tableName.equals("")){
                break;
            }
            System.out.println(threadId + ":" +tableName);
        }*/
    }

    @Override
    public synchronized void start() {
        super.start();
    }
}
