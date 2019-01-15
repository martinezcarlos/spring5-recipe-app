package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
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

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    recipeService = new RecipeServiceImpl(recipeRepository);
  }

  @Test
  public void getRecipes() {
    when(recipeService.getRecipes()).thenReturn(Collections.singleton(new Recipe()));
    assertEquals(1, recipeService.getRecipes().size());
    verify(recipeRepository, times(1)).findAll();
  }
}
