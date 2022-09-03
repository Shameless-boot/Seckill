package com.shaun.seckill.vo;

import com.shaun.seckill.valid.EnumString;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @Author Shaun
 * @Date 2022/7/10 21:53
 * @Description: 校验注解练习的实体
 */

@Data
public class ValidVO {
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "手机号码不能为空")
    private String mobile;

    @NotNull(message = "性别不能为空")
    @EnumString(value = {"男", "女"}, message = "性别只能是男或者女")
    private String sex;

    @NotNull(message = "年龄不能为空")
    @Min(value = 1, message = "不能小于1岁")
    private Integer age;

    @Email(message = "邮箱格式有误")
    @NotBlank(message = "邮箱不能为空")
    private String email;
}
