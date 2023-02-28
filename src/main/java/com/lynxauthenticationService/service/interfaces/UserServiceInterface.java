package com.lynxauthenticationService.service.interfaces;

import com.lynxauthenticationService.dto.UserDTO;
import com.lynxauthenticationService.entity.User;
import com.lynxauthenticationService.payload.request.SignUpRequest;

import java.security.Principal;

public interface UserServiceInterface {
    public User createUser(SignUpRequest userIn);

    public User updateUser(UserDTO userDTO, Principal principal);

    public User getUserByPrincipal(Principal principal);

    public User getCurrentUser(Principal principal);

    public User getUserById(Long id);
}
