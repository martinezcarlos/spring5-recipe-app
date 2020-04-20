package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import java.util.Set;

/** Created by carlosmartinez on 2019-02-28 21:44 */
public interface UnitOfMeasureService {

  Set<UnitOfMeasureCommand> findAllUnitOfMeasuresCommand();
}
