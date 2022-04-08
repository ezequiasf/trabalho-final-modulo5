package com.dbccompany.trabalhofinalmod5.testsUser;

import com.dbccompany.trabalhofinalmod5.dto.RecipeDTO;
import com.dbccompany.trabalhofinalmod5.entity.Classification;
import com.dbccompany.trabalhofinalmod5.entity.RecipeEntity;
import com.dbccompany.trabalhofinalmod5.entity.UserEntity;
import com.dbccompany.trabalhofinalmod5.exception.CaloriesLimitExceededException;
import com.dbccompany.trabalhofinalmod5.exception.PriceExpensiveException;
import com.dbccompany.trabalhofinalmod5.service.RecipeService;
import com.dbccompany.trabalhofinalmod5.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TC_UserEntity {
    @Autowired
    private UserService userService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private ObjectMapper objectMapper;

    //Lógica do user
    @Test
    void deveTestarSeUsuarioTemIdadeMaiorQueDezoitoAnos() {
        UserEntity u1 = UserEntity.builder()
                .username("zezão")
                .age(19)
                .email("zezao@outlook.com")
                .isactive(true)
                .build();
        boolean age = userService.verifyAge(u1);
        assertTrue(age);
    }

    @Test
    void deveTestarSeUsuarioTemIdadeMenorQueDezoitoAnos() {
        UserEntity u1 = UserEntity.builder()
                .username("flavia")
                .age(17)
                .email("flavinha@outlook.com")
                .isactive(true)
                .build();
        boolean age = userService.verifyAge(u1);
        assertFalse(age);
    }

    //Lógica do recipe
    @Test
    void deveTestarSeOPrecoEMuitoCaro (){
        RecipeEntity r1 = RecipeEntity.builder()
                .price(350.0).build();
       assertThrows(PriceExpensiveException.class,()-> recipeService
               .saveRecipe(objectMapper.convertValue(r1, RecipeDTO.class)));
    }

    @Test
    void deveTestarSeUltrapassaOLimiteDeCaloria (){
        RecipeEntity r1 = RecipeEntity.builder()
                .calories(3000.0)
                .price(43.54)
                .build();
        assertThrows(CaloriesLimitExceededException.class,()-> recipeService
                .saveRecipe(objectMapper.convertValue(r1, RecipeDTO.class)));
    }

    @Test
    void deveTestarSeNaoUltrapassaOLimiteDeCaloria (){
        RecipeEntity r1 = RecipeEntity.builder()
                .ingredients(Arrays.asList("ovo", "leite","carne"))
                .author("joao")
                .prepareTime(12)
                .price(32.12)
                .classifications(Arrays.asList(new Classification()))
                .calories(1499.0).build();
        assertDoesNotThrow(()-> recipeService
                .saveRecipe(objectMapper.convertValue(r1, RecipeDTO.class)));
    }

    @Test
    void deveTestarSeUsuarioNaoPodeCadastrarReceita (){
        RecipeEntity r1 = RecipeEntity.builder()
                .ingredients(Arrays.asList("ovo", "leite","carne"))
                .author("arlindo")
                .prepareTime(12)
                .price(32.12)
                .classifications(Arrays.asList(new Classification()))
                .calories(1499.0).build();
        RecipeDTO recipe = objectMapper.convertValue(r1, RecipeDTO.class);
        assertThrows(IllegalAccessException.class, ()-> recipeService.verifyIfUserIsActive(recipe));
    }

}
