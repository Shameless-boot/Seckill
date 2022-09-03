package com.shaun.seckill.valid;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author Shaun
 * @Date 2022/7/10 22:18
 * @Description: 字符串枚举校验器
 */
public class EnumStringValidator implements ConstraintValidator<EnumString, String> {
    private Set<String> stringSet = new HashSet<>();

    /**
     * 该方法用于做一些校验前的初始化工作，例如获取注解中的某些值。
     * @param constraintAnnotation 注解对象
     */
    @Override
    public void initialize(EnumString constraintAnnotation) {
        for (String str : constraintAnnotation.value())
            stringSet.add(str);
    }

    /**
     * 该方法用于指定校验规则
     * @param value 属性值
     * @param context
     * @return 是否通过校验
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        return stringSet.contains(value);
    }
}
