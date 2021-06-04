package com.loper.mine.common.annotions;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author liaonanzhou
 * @date 2021/6/4 10:34
 * @description
 **/
@Constraint(validatedBy = PasswordValidation.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "密码必须包含大小写英文字符、数字、特殊字符";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
