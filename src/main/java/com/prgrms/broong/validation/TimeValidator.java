package com.prgrms.broong.validation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class TimeValidator implements ConstraintValidator<TimeValid, LocalDateTime> {

    @Override
    public void initialize(TimeValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime time, ConstraintValidatorContext context) {
        LocalDateTime now = LocalDateTime.now();

        int hour = LocalDateTime.now().getHour();

        int roundMinute = (int) Math.round(now.getMinute() / 10.0) * 10;

        if (roundMinute >= 60) {
            hour += 1;
            roundMinute = 0;
            if (hour >= 24) {
                hour = 0;
            }
        }

        LocalDateTime roundNow = LocalDate.now().atTime(hour, roundMinute);

        if (ChronoUnit.DAYS.between(roundNow, time) == 0
            && ChronoUnit.MINUTES.between(roundNow, time) < 30) {
            return false;
        }
        return true;
    }

}


