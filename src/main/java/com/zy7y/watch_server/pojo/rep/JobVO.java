package com.zy7y.watch_server.pojo.rep;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class JobVO {
    @Schema(description = "任务名称")
    private String jobName;

    @Schema(description = "任务分组名称")
    private String jobGroupName;


    @Schema(description = "触发器名称")
    private String triggerName;
    @Schema(description = "触发器组名称")
    private String triggerGroupName;


    @Schema(description = "cron表达式")
    private String cron;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "下次运行时间")
    private Date nextRunTime;
}
