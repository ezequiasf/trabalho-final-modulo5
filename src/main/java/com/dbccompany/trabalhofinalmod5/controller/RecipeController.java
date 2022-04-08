package com.dbccompany.trabalhofinalmod5.controller;


import com.dbccompany.trabalhofinalmod5.dto.RecipeDTO;
import com.dbccompany.trabalhofinalmod5.entity.RecipeEntity;
import com.dbccompany.trabalhofinalmod5.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;


    @PostMapping("/saveRecipe")
    public void saveRecipe(@Valid @RequestBody RecipeDTO recipe) {
        recipeService.saveRecipe(recipe);
    }

    @PutMapping("/updateRecipe")
    public void updateRecipe(@RequestParam("recipeName") String recipeName,
                             @RequestParam("author") String author,
                             @Valid @RequestBody RecipeDTO recipe) {
        recipeService.updateRecipe(recipeName, author, recipe);
    }

    @DeleteMapping("/deleteRecipe")
    public void deleteRecipe(@RequestParam("recipeName") String recipeName,
                             @RequestParam("author") String author) {
        recipeService.deleteRecipe(recipeName,author);
    }

    @GetMapping("/findBryRecipeName")
    public RecipeEntity findByRecipeName(@RequestParam("recipeName") String recipeName) {
      return recipeService.findByRecipeName(recipeName);
    }
}
