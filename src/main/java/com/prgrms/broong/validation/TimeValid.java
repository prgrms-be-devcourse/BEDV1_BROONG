package com.prgrms.broong.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Target(ElementType.FIELD) // 1
@Retention(RetentionPolicy.RUNTIME) // 2
@Constraint(validatedBy = TimeValidator.class) // 3
public @interface TimeValid {

    String message() default "유효하지 않은 시간입니다"; // 4

    Class[] groups() default {};

    Class[] payload() default {};

}
