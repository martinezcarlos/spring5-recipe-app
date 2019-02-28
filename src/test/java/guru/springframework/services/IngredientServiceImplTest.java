package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by carlosmartinez on 2019-02-28 17:26
 */
public class IngredientServiceImplTest {

  private IngredientToIngredientCommand ingredientToIngredientCommand;

  @Mock
  private RecipeRepository recipeRepository;

  private IngredientService ingredientService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    ingredientToIngredientCommand = new IngredientToIngredientCommand(
        new UnitOfMeasureToUnitOfMeasureCommand());
    ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, recipeRepository);
  }

  @Test
  public void findByRecipeIdAndIngredientId() {
    //given
    final Recipe recipe = new Recipe();
    recipe.setId(1L);

    final Ingredient ingredient1 = new Ingredient();
    ingredient1.setId(1L);

    final Ingredient ingredient2 = new Ingredient();
    ingredient2.setId(2L);

    final Ingredient ingredient3 = new Ingredient();
    ingredient3.setId(3L);

    recipe.addIngredient(ingredient1);
    recipe.addIngredient(ingredient2);
    recipe.addIngredient(ingredient3);

    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

    //then
    final IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L,
        3L);

    //when
    assertEquals(Long.valueOf(3L), ingredientCommand.getId());
    assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
    verify(recipeRepository, times(1)).findById(anyLong());
  }
}
