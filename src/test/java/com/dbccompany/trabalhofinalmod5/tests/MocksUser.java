package com.dbccompany.trabalhofinalmod5.tests;

import com.dbccompany.trabalhofinalmod5.dto.UserDTO;
import com.dbccompany.trabalhofinalmod5.dto.UserUpdateDTO;
import com.dbccompany.trabalhofinalmod5.entity.UserEntity;
import com.dbccompany.trabalhofinalmod5.exception.UserAlreadyExistsException;
import com.dbccompany.trabalhofinalmod5.exception.UserDontExistException;
import com.dbccompany.trabalhofinalmod5.repository.UserRepository;
import com.dbccompany.trabalhofinalmod5.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MocksUser {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void BeforeEach() {
        ReflectionTestUtils.setField(userService, "objectMapper", objectMapper);
    }

    //==> Save User
    @Test
    public void testarChamadaSave() throws UserAlreadyExistsException, IllegalAccessException {
        UserDTO user = criarUser();

        userService.saveUser(user);

        verify(userRepository, times(1)).saveUser(ArgumentMatchers.any());
    }

    //=== Find By Username
    @Test
    public void deveTestarSeRetornaUserEntityELancaExcecao() throws UserDontExistException {
        String username = criarUser().getUsername();

        UserEntity user = objectMapper.convertValue(criarUser(), UserEntity.class);

        when(userRepository.findByUsername(username)).thenReturn(user)
                .thenThrow(UserDontExistException.class);
    }

    //==> UserUpdate

    @Test
    public void deveTestarChamadaUpdate() throws UserAlreadyExistsException, IllegalAccessException, UserDontExistException {
        UserUpdateDTO user = new UserUpdateDTO();

        userService.updateUser("user", user);

        verify(userRepository, times(1)).updateUser(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    //==> DeleteUser

    @Test
    public void deveTestarChamadaDelete() throws UserAlreadyExistsException, IllegalAccessException, UserDontExistException {
        String username = criarUser().getUsername();

        userService.deleteUser(username);

        verify(userRepository, times(1)).deleteUser(username);
    }

    public UserDTO criarUser() {
        return UserDTO.builder()
                .username("exemplo")
                .age(21)
                .email("exemplo@gmail.com")
                .password("12345")
                .isactive(true).build();
    }

}
