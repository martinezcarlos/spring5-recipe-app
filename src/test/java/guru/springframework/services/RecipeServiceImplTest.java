package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by carlosmartinez on 2018-12-27 13:58
 */
public class RecipeServiceImplTest {

  private RecipeService recipeService;
  @Mock
  private RecipeRepository recipeRepository;
  @Mock
  private RecipeCommandToRecipe recipeCommandToRecipe;
  @Mock
  private RecipeToRecipeCommand recipeToRecipeCommand;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe,
        recipeToRecipeCommand);
  }

  @Test
  public void getRecipes() {
    when(recipeService.getRecipes()).thenReturn(Collections.singleton(new Recipe()));
    assertEquals(1, recipeService.getRecipes().size());
    verify(recipeRepository, times(1)).findAll();
  }

  @Test
  public void getRecipesById() {
    final Recipe recipe = new Recipe();
    recipe.setId(1L);

    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

    final Recipe recipeFound = recipeService.findById(1L);

    assertNotNull("Null recipe returned", recipeFound);
    verify(recipeRepository, times(1)).findById(anyLong());
    verify(recipeRepository, never()).findAll();
  }

  @Test
  public void deleteRecipeById() {
    // When
    recipeService.deleteById(2L);
    // Then
    verify(recipeRepository, times(1)).deleteById(anyLong());
  }

  @Test
  public void findCommandById() {
    // Given
    final Recipe recipe = new Recipe();
    recipe.setId(1L);
    final RecipeCommand commandMock = new RecipeCommand();
    commandMock.setId(1L);
    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
    when(recipeToRecipeCommand.convert(any())).thenReturn(commandMock);
    // When
    final RecipeCommand command = recipeService.findCommandById(1L);
    // Then
    assertEquals(recipe.getId(), command.getId());
  }

  @Test(expected = NotFoundException.class)
  public void recipeNotFound() {
    when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());
    recipeService.findById(1L);
  }
}
