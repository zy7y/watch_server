package com.zy7y.watch_server.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "定时任务创建")
public class JobCreate {
    @Schema(description = "任务名称")
    private String jobName;
    @Schema(description = "任务组名称")
    private String jobGroupName;
    @Schema(description = "触发器名称")
    private String triggerName;
    @Schema(description = "触发器组名称")
    private String triggerGroupName;
    @Schema(description = "cron表达式")
    private String cron;
}
