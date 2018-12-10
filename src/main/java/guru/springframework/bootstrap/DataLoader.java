package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by carlosmartinez on 05/12/2018 18:21
 */
@Component
@AllArgsConstructor
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

  private final RecipeRepository recipeRepository;
  private final IngredientRepository ingredientRepository;
  private final CategoryRepository categoryRepository;
  private final UnitOfMeasureRepository unitOfMeasureRepository;

  //@Override
  //public void run(final String... args) throws Exception {
  //  createImaginaryRecipe();
  //}

  @Override
  public void onApplicationEvent(final ContextRefreshedEvent event) {
    createImaginaryRecipe();
  }

  private void createImaginaryRecipe() {
    // Imaginary ingredients
    final Set<Ingredient> iIngredients = new HashSet<>();
    iIngredients.add(createIngredient("Salt", 2, "Teaspoon"));
    iIngredients.add(createIngredient("Pepper", 3, "Pinch"));
    iIngredients.add(createIngredient("Chicken breast", 2, "Unit"));
    // Imaginary categories
    final Set<Category> iCategories = new HashSet<>();
    categoryRepository.findAllByDescription(Arrays.asList("Mexican", "Fast Food"))
        .forEach(c -> iCategories.add(c));
    // Imaginary notes
    final Notes iNotes = createNotes("This is an imaginary recipe. Cook it however you want.");
    // Imaginary recipe
    final Recipe iRecipe = createRecipe("Imaginary Recipe", 30, 60, 2, "imagination",
        "my.imagination.co", "Do this, then do that", null, iNotes, iIngredients, Difficulty.EASY,
        iCategories);
    //recipeRepository.save(iRecipe);
    // Update ingredients
    iIngredients.forEach(i -> {
      i.setRecipe(iRecipe);
      //ingredientRepository.save(i);
    });
    // Update categories
    iCategories.forEach(c -> {
      c.getRecipes().addAll(Collections.singleton(iRecipe));
      //categoryRepository.save(c);
    });
    // Update notes
    iNotes.setRecipe(iRecipe);
    //TODO: update notes
    recipeRepository.save(iRecipe);
  }

  private Ingredient createIngredient(final String description, final double ammount,
      final String unitOfMeasure) {
    final Ingredient ingredient = new Ingredient();
    ingredient.setDescription(description);
    ingredient.setAmount(BigDecimal.valueOf(ammount));
    ingredient.setUnitOfMeasure(
        unitOfMeasureRepository.findByDescription(unitOfMeasure).orElseThrow(() -> {
          return new IllegalArgumentException(
              "No Unit Of Measure found for the description " + unitOfMeasure);
        }));
    return ingredient;
  }

  private Notes createNotes(final String recipeNotes) {
    final Notes notes = new Notes();
    notes.setRecipeNotes(recipeNotes);
    return notes;
  }

  private Recipe createRecipe(final String description, final Integer prepTime,
      final Integer cookTime, final Integer servings, final String source, final String url,
      final String directions, final Byte[] image, final Notes notes,
      final Set<Ingredient> ingredients, final Difficulty difficulty,
      final Set<Category> categories) {
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
    recipe.setIngredients(ingredients);
    recipe.setDifficulty(difficulty);
    recipe.setCategories(categories);
    return recipe;
  }
}
