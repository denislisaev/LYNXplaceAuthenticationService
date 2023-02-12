package com.lynx.publicApiProvider.service.interfaces;

import com.lynx.publicApiProvider.dto.UserDTO;
import com.lynx.publicApiProvider.entity.User;
import com.lynx.publicApiProvider.payload.request.SignUpRequest;

import java.security.Principal;

public interface UserServiceInterface {
    public User createUser(SignUpRequest userIn);

    public User updateUser(UserDTO userDTO, Principal principal);

    public User getUserByPrincipal(Principal principal);

    public User getCurrentUser(Principal principal);

    public User getUserById(Long id);
}
