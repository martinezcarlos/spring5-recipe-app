package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by carlosmartinez on 2019-02-24 16:08
 */
public class IngredientCommandToIngredientTest {

  private static final BigDecimal AMOUNT = new BigDecimal("1");
  private static final String DESCRIPTION = "Cheeseburger";
  private static final Long ID_VALUE = new Long(1L);
  private static final Long UOM_ID = new Long(2L);

  IngredientCommandToIngredient converter;

  @Before
  public void setUp() {
    converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
  }

  @Test
  public void testNullObject() {
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() {
    assertNotNull(converter.convert(new IngredientCommand()));
  }

  @Test
  public void convert() {
    //given
    final IngredientCommand command = new IngredientCommand();
    command.setId(ID_VALUE);
    command.setAmount(AMOUNT);
    command.setDescription(DESCRIPTION);
    final UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
    unitOfMeasureCommand.setId(UOM_ID);
    command.setUnitOfMeasure(unitOfMeasureCommand);

    //when
    final Ingredient ingredient = converter.convert(command);

    //then
    assertNotNull(ingredient);
    assertNotNull(ingredient.getUnitOfMeasure());
    assertEquals(ID_VALUE, ingredient.getId());
    assertEquals(AMOUNT, ingredient.getAmount());
    assertEquals(DESCRIPTION, ingredient.getDescription());
    assertEquals(UOM_ID, ingredient.getUnitOfMeasure().getId());
  }

  @Test
  public void convertWithNullUOM() {
    //given
    final IngredientCommand command = new IngredientCommand();
    command.setId(ID_VALUE);
    command.setAmount(AMOUNT);
    command.setDescription(DESCRIPTION);

    //when
    final Ingredient ingredient = converter.convert(command);

    //then
    assertNotNull(ingredient);
    assertNull(ingredient.getUnitOfMeasure());
    assertEquals(ID_VALUE, ingredient.getId());
    assertEquals(AMOUNT, ingredient.getAmount());
    assertEquals(DESCRIPTION, ingredient.getDescription());
  }

  @Test
  public void testBuildQuantityDescription() {
    // Given
    final String unitDesc = "grams";
    final UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
    uom.setDescription(unitDesc);
    final String desc = AMOUNT.toString() + " " + unitDesc + " of " + DESCRIPTION;

    final IngredientCommand ingredient = new IngredientCommand();
    ingredient.setUnitOfMeasure(uom);
    ingredient.setAmount(AMOUNT);
    ingredient.setDescription(DESCRIPTION);

    // When
    final String quantityDesc = ingredient.buildQuantityDescription();

    // Then
    assertEquals(desc, quantityDesc);
  }
}
