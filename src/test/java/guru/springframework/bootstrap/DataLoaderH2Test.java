package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Created by carlosmartinez on 2019-02-26 19:28 */
// @RunWith(SpringRunner.class)
@DataJpaTest
public class DataLoaderH2Test {

  private DataLoaderH2 loader;
  @Mock private RecipeRepository recipeRepository;
  @Mock private CategoryRepository categoryRepository;
  @Mock private UnitOfMeasureRepository unitOfMeasureRepository;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    loader = new DataLoaderH2(recipeRepository, categoryRepository, unitOfMeasureRepository);
  }

  @Test(expected = IllegalArgumentException.class)
  public void exceptionWhenLookingForUOM() {
    // Given
    when(unitOfMeasureRepository.findByDescription(anyString())).thenReturn(Optional.empty());
    // When
    loader.onApplicationEvent(any());
    // Then
  }

  @Test
  public void normalDataLoaded() {
    // Given
    final UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(1L);
    uom.setDescription("Fake UOM");
    final Category category = new Category();
    category.setId(1L);
    category.setDescription("Fake Category");
    // When
    when(unitOfMeasureRepository.findByDescription(anyString())).thenReturn(Optional.of(uom));
    when(categoryRepository.findByDescription(anyString())).thenReturn(Optional.of(category));
    loader.onApplicationEvent(any());
    // Then
    verify(recipeRepository, times(1)).save(any());
  }
}
