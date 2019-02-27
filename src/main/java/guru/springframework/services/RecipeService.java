package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import java.util.Set;

/**
 * Created by carlosmartinez on 2018-12-10 12:45
 */
public interface RecipeService {

  void deleteById(Long id);

  Set<Recipe> getRecipes();

  Recipe findById(Long id);

  RecipeCommand saveRecipeCommand(RecipeCommand command);

  RecipeCommand findCommandById(Long l);
}
