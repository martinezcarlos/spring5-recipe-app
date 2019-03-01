package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.transaction.Transactional;
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
  private final IngredientToIngredientCommand entityToCommandConverter;
  private final IngredientCommandToIngredient commandToEntityConverter;
  private final RecipeRepository recipeRepository;
  private final UnitOfMeasureRepository unitOfMeasureRepository;

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
        .map(i -> entityToCommandConverter.convert(i))
        .findFirst();
    if (!ingredientCommandOptional.isPresent()) {
      log.error("Ingredient not found for id {}", ingredientId);
      return null;
    }
    return ingredientCommandOptional.get();
  }

  @Override
  @Transactional
  public IngredientCommand saveIngredientCommand(final IngredientCommand command) {
    if (command == null) {
      throw new IllegalArgumentException("saving command cannot be null");
    }
    final Long recipeId = command.getRecipeId();
    final Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
    if (!recipeOptional.isPresent()) {
      throw new NoSuchElementException("Recipe not found for id " + recipeId);
    }
    final Recipe recipe = recipeOptional.get();
    final Optional<Ingredient> ingredientOptional = recipe.getIngredients()
        .stream()
        .filter(i -> i.getId().equals(command.getId()))
        .findFirst();
    if (ingredientOptional.isPresent()) {
      final Ingredient found = ingredientOptional.get();
      found.setDescription(command.getDescription());
      found.setAmount(command.getAmount());
      final Long uomId = command.getUnitOfMeasure().getId();
      found.setUnitOfMeasure(unitOfMeasureRepository.findById(uomId)
          .orElseThrow(
              () -> new NoSuchElementException("Unit of measure not fount for id " + uomId)));
    } else {
      recipe.addIngredient(commandToEntityConverter.convert(command));
    }

    final Recipe savedRecipe = recipeRepository.save(recipe);
    return entityToCommandConverter.convert(savedRecipe.getIngredients()
        .stream()
        .filter(i -> i.getId().equals(command.getId()))
        .findFirst()
        .get());
  }
}
