package guru.springframework.repositories;

import guru.springframework.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by carlosmartinez on 05/12/2018 18:28
 */
@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
