package com.dbccompany.trabalhofinalmod5.dto;

import com.dbccompany.trabalhofinalmod5.entity.Classification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeComplete extends RecipeDTO {
    private List<Classification> classifications;
}
