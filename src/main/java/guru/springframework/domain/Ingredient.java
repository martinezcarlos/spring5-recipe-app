package guru.springframework.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by carlosmartinez on 29/11/2018 17:19
 */
@Entity
@Data
@EqualsAndHashCode(exclude = {"recipe"})
public class Ingredient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;
  private BigDecimal amount;
  @ManyToOne
  private Recipe recipe;
  @OneToOne(fetch = FetchType.EAGER)
  private UnitOfMeasure unitOfMeasure;

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
