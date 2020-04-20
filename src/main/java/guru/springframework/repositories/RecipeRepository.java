package guru.springframework.repositories;

import guru.springframework.domain.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Created by carlosmartinez on 04/12/2018 15:41 */
@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {}
