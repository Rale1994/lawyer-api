package com.laywerapi.laywerapi.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordResetRequestDTO {
    private String email;
    private String newPassword;
    private String confirmPassword;

}
