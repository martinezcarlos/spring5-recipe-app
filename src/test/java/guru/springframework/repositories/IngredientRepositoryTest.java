package guru.springframework.repositories;

import guru.springframework.domain.Ingredient;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@Sql("classpath:ingredients.sql")
public class IngredientRepositoryTest {

  @Autowired private IngredientRepository ingredientRepository;

  @Test
  public void givenDescription_whenIngredientFetched_thenItsFound() {
    // Given
    final String description = "Salt";
    // When
    final Optional<Ingredient> byDescription = ingredientRepository.findByDescription(description);
    // Then
    assertThat(byDescription).get().extracting(Ingredient::getDescription).isEqualTo(description);
  }
}
