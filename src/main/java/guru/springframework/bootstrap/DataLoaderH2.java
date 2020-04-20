package guru.springframework.bootstrap;

import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/** Created by carlosmartinez on 05/12/2018 18:21 */
@Component
@Log4j2
@RequiredArgsConstructor
@Profile("default")
public class DataLoaderH2 implements ApplicationListener<ContextRefreshedEvent> {

  private final RecipeRepository recipeRepository;
  private final CategoryRepository categoryRepository;
  private final UnitOfMeasureRepository unitOfMeasureRepository;

  @Override
  public void onApplicationEvent(final ContextRefreshedEvent event) {
    log.info("=== Bootstraping data ===");
    createImaginaryRecipe();
    log.info("=== Finished Bootstraping data ===");
  }

  private void createImaginaryRecipe() {
    log.info("=== Creating imaginary recipe ===");
    // Imaginary ingredients
    final Set<Ingredient> iIngredients = new HashSet<>();
    iIngredients.add(createIngredient("Salt", 2, "Teaspoon"));
    iIngredients.add(createIngredient("Pepper", 3, "Pinch"));
    iIngredients.add(createIngredient("Chicken breast", 2, "Unit"));
    // Imaginary notes
    final Notes iNotes = createNotes("This is an imaginary recipe. Cook it however you want.");
    // Imaginary recipe
    final Recipe iRecipe =
        createRecipe(
            "Imaginary Recipe",
            30,
            60,
            2,
            "imagination",
            "my.imagination.co",
            "Do this, then do that",
            null,
            iNotes,
            iIngredients,
            Difficulty.EASY,
            new HashSet<>(Arrays.asList("Mexican", "Fast Food")));
    recipeRepository.save(iRecipe);
    log.info("=== Finished creating imaginary recipe ===");
  }

  private Ingredient createIngredient(
      final String description, final double ammount, final String unitOfMeasure) {
    log.info("=== Creating igredient '{}' ===", description);
    final Ingredient ingredient = new Ingredient();
    ingredient.setDescription(description);
    ingredient.setAmount(BigDecimal.valueOf(ammount));
    ingredient.setUnitOfMeasure(
        unitOfMeasureRepository
            .findByDescription(unitOfMeasure)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "No Unit Of Measure found for the description " + unitOfMeasure)));
    return ingredient;
  }

  private Notes createNotes(final String recipeNotes) {
    log.info("=== Creating notes ===");
    final Notes notes = new Notes();
    notes.setRecipeNotes(recipeNotes);
    return notes;
  }

  private Recipe createRecipe(
      final String description,
      final Integer prepTime,
      final Integer cookTime,
      final Integer servings,
      final String source,
      final String url,
      final String directions,
      final Byte[] image,
      final Notes notes,
      final Set<Ingredient> ingredients,
      final Difficulty difficulty,
      final Set<String> categories) {
    final Recipe recipe = new Recipe();
    recipe.setDescription(description);
    recipe.setPrepTime(prepTime);
    recipe.setCookTime(cookTime);
    recipe.setServings(servings);
    recipe.setSource(source);
    recipe.setUrl(url);
    recipe.setDirections(directions);
    recipe.setImage(image);
    recipe.setNotes(notes);
    ingredients.forEach(recipe::addIngredient);
    recipe.setDifficulty(difficulty);
    categories.forEach(
        c ->
            recipe
                .getCategories()
                .add(
                    categoryRepository
                        .findByDescription(c)
                        .orElseThrow(() -> new IllegalArgumentException("Unknown category " + c))));
    return recipe;
  }
}
