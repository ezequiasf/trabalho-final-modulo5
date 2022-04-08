package com.dbccompany.trabalhofinalmod5.service;

import com.dbccompany.trabalhofinalmod5.dto.UserDTO;
import com.dbccompany.trabalhofinalmod5.entity.UserEntity;
import com.dbccompany.trabalhofinalmod5.exception.UserAlreadyExistsException;
import com.dbccompany.trabalhofinalmod5.exception.UserDontExistException;
import com.dbccompany.trabalhofinalmod5.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public void saveUser(UserDTO user) throws UserAlreadyExistsException {
        userRepository.saveUser(objectMapper.convertValue(user, UserEntity.class));
    }

    public UserDTO findByUsername(String username) throws UserDontExistException {
        return objectMapper.convertValue(userRepository.findByUsername(username), UserDTO.class);
    }

    public void updateUser(String username, UserDTO user) throws UserAlreadyExistsException {
        userRepository.updateUser(username, objectMapper.convertValue(user, UserEntity.class));
    }

    public void deleteUser(String username) {
        userRepository.deleteUser(username);
    }

    //Retorna true se for maior do que 18 anos
    public boolean verifyAge(UserEntity user) {
        return user.getAge() >= 18;
    }

}
