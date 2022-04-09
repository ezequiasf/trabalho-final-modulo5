package com.dbccompany.trabalhofinalmod5.tests;

import com.dbccompany.trabalhofinalmod5.dto.RecipeDTO;
import com.dbccompany.trabalhofinalmod5.dto.UserDTO;
import com.dbccompany.trabalhofinalmod5.entity.RecipeEntity;
import com.dbccompany.trabalhofinalmod5.exception.CaloriesLimitExceededException;
import com.dbccompany.trabalhofinalmod5.exception.PriceExpensiveException;
import com.dbccompany.trabalhofinalmod5.exception.UserAlreadyExistsException;
import com.dbccompany.trabalhofinalmod5.exception.UserDontExistException;
import com.dbccompany.trabalhofinalmod5.repository.RecipeRepository;
import com.dbccompany.trabalhofinalmod5.service.RecipeService;
import com.dbccompany.trabalhofinalmod5.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipesServiceTest {

//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private RecipeService recipeService;
//
//    @Mock
//    private RecipeRepository recipeRepository;
//
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Before
//    public void BeforeEach() {
//        ReflectionTestUtils.setField(recipeService, "objectMapper", objectMapper);
//    }
//
//    //===> MOCK
//    @Test
//    public void deveTestarSeLancaExcecaoParaUsuarioInativo() throws UserDontExistException, UserAlreadyExistsException {
//        //Construção de objetos
//        RecipeDTO recipeDTO = RecipeDTO.builder()
//                .recipeName("nome aleatorio")
//                .author("exemplo")
//                .prepareRecipe("bla bla")
//                .calories(1250.0)
//                .price(32.12)
//                .ingredients(Arrays.asList("ovo", "leite"))
//                .prepareTime(32).build();
//        UserDTO user = UserDTO.builder().username("exemplo")
//                .age(32)
//                .isactive(false)
//                .email("exemplo@gmail.com")
//                .password("12345")
//                .build();
//        RecipeEntity recipeEntity = objectMapper.convertValue(recipeDTO, RecipeEntity.class);
//
//        //Mock
//        when(userService.findByUsername("exemplo")).thenReturn(user);
//        verifyNoInteractions(recipeRepository);
//
//        //Validação
//        IllegalAccessException exc = assertThrows(IllegalAccessException.class,
//                () -> recipeService.saveRecipe(recipeDTO));
//        assertEquals("User not active!", exc.getMessage());
//    }
//
//    @Test
//    public void deveTestarSeLancaExcecaoParaLimiteDeCalorias() {
//        //Construção de objetos
//        RecipeDTO recipeDTO = RecipeDTO.builder()
//                .recipeName("nome aleatorio")
//                .calories(1600.0)
//                .price(32.12)
//                .ingredients(Arrays.asList("ovo", "leite"))
//                .prepareTime(32).build();
//
//        CaloriesLimitExceededException exc = assertThrows(CaloriesLimitExceededException.class,
//                () -> recipeService.saveRecipe(recipeDTO));
//        assertEquals("Food too much fat!", exc.getMessage());
//    }
//
//    @Test
//    public void deveTestarSeLancaExcecaoParaPrecoLimite() {
//        //Construção de objetos
//        RecipeDTO recipeDTO = RecipeDTO.builder()
//                .recipeName("nome aleatorio")
//                .price(355.0).build();
//
//        PriceExpensiveException exc = assertThrows(PriceExpensiveException.class,
//                () -> recipeService.saveRecipe(recipeDTO));
//        assertEquals("Price too much expensive!", exc.getMessage());
//    }


}
