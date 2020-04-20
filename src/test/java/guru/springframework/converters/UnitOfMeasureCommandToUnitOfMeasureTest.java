package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/** Created by carlosmartinez on 2019-02-24 15:05 */
public class UnitOfMeasureCommandToUnitOfMeasureTest {

  public static final long ID = 1L;
  public static final String DUMMY_DESCRIPTION = "Dummy description";
  private UnitOfMeasureCommandToUnitOfMeasure converter;

  @Before
  public void setUp() throws Exception {
    converter = new UnitOfMeasureCommandToUnitOfMeasure();
  }

  @Test
  public void nullConversion() {
    assertNull(converter.convert(null));
  }

  @Test
  public void notNullConversion() {
    assertNotNull(converter.convert(new UnitOfMeasureCommand()));
  }

  @Test
  public void convert() {
    // Given
    final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
    uomc.setId(ID);
    uomc.setDescription(DUMMY_DESCRIPTION);
    // When
    final UnitOfMeasure uom = converter.convert(uomc);
    // Then
    assertEquals(ID, uom.getId().longValue());
    assertEquals(DUMMY_DESCRIPTION, uom.getDescription());
  }
}
