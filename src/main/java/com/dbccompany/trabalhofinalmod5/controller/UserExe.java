package com.dbccompany.trabalhofinalmod5.controller;

import com.dbccompany.trabalhofinalmod5.entity.UserEntity;
import com.dbccompany.trabalhofinalmod5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userExe")
public class UserExe {
    private final UserRepository userRepository;

    @GetMapping
    public UserEntity find(String username) {
        return userRepository.findByUsername(username);
    }
}
