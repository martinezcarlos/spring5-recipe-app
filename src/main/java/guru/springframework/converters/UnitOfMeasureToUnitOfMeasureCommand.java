package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/** Created by carlosmartinez on 2019-02-24 15:00 */
@Component
public class UnitOfMeasureToUnitOfMeasureCommand
    implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {
  @Synchronized
  @Nullable
  @Override
  public UnitOfMeasureCommand convert(final UnitOfMeasure source) {
    if (source == null) {
      return null;
    }
    final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
    uomc.setId(source.getId());
    uomc.setDescription(source.getDescription());
    return uomc;
  }
}
