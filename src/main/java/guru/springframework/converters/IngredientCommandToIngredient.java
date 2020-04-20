package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/** Created by carlosmartinez on 2019-02-24 15:51 */
@RequiredArgsConstructor
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

  private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

  @Nullable
  @Override
  public Ingredient convert(final IngredientCommand source) {
    if (source == null) {
      return null;
    }

    final Ingredient ingredient = new Ingredient();
    ingredient.setId(source.getId());
    ingredient.setAmount(source.getAmount());
    ingredient.setDescription(source.getDescription());
    ingredient.setUnitOfMeasure(uomConverter.convert(source.getUnitOfMeasure()));
    return ingredient;
  }
}
