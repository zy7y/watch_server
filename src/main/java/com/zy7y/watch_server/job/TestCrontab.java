package com.zy7y.watch_server.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class TestCrontab {

    @Scheduled(cron = "0 1 * * * *")
    public void demo(){
        log.info("springboot 注解定时任务 crontab");
    }
}
