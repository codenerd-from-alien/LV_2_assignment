package com.sparta.hanghaeblog.repository;

import com.sparta.hanghaeblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { //왜 Long일까
    Optional<User> findByUsername(String username);

//    Optional<User> findByEmail(String email);
}
