package com.zy7y.watch_server.pojo.rep;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "返回结果实体类", description = "结果实体类")
public class R implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "返回码")
    private Integer code;

    @Schema(name = "返回消息")
    private String message;

    @Schema(name = "返回数据")
    private Object data;

    private R() {

    }

    public R(ResultCode resultCode, Object data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    private void setResultCode(ResultCode resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    // 返回成功
    public static R success() {
        R result = new R();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }
    // 返回成功
    public static R success(Object data) {
        R result = new R();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    // 返回失败
    public static R fail(Integer code, String message) {
        R result = new R();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    // 返回失败
    public static R fail(ResultCode resultCode) {
        R result = new R();
        result.setResultCode(resultCode);
        return result;
    }
}
