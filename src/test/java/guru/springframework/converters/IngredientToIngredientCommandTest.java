package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/** Created by carlosmartinez on 2019-02-24 16:10 */
public class IngredientToIngredientCommandTest {

  private static final Recipe RECIPE = new Recipe();
  private static final BigDecimal AMOUNT = new BigDecimal("1");
  private static final String DESCRIPTION = "Cheeseburger";
  private static final Long UOM_ID = new Long(2L);
  private static final Long ID_VALUE = new Long(1L);

  private IngredientToIngredientCommand converter;

  @Before
  public void setUp() throws Exception {
    converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
  }

  @Test
  public void testNullConvert() throws Exception {
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception {
    assertNotNull(converter.convert(new Ingredient()));
  }

  @Test
  public void testConvertNullUOM() throws Exception {
    // given
    final Ingredient ingredient = new Ingredient();
    ingredient.setId(ID_VALUE);
    ingredient.setRecipe(RECIPE);
    ingredient.setAmount(AMOUNT);
    ingredient.setDescription(DESCRIPTION);
    ingredient.setUnitOfMeasure(null);
    // when
    final IngredientCommand ingredientCommand = converter.convert(ingredient);
    // then
    assertNull(ingredientCommand.getUnitOfMeasure());
    assertEquals(ID_VALUE, ingredientCommand.getId());
    // assertEquals(RECIPE, ingredientCommand.get);
    assertEquals(AMOUNT, ingredientCommand.getAmount());
    assertEquals(DESCRIPTION, ingredientCommand.getDescription());
  }

  @Test
  public void testConvertWithUom() throws Exception {
    // given
    final Ingredient ingredient = new Ingredient();
    ingredient.setId(ID_VALUE);
    ingredient.setRecipe(RECIPE);
    ingredient.setAmount(AMOUNT);
    ingredient.setDescription(DESCRIPTION);

    final UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(UOM_ID);

    ingredient.setUnitOfMeasure(uom);
    // when
    final IngredientCommand ingredientCommand = converter.convert(ingredient);
    // then
    assertEquals(ID_VALUE, ingredientCommand.getId());
    assertNotNull(ingredientCommand.getUnitOfMeasure());
    assertEquals(UOM_ID, ingredientCommand.getUnitOfMeasure().getId());
    assertEquals(AMOUNT, ingredientCommand.getAmount());
    assertEquals(DESCRIPTION, ingredientCommand.getDescription());
  }

  @Test
  public void testBuildQuantityDescription() {
    // Given
    final String unitDesc = "grams";
    final UnitOfMeasure uom = new UnitOfMeasure();
    uom.setDescription(unitDesc);
    final String desc = AMOUNT.toString() + " " + unitDesc + " of " + DESCRIPTION;

    final Ingredient ingredient = new Ingredient();
    ingredient.setUnitOfMeasure(uom);
    ingredient.setAmount(AMOUNT);
    ingredient.setDescription(DESCRIPTION);

    // When
    final String quantityDesc = ingredient.buildQuantityDescription();

    // Then
    assertEquals(desc, quantityDesc);
  }
}
