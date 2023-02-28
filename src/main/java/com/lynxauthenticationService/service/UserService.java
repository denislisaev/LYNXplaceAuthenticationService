package com.lynxauthenticationService.service;

import com.lynxauthenticationService.dto.UserDTO;
import com.lynxauthenticationService.entity.User;
import com.lynxauthenticationService.entity.enums.ERole;
import com.lynxauthenticationService.exceptions.UserAlreadyException;
import com.lynxauthenticationService.payload.request.SignUpRequest;
import com.lynxauthenticationService.repository.UserRepository;
import com.lynxauthenticationService.security.jwt.JWTProvider;
import com.lynxauthenticationService.service.interfaces.UserServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserService implements UserServiceInterface {
    public static final Logger LOG = LoggerFactory.getLogger(JWTProvider.class);


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User createUser(SignUpRequest userIn){
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setUsername(userIn.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userIn.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);
        user.setIsBlocked(false);

        try {
            LOG.info("Saving user {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception e) {
            LOG.error("Error registration {}", e.getMessage());
            boolean isEmailUsed = e.getCause().getCause().getMessage().contains(user.getEmail());
            boolean isUsernamelUsed = e.getCause().getCause().getMessage().contains(user.getUsername());

            throw new UserAlreadyException("The user already exist!", isEmailUsed, isUsernamelUsed);
        }
    }

    public User updateUser(UserDTO userDTO, Principal principal){
        LOG.debug("Update user {}", userDTO.getUsername());
        User user = getUserByPrincipal(principal);
//        user.setFirstname(userDTO.getFirstname());
//        user.setLastname(userDTO.getLastname());
//        user.setLocation(userDTO.getLocation());

        try {
            return userRepository.save(user);
        } catch (Exception e){
            LOG.error("User {} has not updated", user.getUsername());
        }
        return null;
    }


    public User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        LOG.debug("Find user {} by principal", username);
        try {
            return userRepository.findUserByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден" + username));
        } catch (UsernameNotFoundException e){
            LOG.error(e.getMessage());
        }
        return null;
    }

    public User getCurrentUser(Principal principal){
        try {
            LOG.debug("Get current user");
            return getUserByPrincipal(principal);
        } catch (Exception e){
            LOG.error("Error! Can`t get current user");
        }
        return null;
    }

    public User getUserById(Long userId){
        try {
            LOG.debug("Get user by id: " + userId);
            return userRepository.findUserById(userId).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        } catch (UsernameNotFoundException e){
            LOG.error(e.getMessage());
        }
        return null;
    }

    public void deleteUserByUsername(String username){
        LOG.info("Delete user: " + username);
        try {
            User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            userRepository.delete(user);
        } catch (UsernameNotFoundException e){
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error("Error! Can`t delete user: " + username);
        }
    }

    public List<User> getUsers(){
        try {
            return userRepository.findAll();
        } catch (Exception e){
            LOG.error("Can`t get all users");
        }
        return null;
    }
}
