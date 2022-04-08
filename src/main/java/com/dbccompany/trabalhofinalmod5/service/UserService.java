package com.dbccompany.trabalhofinalmod5.service;

import com.dbccompany.trabalhofinalmod5.dto.UserDTO;
import com.dbccompany.trabalhofinalmod5.dto.UserUpdateDTO;
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

    public void saveUser(UserDTO user) throws UserAlreadyExistsException, IllegalAccessException {
        UserEntity userEntity = objectMapper.convertValue(user, UserEntity.class);

        if (!verifyAge(userEntity)) {
            throw new IllegalAccessException("User too young!");
        }
        userRepository.saveUser(userEntity);
    }

    public UserDTO findByUsername(String username) throws UserDontExistException {
        return objectMapper.convertValue(userRepository.findByUsername(username), UserDTO.class);
    }

    public void updateUser(String username, UserUpdateDTO user) throws  UserDontExistException {
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
