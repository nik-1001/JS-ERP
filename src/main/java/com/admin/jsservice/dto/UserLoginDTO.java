package com.admin.jsservice.dto;

import com.admin.jsservice.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginDTO {

    private String email;
    @ValidPassword
    private String password;
}
