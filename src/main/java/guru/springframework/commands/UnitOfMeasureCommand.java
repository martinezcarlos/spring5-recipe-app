package guru.springframework.commands;

import lombok.Data;
import lombok.NoArgsConstructor;

/** Created by carlosmartinez on 2019-02-24 14:36 */
@Data
@NoArgsConstructor
public class UnitOfMeasureCommand {
  private Long id;
  private String description;
}
