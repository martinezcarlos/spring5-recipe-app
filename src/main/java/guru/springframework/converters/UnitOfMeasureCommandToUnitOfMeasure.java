package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by carlosmartinez on 2019-02-24 15:00
 */
@Component
public class UnitOfMeasureCommandToUnitOfMeasure
    implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {
  @Synchronized
  @Nullable
  @Override
  public UnitOfMeasure convert(final UnitOfMeasureCommand source) {
    if (source == null) {
      return null;
    }
    final UnitOfMeasure uom = new UnitOfMeasure();
    uom.setId(source.getId());
    uom.setDescription(source.getDescription());
    return uom;
  }
}
