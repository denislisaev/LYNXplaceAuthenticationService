package com.lynx.publicApiProvider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lynx.publicApiProvider.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional <User> findUserByEmail(String email);
    Optional <User> findUserByUsername(String username);
    Optional <User> findUserById(Long id);
}
