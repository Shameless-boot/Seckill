package com.shaun.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Shaun
 * @Date 2022/7/9 21:51
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;

    private String message;

    private Object obj;



    public static Result success() {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static Result success(Object obj) {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), obj);
    }

    // 由于失败可能存在多个问题，因此需要传进去状态码
    public static Result Error(ResultCode resultCode) {
        return new Result(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static Result Error(ResultCode resultCode, Object obj) {
        return new Result(resultCode.getCode(), resultCode.getMessage(), obj);
    }
}
