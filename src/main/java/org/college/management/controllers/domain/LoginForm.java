package org.college.management.controllers.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class LoginForm {
    @NotBlank(message = "userId can't empty!")
    String userId;
    @NotBlank(message = "password can't empty!")
    String password;
}
