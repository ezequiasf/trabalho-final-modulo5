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

    public String saveUser(UserDTO user) throws UserAlreadyExistsException, IllegalAccessException {
        UserEntity userEntity = objectMapper.convertValue(user, UserEntity.class);

        if (!verifyAge(userEntity)) {
            throw new IllegalAccessException("User too young!");
        }
        if (findByUsername(userEntity.getUsername()) != null) {
            throw new UserAlreadyExistsException("User already exists!");
        }
        return userRepository.saveUser(userEntity);
    }

    public String updateUser(String id, UserUpdateDTO user) throws UserDontExistException {
        UserEntity userEntity = findById(id);
        UserEntity userPersist = objectMapper.convertValue(user, UserEntity.class);
        userPersist.setUsername(userEntity.getUsername());
        return userRepository.updateUser(id, userPersist);
    }

    public UserDTO findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            return objectMapper.convertValue(user, UserDTO.class);
        }
        return null;
    }

    public UserEntity findById(String hexId) throws UserDontExistException {
        UserEntity userEntity = userRepository.findById(hexId);
        if (userEntity == null) {
            throw new UserDontExistException("User dont exist!");
        }
        return userEntity;
    }

    public void deleteUser(String hexId) {
        userRepository.deleteUser(hexId);
    }

    //Retorna true se for maior do que 18 anos
    public boolean verifyAge(UserEntity user) {
        return user.getAge() >= 18;
    }
}
