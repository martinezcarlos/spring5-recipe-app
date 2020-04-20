package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import java.util.Collections;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/** Created by carlosmartinez on 2018-12-27 14:14 */
public class IndexControllerTest {

  @Mock private RecipeService recipeService;
  @Mock private Model model;

  private IndexController indexController;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    indexController = new IndexController(recipeService);
  }

  @Test
  public void testMockMVC() throws Exception {
    final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
  }

  @Test
  public void getIndexPageText() {
    // When
    final String indexPage = indexController.getIndexPage(model);
    // Then
    assertEquals("index", indexPage);
    verify(recipeService, times(1)).getRecipes();
    verify(model, times(1)).addAttribute(eq("recipes"), anySet());
  }

  @Test
  public void recipeStoredInModel() {
    // Given
    final Recipe recipe = new Recipe();
    recipe.setDescription("Dummy description");
    when(recipeService.getRecipes()).thenReturn(Collections.singleton(recipe));
    final ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
    // When
    indexController.getIndexPage(model);
    // Then
    verify(recipeService, times(1)).getRecipes();
    verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
    assertEquals(recipe, argumentCaptor.getValue().iterator().next());
  }
}
