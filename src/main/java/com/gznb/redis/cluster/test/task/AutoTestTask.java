package com.gznb.redis.cluster.test.task;

import com.gznb.redis.cluster.client.RedisClusterTemplate;
import com.gznb.redis.cluster.test.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangjun
 * @create 2017/9/18
 */
@Component
public class AutoTestTask {

    private static final Logger logger = LoggerFactory.getLogger(AutoTestTask.class);

    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

    @Autowired
    private RedisClusterTemplate redisClusterTemplate;

    @PostConstruct
    public void runCheck() {
        scheduledExecutorService.scheduleAtFixedRate((() -> {
            runTest();
        }), 5, 30, TimeUnit.SECONDS);
    }


    private void runTest() {
        try {
            // 测试string
            logger.info("*****************************test set string begin************************************************************");
            Long rows = redisClusterTemplate.setData("str", "this is string!", String.class, -1);
            logger.info("set string data result:{}", rows);
            String str = redisClusterTemplate.getData("str", String.class);
            logger.info("get string data result:{}", str);
            rows = redisClusterTemplate.delete("str");
            logger.info("delete string data result:{}", rows);
            logger.info("*****************************test set string end************************************************************");
            Thread.sleep(3000);
            logger.info("*****************************test set bean begin************************************************************");
            User user = new User("Jack", "666", 20);
            rows = redisClusterTemplate.setData("bean", user, User.class, -1);
            logger.info("set bean result:{}", rows);
            User redisUser = redisClusterTemplate.getData("bean", User.class);
            logger.info("get user bean:{}", redisUser.toString());
            rows = redisClusterTemplate.delete("bean");
            logger.info("delete bean result:{}", rows);
            logger.info("*****************************test set bean end************************************************************");
            Thread.sleep(3000);
            logger.info("*****************************test set single hash data begin************************************************************");
            rows = redisClusterTemplate.setHashData("hash1", "Jack", user, User.class);
            logger.info("set single hash data result:{}", rows);
            User hashUser = redisClusterTemplate.getHashData("hash1", "Jack", User.class);
            logger.info("get single hash data result:{}", hashUser.toString());
            Set<String> set = new HashSet<>();
            set.add("Jack");
            rows = redisClusterTemplate.removeHashData("hash1", set);
            logger.info("delete single hash data result:{}", rows);
            logger.info("*****************************test set single hash data end************************************************************");
            Thread.sleep(3000);
            logger.info("*****************************test set hash data begin************************************************************");
            User user2 = new User("Tom", "111", 18);
            User user3 = new User("Mouse", "222", 8);
            Map<String, User> map = new HashMap();
            map.put("Jack", user);
            map.put("Tom", user2);
            map.put("Mouse", user3);
            rows = redisClusterTemplate.setHashData("hash2", map, User.class, -1);
            logger.info("set hash data result:{}", rows);
            User hashData = redisClusterTemplate.getHashData("hash2", "Tom", User.class);
            logger.info("get hash data by key result:{}", hashData.toString());
            Map<String, User> resultData = redisClusterTemplate.getHashData("hash2", User.class);
            logger.info("get hash all data result:{}", resultData.toString());
            rows = redisClusterTemplate.delete("hash2");
            logger.info("delete hash data result:{}", rows);
            logger.info("*****************************test set hash data end************************************************************");
            Thread.sleep(3000);
            logger.info("*****************************test set list data begin************************************************************");
            List<User> list = new ArrayList();
            list.add(user);
            list.add(user2);
            list.add(user3);
            rows = redisClusterTemplate.setListData("list", list, User.class, -1);
            logger.info("set list data result:{}",rows);
            List<User> redisListData = redisClusterTemplate.getListData("list", User.class);
            logger.info("get list data result:{}",redisListData);
            rows = redisClusterTemplate.delete("list");
            logger.info("delete list data result:{}",rows);
            logger.info("*****************************test set list data end************************************************************");
            Thread.sleep(3000);
            logger.info("*****************************test rpush list begin************************************************************");
            rows = redisClusterTemplate.rpush2List("rpush",User.class,user);
            logger.info("rpush list result:{}",rows);
            User rpush = redisClusterTemplate.rpopFromList("rpush", User.class);
            logger.info("rpop result:{}",rpush);
            logger.info("*****************************test rpush list end************************************************************");
            Thread.sleep(3000);
            logger.info("*****************************test lpush list begin************************************************************");
            rows = redisClusterTemplate.lpush2List("lpush",User.class,user2);
            logger.info("lpush result:{}",rows);
            User lpush = redisClusterTemplate.lpopFromList("lpush", User.class);
            logger.info("lpop result:{}",lpush);
            logger.info("*****************************test lpush list end************************************************************");
            Thread.sleep(3000);
            logger.info("*****************************test set begin************************************************************");
            HashSet<String> stringSet = new HashSet<>();
            stringSet.add("1");
            stringSet.add("2");
            stringSet.add("3");
            rows = redisClusterTemplate.setCollectionData("set",stringSet);
            logger.info("test set result:{}",rows);
            Set<String> redisSet = redisClusterTemplate.getCollectionData("set");
            logger.info("test get set result:{}",redisSet);
            Boolean ismember = redisClusterTemplate.isMember("set", "1");
            logger.info("test isMember result:{}",ismember);
            ismember = redisClusterTemplate.isMember("set", "2313");
            logger.info("test isMember result:{}",ismember);
            rows = redisClusterTemplate.removeFromCollection("set", stringSet);
            logger.info("test remove set result:{}",rows);
            logger.info("*****************************test set end************************************************************");
            Thread.sleep(3000);
            logger.info("*****************************test incr begin************************************************************");
            Long incr = redisClusterTemplate.incr("incr");
            logger.info("test incr 1th result:{}",incr);
            incr = redisClusterTemplate.incr("incr");
            logger.info("test incr 2th result:{}",incr);
            rows = redisClusterTemplate.delete("incr");
            logger.info("delete incr result:{}",rows);
            logger.info("*****************************test incr end************************************************************");
            Thread.sleep(3000);
            logger.info("*****************************test incrBy begin************************************************************");
            Long incrBy = redisClusterTemplate.incrBy("incrBy",10);
            logger.info("test incr 1th result:{}",incrBy);
            incrBy = redisClusterTemplate.incrBy("incrBy",5);
            logger.info("test incr 2th result:{}",incrBy);
            rows = redisClusterTemplate.delete("incrBy");
            logger.info("delete incrBy result:{}",rows);
            logger.info("*****************************test incrBy end************************************************************");
        } catch (Exception e) {
            logger.error("发生异常！异常说明：{}，异常信息：{}", e.getMessage(), e.getCause());
        }
    }
}
