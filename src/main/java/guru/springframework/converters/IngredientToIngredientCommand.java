package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by carlosmartinez on 2019-02-24 15:53
 */
@RequiredArgsConstructor
@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

  private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

  @Synchronized
  @Nullable
  @Override
  public IngredientCommand convert(final Ingredient ingredient) {
    if (ingredient == null) {
      return null;
    }
    final IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setId(ingredient.getId());
    if (ingredient.getRecipe() != null) {
      ingredientCommand.setRecipeId(ingredient.getRecipe().getId());
    }
    ingredientCommand.setAmount(ingredient.getAmount());
    ingredientCommand.setDescription(ingredient.getDescription());
    ingredientCommand.setUnitOfMeasure(uomConverter.convert(ingredient.getUnitOfMeasure()));
    return ingredientCommand;
  }
}
