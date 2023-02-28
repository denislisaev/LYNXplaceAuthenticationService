package com.lynxauthenticationService.payload.request;

import com.lynxauthenticationService.annotations.PasswordMatches;
import com.lynxauthenticationService.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignUpRequest {
    @Email(message = "Email имеет неверный формат")
    @NotBlank(message = "Поле Email обязательно")
    @ValidEmail
    private String email;

    @NotEmpty(message = "Пожалуйста, введите логин")
    private String username;

    @NotEmpty(message = "Пожалуйста, введите пароль")
    @Size(min = 5, max = 50)
    private String password;

    @NotEmpty(message = "Пожалуйста, введите пароль еще раз")
    @Size(min = 5, max = 50)
    private String confirmPassword;
}