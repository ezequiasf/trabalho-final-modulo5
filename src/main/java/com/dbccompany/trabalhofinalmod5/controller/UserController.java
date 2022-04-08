package com.dbccompany.trabalhofinalmod5.controller;

import com.dbccompany.trabalhofinalmod5.dto.UserDTO;
import com.dbccompany.trabalhofinalmod5.exception.UserAlreadyExistsException;
import com.dbccompany.trabalhofinalmod5.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/saveUser")
    public void saveUser(@Valid @RequestBody UserDTO user) throws UserAlreadyExistsException {
        userService.saveUser(user);
    }

    @PutMapping("/updateRecipe")
    public void updateUser(@RequestParam("username") String username, @Valid @RequestBody UserDTO user) throws UserAlreadyExistsException {
            userService.updateUser(username,user);
    }
    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestParam("username") String username) {
        userService.deleteUser(username);
    }


    @GetMapping("/findByUserName")
    public UserDTO findByUsername(@RequestParam("username") String username) {
        return userService.findByUsername(username);
    }
}
