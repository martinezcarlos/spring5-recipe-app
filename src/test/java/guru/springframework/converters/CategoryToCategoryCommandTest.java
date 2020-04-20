package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import guru.springframework.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

/** Created by carlosmartinez on 2019-02-24 15:44 */
public class CategoryToCategoryCommandTest {

  private static final long ID = 1L;
  private static final String DUMMY_DESCRIPTION = "Dummy description";
  private CategoryToCategoryCommand converter;

  @Before
  public void setUp() throws Exception {
    converter = new CategoryToCategoryCommand();
  }

  @Test
  public void nullConversion() {
    assertNull(converter.convert(null));
  }

  @Test
  public void notNullConversion() {
    final Category category = new Category();
    assertNotNull(converter.convert(category));
    assertNotNull(category.getRecipes());
  }

  @Test
  public void testRecipeSet() {
    // Given
    final Recipe recipe = new Recipe();
    recipe.setId(1L);
    final Category category = new Category();
    category.getRecipes().add(recipe);

    // When

    // Then
    assertEquals(recipe, category.getRecipes().iterator().next());
  }

  @Test
  public void convert() {
    // Given
    final Category entity = new Category();
    entity.setId(ID);
    entity.setDescription(DUMMY_DESCRIPTION);
    // When
    final CategoryCommand command = converter.convert(entity);
    // Then
    assertEquals(ID, command.getId().longValue());
    assertEquals(DUMMY_DESCRIPTION, command.getDescription());
  }
}
