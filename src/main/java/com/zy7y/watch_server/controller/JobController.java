package com.zy7y.watch_server.controller;

import com.zy7y.watch_server.job.TestJob;
import com.zy7y.watch_server.pojo.rep.JobVO;
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
                    .usingJobData("name", jobCreate.getJobName())
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
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobCreate.getCron()))
                    .build();
            scheduler.rescheduleJob(triggerKey, trigger);
            return R.success(jobCreate);
        } catch (SchedulerException e){
            e.printStackTrace();
        }

        return R.fail(4000, "任务不存在");
    }

    @Operation(summary = "定时任务列表")
    @GetMapping("")
    public R<List<JobVO>> getAllJob() throws Exception {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        List<JobVO> jobVOList = new ArrayList<>();
        for(JobKey jobKey: scheduler.getJobKeys(matcher)){
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger: triggers) {
                JobVO jobVO = new JobVO();
                jobVO.setJobName(jobKey.getName());
                jobVO.setJobGroupName(jobKey.getGroup());
                // 触发器状态
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                jobVO.setStatus(triggerState.name());
                if (trigger instanceof  CronTrigger){
                    jobVO.setCron(((CronTrigger) trigger).getCronExpression());
                }
                String[] triggerInfos = String.valueOf(trigger.getKey()).split("\\.");
                jobVO.setTriggerName(triggerInfos[0]);
                jobVO.setTriggerGroupName(triggerInfos[0]);
                jobVO.setNextRunTime(trigger.getNextFireTime());
                jobVOList.add(jobVO);
            }
        }

        return R.success(jobVOList);
    }


}
