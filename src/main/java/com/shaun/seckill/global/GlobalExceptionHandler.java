package com.shaun.seckill.global;

import com.shaun.seckill.vo.Result;
import com.shaun.seckill.vo.ResultCode;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author Shaun
 * @Date 2022/7/9 22:53
 * @Description: 全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        Result result = Result.Error(ResultCode.MOBILE_ERROR);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        result.setMessage(message);
        return result;
    }

    @ExceptionHandler(GlobalException.class)
    public Result handleGlobalException(GlobalException e) {
        return Result.Error(e.getResultCode());
    }

    // @ExceptionHandler(Exception.class)
    // public Result handleException(Exception e) {
    //     return Result.Error(ResultCode.ERROR, e.getMessage());
    // }
}
