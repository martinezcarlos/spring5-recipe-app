package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by carlosmartinez on 2019-02-24 16:04
 */
@RequiredArgsConstructor
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

  private final CategoryToCategoryCommand categoryConveter;
  private final IngredientToIngredientCommand ingredientConverter;
  private final NotesToNotesCommand notesConverter;

  @Synchronized
  @Nullable
  @Override
  public RecipeCommand convert(final Recipe source) {
    if (source == null) {
      return null;
    }

    final RecipeCommand command = new RecipeCommand();
    command.setId(source.getId());
    command.setCookTime(source.getCookTime());
    command.setPrepTime(source.getPrepTime());
    command.setDescription(source.getDescription());
    command.setDifficulty(source.getDifficulty());
    command.setDirections(source.getDirections());
    command.setServings(source.getServings());
    command.setSource(source.getSource());
    command.setUrl(source.getUrl());
    command.setNotes(notesConverter.convert(source.getNotes()));

    if (CollectionUtils.isNotEmpty(source.getCategories())) {
      source.getCategories()
          .forEach((Category category) -> command.getCategories()
              .add(categoryConveter.convert(category)));
    }

    if (CollectionUtils.isNotEmpty(source.getIngredients())) {
      source.getIngredients()
          .forEach(
              ingredient -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
    }

    return command;
  }
}
