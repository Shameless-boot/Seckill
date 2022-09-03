package com.shaun.seckill.vo;

import com.shaun.seckill.valid.IsMobile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author Shaun
 * @Date 2022/7/9 21:55
 * @Description: 登录 VO对象
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {
    @NotNull
    @IsMobile
    private String mobile;

    @Length(min = 32)
    @NotNull
    private String password;
}
