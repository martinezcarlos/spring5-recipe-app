package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

/**
 * Created by carlosmartinez on 2019-02-28 17:09
 */
public interface IngredientService {
  IngredientCommand findByRecipeIdAndIngredientId(long recipeId, long ingredientId);

  IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

  void deleteById(Long recipeId, Long ingredientId);
}
