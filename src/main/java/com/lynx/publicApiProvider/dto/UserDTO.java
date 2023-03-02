package com.lynx.publicApiProvider.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO {
    private Long id;

    @NotEmpty
    private String username;
    @NotEmpty
    private String email;

    private LocalDateTime createDate;

    private String ozonToken;
    private String wildberriesToken;
}

