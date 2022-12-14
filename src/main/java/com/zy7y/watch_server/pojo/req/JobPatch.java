package com.zy7y.watch_server.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "暂停/恢复")
public class JobPatch {
    @Schema(description = "任务名称")
    private String jobName;
    @Schema(description = "任务组名称")
    private String jobGroupName;
}
