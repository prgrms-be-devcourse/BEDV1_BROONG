package com.prgrms.broong.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeValidator.class)
public @interface TimeValid {

    String message() default "유효하지 않은 시간입니다";

    Class[] groups() default {};

    Class[] payload() default {};

}
