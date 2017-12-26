package com.gznb.redis.cluster.test.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangjun
 * @create 2017/9/14
 */
@Component
public class CheckRedisCusterTask {

    private static final Logger logger = LoggerFactory.getLogger(CheckRedisCusterTask.class);

    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);

    @Autowired
    private JedisCluster jedisCluster;

    @PostConstruct
    public void runCheck(){
        scheduledExecutorService.scheduleAtFixedRate((() -> {updateRedis();}),5,10, TimeUnit.SECONDS);
    }

    private void updateRedis() {
        try {
            String set = jedisCluster.set("test","test");
            logger.info("set result: {}",set);
            Long del = jedisCluster.del("test");
            logger.info("del result: {}",del);
        }catch (Exception e){
            logger.error("发生异常！异常信息：" + e);
        }
    }

}
