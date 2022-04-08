package com.dbccompany.trabalhofinalmod5.tests;

import com.dbccompany.trabalhofinalmod5.dto.RecipeComplete;
import com.dbccompany.trabalhofinalmod5.dto.RecipeDTO;
import com.dbccompany.trabalhofinalmod5.exception.CaloriesLimitExceededException;
import com.dbccompany.trabalhofinalmod5.exception.PriceExpensiveException;
import com.dbccompany.trabalhofinalmod5.exception.RecipeNotFoundException;
import com.dbccompany.trabalhofinalmod5.exception.UserDontExistException;
import com.dbccompany.trabalhofinalmod5.repository.RecipeRepository;
import com.dbccompany.trabalhofinalmod5.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MocksRecipes {

    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void BeforeEach() {
        ReflectionTestUtils.setField(recipeService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarChamadaSaveRecipe ()  throws RecipeNotFoundException {
        RecipeDTO recipe = criarReceita();

        when(recipeService.findByRecipeName(recipe.getRecipeName()))
                .thenReturn(objectMapper.convertValue(recipe, RecipeComplete.class));
    }

    public RecipeDTO criarReceita() {
        return RecipeDTO.builder()
                .author("exemplo")
                .recipeName("exemplo receita")
                .calories(321.0)
                .ingredients(Arrays.asList("ovo", "leite"))
                .prepareRecipe("exemplooooooo")
                .price(12.32)
                .build();
    }


}
