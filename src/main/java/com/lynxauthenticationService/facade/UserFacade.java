package com.lynxauthenticationService.facade;

import com.lynxauthenticationService.entity.User;
import com.lynxauthenticationService.dto.UserDTO;
import org.springframework.stereotype.Component;


@Component
public class UserFacade {
    public UserDTO userToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreateDate(user.getCreateDate());

        return userDTO;
    }
}