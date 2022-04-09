package com.dbccompany.trabalhofinalmod5.service;

import com.dbccompany.trabalhofinalmod5.dto.RecipeComplete;
import com.dbccompany.trabalhofinalmod5.dto.RecipeDTO;
import com.dbccompany.trabalhofinalmod5.dto.RecipeUpdateDTO;
import com.dbccompany.trabalhofinalmod5.dto.UserDTO;
import com.dbccompany.trabalhofinalmod5.entity.RecipeEntity;
import com.dbccompany.trabalhofinalmod5.exception.*;
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

    public String saveRecipe(RecipeDTO recipe) throws PriceExpensiveException, CaloriesLimitExceededException, IllegalAccessException, UserDontExistException {
        if (recipe.getPrice() > 300) {
            throw new PriceExpensiveException("Price too much expensive!");
        }
        if (recipe.getCalories() > 1500) {
            throw new CaloriesLimitExceededException("Food too much fat!");
        }
        UserDTO user = userService.findByUsername(recipe.getAuthor());
        if (user == null) {
            throw new UserDontExistException("User don't exists!");
        }
        if (!user.getIsactive()) {
            throw new IllegalAccessException("User not active!");
        }
        return recipeRepository.saveRecipe(objectMapper.convertValue(recipe, RecipeEntity.class));
    }

    public void updateRecipe(String hexId, RecipeUpdateDTO recipe) throws RecipeNotFoundException {
        RecipeEntity recipeEntity = findById(hexId);
        RecipeEntity recipePersist = objectMapper.convertValue(recipe, RecipeEntity.class);
        recipePersist.setAuthor(recipeEntity.getAuthor());
        recipeRepository.updateRecipe(hexId, recipePersist);
    }

    public void deleteRecipe(String hexId) {
        recipeRepository.deleteRecipe(hexId);
    }

    public RecipeComplete findByRecipeName(String recipeName) throws RecipeNotFoundException {
        return objectMapper.convertValue(recipeRepository.findByRecipeName(recipeName), RecipeComplete.class);
    }

    public RecipeEntity findById(String hexId) throws RecipeNotFoundException {
        RecipeEntity recipeEntity = recipeRepository.findById(hexId);
        if (recipeEntity == null) {
            throw new RecipeNotFoundException("Recipe not found!");
        }
        return recipeEntity;
    }


}
