package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

/** Created by carlosmartinez on 2019-02-24 15:39 */
public class CategoryCommandToCategoryTest {

  private static final long ID = 1L;
  private static final String DUMMY_DESCRIPTION = "Dummy description";
  private CategoryCommandToCategory converter;

  @Before
  public void setUp() throws Exception {
    converter = new CategoryCommandToCategory();
  }

  @Test
  public void nullConversion() {
    assertNull(converter.convert(null));
  }

  @Test
  public void notNullConversion() {
    assertNotNull(converter.convert(new CategoryCommand()));
  }

  @Test
  public void convert() {
    // Given
    final CategoryCommand command = new CategoryCommand();
    command.setId(ID);
    command.setDescription(DUMMY_DESCRIPTION);
    // When
    final Category entity = converter.convert(command);
    // Then
    assertEquals(ID, entity.getId().longValue());
    assertEquals(DUMMY_DESCRIPTION, entity.getDescription());
  }
}
