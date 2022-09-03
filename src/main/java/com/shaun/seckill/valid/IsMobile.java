package com.shaun.seckill.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author Shaun
 * @Date 2022/7/9 22:35
 * @Description:
 */

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMobileValidator.class}
)
public @interface IsMobile {

    String message() default "手机格式错误";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    boolean required() default true;
}
