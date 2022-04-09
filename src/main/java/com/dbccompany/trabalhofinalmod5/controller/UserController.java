package com.dbccompany.trabalhofinalmod5.controller;

import com.dbccompany.trabalhofinalmod5.dto.UserDTO;
import com.dbccompany.trabalhofinalmod5.dto.UserUpdateDTO;
import com.dbccompany.trabalhofinalmod5.exception.UserAlreadyExistsException;
import com.dbccompany.trabalhofinalmod5.exception.UserDontExistException;
import com.dbccompany.trabalhofinalmod5.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Salva um usuário no banco de dados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "O usuário foi registrado com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @PostMapping("/saveUser")
    public void saveUser(@Valid @RequestBody UserDTO user) throws UserAlreadyExistsException, IllegalAccessException {
        userService.saveUser(user);
    }

    @ApiOperation(value = "Atualiza um usuário no banco de dados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "O usuário foi atualizado com sucesso no banco."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @PutMapping("/updateUser")
    public void updateUser(@RequestParam("username") String username, @Valid @RequestBody UserUpdateDTO user) throws UserDontExistException {
        userService.updateUser(username, user);
    }

    @ApiOperation(value = "Deleta um usuário do banco de dados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "O usuário foi deletado com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestParam("username") String username) {
        userService.deleteUser(username);
    }


    @ApiOperation(value = "Retorna um usuário através de seu username.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "O usuário foi listado com sucesso."),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema."),})
    @GetMapping("/findByUserName")
    public UserDTO findByUsername(@RequestParam("username") String username) {
        return userService.findByUsername(username);
    }


}
