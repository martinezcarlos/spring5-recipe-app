package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by carlosmartinez on 2019-02-24 15:57
 */
@RequiredArgsConstructor
@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

  private final CategoryCommandToCategory categoryConveter;
  private final IngredientCommandToIngredient ingredientConverter;
  private final NotesCommandToNotes notesConverter;

  @Synchronized
  @Nullable
  @Override
  public Recipe convert(final RecipeCommand source) {
    if (source == null) {
      return null;
    }

    final Recipe recipe = new Recipe();
    recipe.setId(source.getId());
    recipe.setCookTime(source.getCookTime());
    recipe.setPrepTime(source.getPrepTime());
    recipe.setDescription(source.getDescription());
    recipe.setDifficulty(source.getDifficulty());
    recipe.setDirections(source.getDirections());
    recipe.setServings(source.getServings());
    recipe.setSource(source.getSource());
    recipe.setUrl(source.getUrl());
    recipe.setNotes(notesConverter.convert(source.getNotes()));
    recipe.setImage(source.getImage());

    if (CollectionUtils.isNotEmpty(source.getCategories())) {
      source.getCategories()
          .forEach(category -> recipe.getCategories().add(categoryConveter.convert(category)));
    }

    if (CollectionUtils.isNotEmpty(source.getIngredients())) {
      source.getIngredients()
          .forEach(
              ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
    }

    return recipe;
  }
}
