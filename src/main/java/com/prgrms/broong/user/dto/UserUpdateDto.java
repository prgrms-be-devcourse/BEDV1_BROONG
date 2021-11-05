package com.prgrms.broong.user.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    @NotNull(message = "포인트는 null일 수 없습니다")
    @Min(message = "point는 0이상이여야 합니다", value = 0)
    private int point;

}
