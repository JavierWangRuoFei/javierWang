package cn.javier.redis.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Author zhicao wangruofei@yscredit.com
 * @Date 2019/4/28 20:14
 * @Version 1.0
 */
public class SpringDataTest {
    public static void main(String[] args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
        StringRedisTemplate redisTemplate =  (StringRedisTemplate) ac.getBean("stringRedisTemplate");
        redisTemplate.opsForValue().set("abc","bbbb");
        redisTemplate.opsForList().leftPush("tableNameList","tableName1");
        System.out.println(redisTemplate.opsForValue().get("abc"));
        System.out.println(redisTemplate.opsForList().range("tableNameList",0,-1));
        System.out.println(redisTemplate.opsForList().size("tableNameList"));
    }
}
