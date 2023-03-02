package com.lynx.publicApiProvider.controllers;

import com.lynx.publicApiProvider.dto.UserDTO;
import com.lynx.publicApiProvider.entity.User;
import com.lynx.publicApiProvider.facade.UserFacade;
import com.lynx.publicApiProvider.service.UserService;
import com.lynx.publicApiProvider.validators.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private UserFacade userFacade;
    private UserService userService;
    private ResponseErrorValidator responseErrorValidator;

    @Autowired
    public UserController(UserFacade userFacade, UserService userService, ResponseErrorValidator responseErrorValidator) {
        this.userFacade = userFacade;
        this.userService = userService;
        this.responseErrorValidator = responseErrorValidator;
    }

    @GetMapping("/")
    public ResponseEntity<UserDTO> getCurrentUser (Principal principal){
        User user = userService.getUserByPrincipal(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> listErrors = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listErrors)) return  listErrors;

        User user = userService.updateUser(userDTO, principal);
        UserDTO userUpdated = userFacade.userToUserDTO(user);

        return  new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }
}
