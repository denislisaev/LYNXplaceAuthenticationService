package com.lynx.publicApiProvider.facade;

import com.lynx.publicApiProvider.entity.User;
import org.springframework.stereotype.Component;
import com.lynx.publicApiProvider.dto.UserDTO;


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