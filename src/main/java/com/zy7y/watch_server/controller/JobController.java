package com.zy7y.watch_server.controller;

import com.zy7y.watch_server.job.TestJob;
import com.zy7y.watch_server.pojo.rep.R;
import com.zy7y.watch_server.pojo.req.JobCreate;
import com.zy7y.watch_server.pojo.req.JobPatch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name="Quartz定时任务")
@RequestMapping("/job")
@RestController
@Slf4j
public class JobController {

    @Autowired
    private Scheduler scheduler;

    @Operation(summary = "创建定时任务", description = "https://cron.qqe2.com/")
    @PostMapping("")
    public R create(@RequestBody JobCreate jobCreate) {
        try{
            JobDetail jobDetail = JobBuilder.newJob(TestJob.class).withIdentity(jobCreate.getJobName(), jobCreate.getJobGroupName()).storeDurably().build();
            if (scheduler.checkExists(jobDetail.getKey())){
                log.info("任务已存在，准备删除");
                scheduler.deleteJob(jobDetail.getKey());
            }

            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobCreate.getTriggerName(), jobCreate.getTriggerGroupName())
                    .startNow()
                    .usingJobData("stamp", System.currentTimeMillis())
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobCreate.getCron()))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return R.fail(400, "任务创建失败");
        }
        return R.success(jobCreate);
    }

    @Operation(summary = "暂停任务")
    @PatchMapping("/pause")
    public R pauseJob(@RequestBody JobPatch jobPatch) {
        JobKey jobKey = new JobKey(jobPatch.getJobName(), jobPatch.getJobGroupName());
        try {
            if (scheduler.checkExists(jobKey)) {
                scheduler.pauseJob(jobKey);       //存在对应的恢复方法scheduler.resumeJob(jobKey);
                return R.success(jobPatch);
            }
        } catch (SchedulerException e){
            e.printStackTrace();
        }
        return R.fail(4000, "任务不存在");
    }

    @Operation(summary = "恢复任务")
    @PatchMapping("/resume")
    public R resumeJob(@RequestBody JobPatch jobPatch) {
        JobKey jobKey = new JobKey(jobPatch.getJobName(), jobPatch.getJobGroupName());
        try {
            if (scheduler.checkExists(jobKey)) {
                scheduler.resumeJob(jobKey);       //存在对应的恢复方法scheduler.resumeJob(jobKey);
                return R.success(jobPatch);
            }
        } catch (SchedulerException e){
            e.printStackTrace();
        }
        return R.fail(4000, "任务不存在");
    }

    @Operation(summary = "删除任务")
    @DeleteMapping("")
    public R delJob(@RequestBody JobPatch jobPatch){
        JobKey jobKey = new JobKey(jobPatch.getJobName(), jobPatch.getJobGroupName());
        try {
            if (scheduler.checkExists(jobKey)) {
                scheduler.deleteJob(jobKey);       //存在对应的恢复方法scheduler.resumeJob(jobKey);
                return R.success(jobPatch);
            }
        } catch (SchedulerException e){
            e.printStackTrace();
        }
        return R.fail(4000, "任务不存在");
    }

    @Operation(summary = "update task")
    @PutMapping("")
    public R update(@RequestBody JobCreate jobCreate){
        try{
            TriggerKey triggerKey = new TriggerKey(jobCreate.getTriggerName(), jobCreate.getTriggerGroupName());
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobCreate.getCron()))
                    .build();
            scheduler.rescheduleJob(triggerKey, trigger);           //更新并立即执行任务
            return R.success(jobCreate);
        } catch (SchedulerException e){
            e.printStackTrace();
        }

        return R.fail(4000, "任务不存在");
    }

    @Operation(summary = "定时任务列表")
    @GetMapping("")
    public R getJobsByState() throws Exception {
        List<String> jobNames = new ArrayList<>();
        List<String> jobGroupNames = scheduler.getJobGroupNames();         //获取所有注册到调度器任务的group
        for (String groupName : jobGroupNames) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {        //获取每个group下的所有任务
                jobNames.add(jobKey.getName());
            }
        }
        return R.success(jobNames);
    }


}
