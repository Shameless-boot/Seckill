package com.shaun.seckill.global;

import com.shaun.seckill.vo.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Shaun
 * @Date 2022/7/9 22:53
 * @Description:
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GlobalException extends RuntimeException{
    private ResultCode resultCode;
}
