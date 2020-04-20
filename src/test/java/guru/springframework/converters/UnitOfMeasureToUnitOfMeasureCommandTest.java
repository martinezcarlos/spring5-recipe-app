package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

/** Created by carlosmartinez on 2019-02-24 15:32 */
public class UnitOfMeasureToUnitOfMeasureCommandTest {

  public static final long ID = 1L;
  public static final String DUMMY_DESCRIPTION = "Dummy description";
  private UnitOfMeasureToUnitOfMeasureCommand converter;

  @Before
  public void setUp() throws Exception {
    converter = new UnitOfMeasureToUnitOfMeasureCommand();
  }

  @Test
  public void nullConversion() {
    assertNull(converter.convert(null));
  }

  @Test
  public void notNullConversion() {
    assertNotNull(converter.convert(new UnitOfMeasure()));
  }

  @Test
  public void convert() {
    // Given
    final UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(ID);
    uom.setDescription(DUMMY_DESCRIPTION);
    // When
    final UnitOfMeasureCommand uomc = converter.convert(uom);
    // Then
    assertEquals(ID, uomc.getId().longValue());
    assertEquals(DUMMY_DESCRIPTION, uomc.getDescription());
  }
}
