package guru.springframework.commands;

import java.math.BigDecimal;
import javax.persistence.Transient;
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

  @Transient
  public String buildQuantityDescription() {
    return new StringBuilder().append(amount.toString())
        .append(" ")
        .append(unitOfMeasure.getDescription())
        .append(" of ")
        .append(description)
        .toString();
  }
}
