package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by carlosmartinez on 2019-02-28 17:26
 */
public class IngredientServiceImplTest {

  @Rule
  public final ExpectedException expectedEx = ExpectedException.none();

  private final IngredientToIngredientCommand entityToCommandConverter
      = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
  private final IngredientCommandToIngredient commandToEntityConverter
      = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
  @Mock
  private RecipeRepository recipeRepository;
  @Mock
  private UnitOfMeasureRepository unitOfMeasureRepository;

  private IngredientService ingredientService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    ingredientService = new IngredientServiceImpl(entityToCommandConverter,
        commandToEntityConverter, recipeRepository, unitOfMeasureRepository);
  }

  @Test
  public void recipeNorFound() {
    // Given
    when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
    // When
    final IngredientCommand command = ingredientService.findByRecipeIdAndIngredientId(1L, 1L);
    // Then
    assertNull(command);
  }

  @Test
  public void ingredientNotFound() {
    // Given
    final Recipe recipe = new Recipe();
    recipe.setId(1L);
    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
    // When
    final IngredientCommand command = ingredientService.findByRecipeIdAndIngredientId(1L, 1L);
    // Then
    assertNull(command);
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

    //when
    final IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L,
        3L);

    //then
    assertEquals(Long.valueOf(3L), ingredientCommand.getId());
    assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
    verify(recipeRepository, times(1)).findById(anyLong());
  }

  @Test(expected = IllegalArgumentException.class)
  public void exceptionSavingNullIngredientCommand() {
    // Given
    // When
    ingredientService.saveIngredientCommand(null);
    // Then
  }

  @Test(expected = NoSuchElementException.class)
  public void exceptionNoRecipe() {
    // Given
    final IngredientCommand command = new IngredientCommand();
    command.setId(null);
    command.setRecipeId(null);
    command.setDescription("Dummy desc");
    // When
    ingredientService.saveIngredientCommand(command);
    // Then
  }

  @Test
  public void saveIngredientCommand() {
    // Given
    final IngredientCommand command = new IngredientCommand();
    command.setRecipeId(1L);

    final Recipe savedRecipe = new Recipe();
    savedRecipe.addIngredient(new Ingredient());
    savedRecipe.getIngredients().iterator().next().setId(2L);

    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(new Recipe()));
    when(recipeRepository.save(any())).thenReturn(savedRecipe);
    // When
    final IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
    // Then
    assertEquals(Long.valueOf(2L), savedCommand.getId());
    verify(recipeRepository, times(1)).findById(anyLong());
    verify(recipeRepository, times(1)).save(any(Recipe.class));
  }

  @Test
  public void updateIngredientCommand() {
    // Given
    final UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
    uomCommand.setId(1L);
    uomCommand.setDescription("Kilogram");

    final IngredientCommand command = new IngredientCommand();
    command.setId(2L);
    command.setRecipeId(1L);
    command.setDescription("My new desc");
    command.setAmount(BigDecimal.TEN);
    command.setUnitOfMeasure(uomCommand);

    final Recipe originalRecipe = new Recipe();
    originalRecipe.addIngredient(new Ingredient());
    originalRecipe.getIngredients().iterator().next().setId(2L);

    final Recipe savedRecipe = new Recipe();
    savedRecipe.addIngredient(commandToEntityConverter.convert(command));
    savedRecipe.getIngredients().iterator().next().setId(2L);

    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(originalRecipe));
    when(unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.of(new UnitOfMeasure()));

    // When
    when(recipeRepository.save(any())).thenReturn(savedRecipe);
    final IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
    // Then
    assertEquals(Long.valueOf(2L), savedCommand.getId());
    assertEquals("My new desc", savedCommand.getDescription());
    assertEquals("Kilogram", savedCommand.getUnitOfMeasure().getDescription());
    assertEquals(BigDecimal.TEN, savedCommand.getAmount());
    verify(recipeRepository, times(1)).findById(anyLong());
    verify(recipeRepository, times(1)).save(any(Recipe.class));
  }

  @Test(expected = NoSuchElementException.class)
  public void unitOfMeasureNotFound() {
    // Given
    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(new Recipe()));
    when(unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.empty());
    // When
    ingredientService.saveIngredientCommand(new IngredientCommand());
    // Then
  }

  @Test
  public void nullIdsToDelete() {
    // Given
    expectedEx.expect(IllegalArgumentException.class);
    expectedEx.expectMessage("Ids cannot be null");
    // When
    ingredientService.deleteById(null, null);
    // Then
    verifyZeroInteractions(recipeRepository);
  }

  @Test
  public void ingredientRecipeToDeleteNotFound() {
    // Given
    expectedEx.expect(NoSuchElementException.class);
    expectedEx.expectMessage(startsWith("No recipe found for id"));
    when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
    // When
    ingredientService.deleteById(1L, 1L);
    // Then
    verify(recipeRepository, times(1)).findById(anyLong());
  }

  @Test
  public void ingredientToDeleteNotFound() {
    // Given
    expectedEx.expect(NoSuchElementException.class);
    expectedEx.expectMessage(startsWith("No ingredient found for id"));

    //final Ingredient ingredient = new Ingredient();
    //ingredient.setId(1L);
    //final Recipe recipe = new Recipe();
    //recipe.addIngredient(ingredient);
    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(new Recipe()));
    // When
    ingredientService.deleteById(1L, 1L);
    // Then
    verify(recipeRepository, times(1)).findById(any());
  }

  @Test
  public void deleteIngredientCommand() {
    // Given
    final Ingredient ingredient = new Ingredient();
    ingredient.setId(1L);
    final Recipe recipe = new Recipe();
    recipe.addIngredient(ingredient);
    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
    // When
    ingredientService.deleteById(1L, 1L);
    final IngredientCommand deletedCommand = ingredientService.findByRecipeIdAndIngredientId(1L,
        1L);
    // Then
    assertNull(deletedCommand);
    verify(recipeRepository, times(2)).findById(anyLong());
  }
}
