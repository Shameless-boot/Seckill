package com.shaun.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author Shaun
 * @Date 2022/7/9 21:45
 * @Description:
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode {
    // 公共模块
    SUCCESS(200, "成功"),
    ERROR(500, "失败"),
    // 登录模块
    LOGIN_ERROR(50001, "登录失败"),
    MOBILE_ERROR(50002, "手机号码格式不正确"),
    NOT_LOGIN(50003, "请先登录"),
    // 秒杀模块
    EMPTY_STOCK(50101, "库存数量不足"),
    REPEAT_PURCHASE(50102, "不能重复购买秒杀商品"),
    SECKILL_FAIL(50103, "秒杀失败"),
    SECKILL_PATH_ERROR(50104, "秒杀地址错误"),

    // 订单模板
    ORDER_IS_NOT_EXIST(50201, "订单不存在"),
    // 验证码模块
    ERROR_CAPTCHA(50301, "验证码错误"),
    // 限流模块
    ACCESS_LIMIT_ERROR(50401, "请求太过于频繁，请稍后重试")
    ;

    private Integer code;

    private String message;
}
