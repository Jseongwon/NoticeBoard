package com.noticeboard.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserCreateForm {
    @NotEmpty(message = "사용자 ID는 필수항목입니다.")
    private String name;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

    @NotEmpty(message = "비정상적 접근입니다.")
    private String salt;

    @Email
    @NotEmpty(message = "이메일은 필수항목입니다.")
    private String emailAddress;
}
