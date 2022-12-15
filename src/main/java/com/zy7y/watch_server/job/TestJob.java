package com.zy7y.watch_server.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;


@Slf4j
public class TestJob implements Job {

    private long stamp;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    // 获取到job创建时的usingDataMap中的对应value
    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 和上面等价，拿到的数据时int类型
//        jobExecutionContext.getMergedJobDataMap().getInt("taskId");
        log.info("{}  {}执行了{}", stamp, name, new Date());
    }
}
