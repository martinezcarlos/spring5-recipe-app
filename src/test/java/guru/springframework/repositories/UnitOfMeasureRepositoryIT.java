package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by carlosmartinez on 2019-01-15 13:22
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

  @Autowired
  private UnitOfMeasureRepository unitOfMeasureRepository;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void findByDescription() {
    final Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription(
        "Teaspoon");
    assertEquals("Teaspoon", uomOptional.get().getDescription());
  }

  //@Test(expected = IllegalArgumentException.class)
  //public void exceptionOnFinByDescription() {
  //  // Given
  //  final UnitOfMeasureRepository repository = mock(UnitOfMeasureRepository.class);
  //  when(repository.findByDescription(anyString())).thenReturn(Optional.empty());
  //  // When
  //  repository.findByDescription("Teaspoon");
  //  // Then
  //}
}
