package guru.springframework.services;

import guru.springframework.domain.Recipe;
import java.util.Set;

/**
 * Created by carlosmartinez on 2018-12-10 12:45
 */
public interface RecipeService {

  Set<Recipe> getRecipes();
}
