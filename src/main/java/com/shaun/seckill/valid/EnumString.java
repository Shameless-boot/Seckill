package com.shaun.seckill.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * @Author Shaun
 * @Date 2022/7/10 22:16
 * @Description:
 */

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {EnumStringValidator.class })
@Documented
public @interface EnumString {
    String message() default "填写数据不符合要求";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String[] value() default {};
}
