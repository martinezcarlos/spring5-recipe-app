package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/** Created by carlosmartinez on 2019-02-22 16:38 */
public class RecipeControllerTest {
  @Mock private RecipeService recipeService;
  @Mock private RecipeController recipeController;
  private MockMvc mockMvc;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    recipeController = new RecipeController(recipeService);
    mockMvc =
        MockMvcBuilders.standaloneSetup(recipeController)
            .setControllerAdvice(new ControllerExceptionHandler())
            .build();
  }

  @Test
  public void showById() throws Exception {
    final Recipe recipe = new Recipe();
    recipe.setId(1L);

    final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

    when(recipeService.findById(anyLong())).thenReturn(recipe);

    mockMvc
        .perform(get("/recipe/1/show"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/show"));
  }

  @Test
  public void testGetNewRecipeForm() throws Exception {
    final RecipeCommand command = new RecipeCommand();

    mockMvc
        .perform(get("/recipe/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/recipeform"))
        .andExpect(model().attributeExists("recipe"));
  }

  @Test
  public void testPostNewRecipeFormValidationFail() throws Exception {
    final RecipeCommand command = new RecipeCommand();
    command.setId(2L);

    when(recipeService.saveRecipeCommand(any())).thenReturn(command);

    mockMvc
        .perform(
            post("/recipe/saveOrUpdate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", ""))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("recipe"))
        .andExpect(view().name("recipe/recipeform"));
  }

  @Test
  public void testPostNewRecipeForm() throws Exception {
    final RecipeCommand command = new RecipeCommand();
    command.setId(2L);

    when(recipeService.saveRecipeCommand(any())).thenReturn(command);

    mockMvc
        .perform(
            post("/recipe/saveOrUpdate")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some desc")
                .param("directions", "some direct"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/2/show"));
  }

  @Test
  public void testGetUpdateView() throws Exception {
    final RecipeCommand command = new RecipeCommand();
    command.setId(1L);

    when(recipeService.findCommandById(anyLong())).thenReturn(command);

    mockMvc
        .perform(get("/recipe/1/update"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/recipeform"))
        .andExpect(model().attributeExists("recipe"));
  }

  @Test
  public void deleteById() throws Exception {
    // Given
    // When
    mockMvc
        .perform(get("/recipe/2/delete"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"));
    // Then
    verify(recipeService, times(1)).deleteById(anyLong());
  }

  @Test
  public void recipeNotFound() throws Exception {
    when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);
    mockMvc
        .perform(get("/recipe/1/show"))
        .andExpect(status().isNotFound())
        .andExpect(view().name("404error"));
  }

  @Test
  public void recipeNumberFormatException() throws Exception {
    mockMvc
        .perform(get("/recipe/string/show"))
        .andExpect(status().isBadRequest())
        .andExpect(view().name("400error"));
  }
}
