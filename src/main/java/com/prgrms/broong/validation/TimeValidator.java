package com.prgrms.broong.validation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class TimeValidator implements ConstraintValidator<TimeValid, LocalDateTime> {

    private static final int HOUR_END = 24;
    private static final int MIN_END = 60;
    private static final int USER_ABLE = 30;

    @Override
    public void initialize(TimeValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime time, ConstraintValidatorContext context) {
        LocalDateTime now = LocalDateTime.now();

        int hour = LocalDateTime.now().getHour();

        int roundMinute = (int) Math.round(now.getMinute() / 10.0) * 10;

        if (roundMinute >= MIN_END) {
            hour += 1;
            roundMinute = 0;
            if (hour >= HOUR_END) {
                hour = 0;
            }
        }

        LocalDateTime roundNow = LocalDate.now().atTime(hour, roundMinute);

        if (ChronoUnit.DAYS.between(roundNow, time) == 0
            && ChronoUnit.MINUTES.between(roundNow, time) < USER_ABLE) {
            return false;
        }
        return true;
    }

}


