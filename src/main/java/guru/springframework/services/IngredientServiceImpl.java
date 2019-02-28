package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * Created by carlosmartinez on 2019-02-28 17:11
 */
@RequiredArgsConstructor
@Log4j2
@Service
public class IngredientServiceImpl implements IngredientService {
  private final IngredientToIngredientCommand ingredientToIngredientCommand;
  private final RecipeRepository recipeRepository;

  @Override
  public IngredientCommand findByRecipeIdAndIngredientId(final long recipeId,
      final long ingredientId) {
    final Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
    if (!recipeOptional.isPresent()) {
      log.error("Recipe not found for id {}", recipeId);
      return null;
    }
    final Optional<IngredientCommand> ingredientCommandOptional = recipeOptional.get()
        .getIngredients()
        .stream()
        .filter(i -> i.getId().equals(ingredientId))
        .map(i -> ingredientToIngredientCommand.convert(i))
        .findFirst();
    if (!ingredientCommandOptional.isPresent()) {
      log.error("Ingredient not found for id {}", ingredientId);
      return null;
    }
    return ingredientCommandOptional.get();
  }
}
