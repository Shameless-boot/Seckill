package com.shaun.seckill.valid;

import com.shaun.seckill.util.ValidatorUtil;
import com.shaun.seckill.valid.IsMobile;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author Shaun
 * @Date 2022/7/9 22:36
 * @Description: 手机号码格式校验器
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        System.out.println(234);
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println(value);
        if (required) {
            return ValidatorUtil.isMobile(value);
        } else {
            if (!StringUtils.hasLength(value))
                return true;
            else
                return ValidatorUtil.isMobile(value);
        }
    }
}
