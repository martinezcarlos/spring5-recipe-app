package guru.springframework.commands;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by carlosmartinez on 2019-02-24 14:36
 */
@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
  private Long id;
  private String description;
  private BigDecimal amount;
  private UnitOfMeasureCommand unitOfMeasure;
}
