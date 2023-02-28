package com.lynxauthenticationService.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;

    @NotEmpty
    private String username;
    @NotEmpty
    private String email;

    private LocalDateTime createDate;
}

