package com.dbccompany.trabalhofinalmod5.service;

import com.dbccompany.trabalhofinalmod5.dto.RecipeComplete;
import com.dbccompany.trabalhofinalmod5.dto.RecipeDTO;
import com.dbccompany.trabalhofinalmod5.dto.UserDTO;
import com.dbccompany.trabalhofinalmod5.entity.RecipeEntity;
import com.dbccompany.trabalhofinalmod5.exception.CaloriesLimitExceededException;
import com.dbccompany.trabalhofinalmod5.exception.PriceExpensiveException;
import com.dbccompany.trabalhofinalmod5.exception.RecipeNotFoundException;
import com.dbccompany.trabalhofinalmod5.exception.UserDontExistException;
import com.dbccompany.trabalhofinalmod5.repository.RecipeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final UserService userService;
    private final RecipeRepository recipeRepository;
    private final ObjectMapper objectMapper;

    public void saveRecipe(RecipeDTO recipe) throws PriceExpensiveException, CaloriesLimitExceededException, IllegalAccessException, UserDontExistException {
        if (recipe.getPrice() > 300) {
            throw new PriceExpensiveException("Price too much expensive!");
        }
        if (recipe.getCalories() > 1500) {
            throw new CaloriesLimitExceededException("Food too much fat!");
        }
        UserDTO user = userService.findByUsername(recipe.getAuthor());
        if (!user.getIsactive()) {
            throw new IllegalAccessException("User not active!");
        }
        recipeRepository.saveRecipe(objectMapper.convertValue(recipe, RecipeEntity.class));
    }

    public void updateRecipe(String recipeName, String author, RecipeDTO recipe) {
        recipeRepository.updateRecipe(recipeName, author, objectMapper.convertValue(recipe, RecipeEntity.class));
    }

    public void deleteRecipe(String recipeName, String author) {
        recipeRepository.deleteRecipe(recipeName, author);
    }

    public RecipeComplete findByRecipeName(String recipeName) throws RecipeNotFoundException {
        return objectMapper.convertValue(recipeRepository.findByRecipeName(recipeName), RecipeComplete.class);
    }


}
