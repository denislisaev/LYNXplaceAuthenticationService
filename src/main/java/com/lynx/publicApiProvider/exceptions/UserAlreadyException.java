package com.lynx.publicApiProvider.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyException extends RuntimeException {
    private final boolean isEmailUsed;
    private final boolean isUsernameUsed;

    public UserAlreadyException(String s, boolean isEmailUsed, boolean isUsernameUsed) {
        super(s);
        this.isEmailUsed = isEmailUsed;
        this.isUsernameUsed = isUsernameUsed;
    }

    public boolean isEmailUsed() {
        return isEmailUsed;
    }

    public boolean isUsernameUsed() {
        return isUsernameUsed;
    }
}
