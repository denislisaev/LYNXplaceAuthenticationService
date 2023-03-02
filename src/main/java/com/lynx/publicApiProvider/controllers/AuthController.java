package com.lynx.publicApiProvider.controllers;

import com.lynx.publicApiProvider.exceptions.UserAlreadyException;
import com.lynx.publicApiProvider.payload.request.LoginRequest;
import com.lynx.publicApiProvider.payload.request.SignUpRequest;
import com.lynx.publicApiProvider.payload.response.JWTSuccessResponse;
import com.lynx.publicApiProvider.payload.response.MessageResponse;
import com.lynx.publicApiProvider.security.SecurityConstants;
import com.lynx.publicApiProvider.security.jwt.JWTProvider;
import com.lynx.publicApiProvider.service.UserService;
import com.lynx.publicApiProvider.validators.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ResponseErrorValidator responseErrorValidator;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult){
        ResponseEntity<Object> listErrors = responseErrorValidator.mappedValidatorService(bindingResult);

        if(!ObjectUtils.isEmpty(listErrors)) {
            return listErrors;
        }
        try{
            userService.createUser(signUpRequest);
        } catch (UserAlreadyException e){
            String response = "";
            if (e.isEmailUsed() ){
                response += "Email уже занят \n";
            }
            if (e.isUsernameUsed()){
                response += "Логин уже занят \n";
            }
            return new ResponseEntity<>(new MessageResponse(response), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(new MessageResponse("Регистрация успешно завершена"));
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){
        ResponseEntity<Object> listErrors = responseErrorValidator.mappedValidatorService(bindingResult);

        if(!ObjectUtils.isEmpty(listErrors)) {
            return listErrors;
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = SecurityConstants.TOKEN_PREFIX + jwtProvider.generateToken(authentication);

            return ResponseEntity.ok(new JWTSuccessResponse(true, jwt));
        } catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("Неверный логин или пароль!"), HttpStatus.BAD_REQUEST);
        }

    }
}
