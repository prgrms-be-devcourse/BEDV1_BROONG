package com.prgrms.broong.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "email은 필수 입니다.")
    @Pattern(regexp = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", message = "유효한 email 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 null일 수 없습니다.")
    @Size(message = "비밀번호는 두자리 이상이어야 합니다.", min = 2)
    private String password;

    @NotBlank(message = "이름은 null일 수 없습니다.")
    private String name;

    @NotBlank(message = "위치정보는 null일 수 없습니다.")
    private String locationName;

    @NotNull(message = "면허정보는 null일 수 없습니다.")
    private Boolean licenseInfo;

    @NotNull(message = "결제수단은 null일 수 없습니다.")
    private Boolean paymentMethod;

}
