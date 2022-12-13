package com.springSecurity.jwt.repository;

import com.springSecurity.jwt.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);


}
