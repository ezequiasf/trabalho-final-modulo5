package com.dbccompany.trabalhofinalmod5.service;

import com.dbccompany.trabalhofinalmod5.dto.RecipeDTO;
import com.dbccompany.trabalhofinalmod5.entity.RecipeEntity;
import com.dbccompany.trabalhofinalmod5.repository.RecipeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final ObjectMapper objectMapper;

    public void saveRecipe(RecipeDTO recipe) {
        recipeRepository.saveRecipe(objectMapper.convertValue(recipe, RecipeEntity.class));
    }

    public void updateRecipe(String recipeName, String author, RecipeDTO recipe) {
        recipeRepository.updateRecipe(recipeName, author, objectMapper.convertValue(recipe, RecipeEntity.class));
    }

    public void deleteRecipe(String recipeName, String author) {
        recipeRepository.deleteRecipe(recipeName, author);
    }

    public RecipeEntity findByRecipeName(String recipeName) {
        return recipeRepository.findByRecipeName(recipeName);
    }
}
