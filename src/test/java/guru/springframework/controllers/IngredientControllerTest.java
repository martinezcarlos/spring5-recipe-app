package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import java.util.HashSet;
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

/**
 * Created by carlosmartinez on 2019-02-27 16:52
 */
public class IngredientControllerTest {

  @Mock
  private RecipeService recipeService;
  @Mock
  private IngredientService ingredientService;
  @Mock
  private UnitOfMeasureService unitOfMeasureService;
  private IngredientController controller;
  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void listIngredients() throws Exception {
    // Given
    final RecipeCommand command = new RecipeCommand();
    when(recipeService.findCommandById(anyLong())).thenReturn(command);
    // When
    mockMvc.perform(get("/recipe/1/ingredients"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredient/list"))
        .andExpect(model().attributeExists("recipe"));
    // Then
    verify(recipeService, times(1)).findCommandById(anyLong());
  }

  @Test
  public void showIngredient() throws Exception {
    // Given
    final IngredientCommand command = new IngredientCommand();
    when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);
    // When
    mockMvc.perform(get("/recipe/1/ingredient/2/show"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredient/show"))
        .andExpect(model().attributeExists("ingredient"));
    // Then
    verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
  }

  @Test
  public void updateIngredient() throws Exception {
    //given
    final IngredientCommand ingredientCommand = new IngredientCommand();

    //when
    when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(
        ingredientCommand);
    when(unitOfMeasureService.findAllUnitOfMeasuresCommand()).thenReturn(new HashSet<>());

    //then
    mockMvc.perform(get("/recipe/1/ingredient/2/update"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredient/ingredientform"))
        .andExpect(model().attributeExists("ingredient"))
        .andExpect(model().attributeExists("uomList"));
  }

  @Test
  public void saveOrUpdate() throws Exception {
    //given
    final IngredientCommand command = new IngredientCommand();
    command.setId(3L);
    command.setRecipeId(2L);

    //when
    when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

    //then
    mockMvc.perform(post("/recipe/2/ingredient").contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("id", "")
        .param("description", "some string"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
  }

  @Test
  public void newIngredient() throws Exception {
    // Given
    final RecipeCommand command = new RecipeCommand();
    command.setId(1L);

    // WHen
    when(recipeService.findCommandById(anyLong())).thenReturn(command);
    when(unitOfMeasureService.findAllUnitOfMeasuresCommand()).thenReturn(new HashSet<>());

    // Then
    mockMvc.perform(get("/recipe/1/ingredient/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredient/ingredientform"))
        .andExpect(model().attributeExists("ingredient"))
        .andExpect(model().attributeExists("uomList"));

    verify(recipeService, times(1)).findCommandById(anyLong());
  }

  @Test
  public void deleteIngredient() throws Exception {
    // Given
    // When
    // Then
    mockMvc.perform(get("/recipe/1/ingredient/1/delete"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/recipe/1/ingredients"));
    verify(ingredientService, times(1)).deleteById(anyLong(), anyLong());
  }
}
