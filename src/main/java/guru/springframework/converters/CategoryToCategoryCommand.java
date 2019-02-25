package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by carlosmartinez on 2019-02-24 15:37
 */
@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {
  @Synchronized
  @Nullable
  @Override
  public CategoryCommand convert(final Category source) {
    if (source == null) {
      return null;
    }
    final CategoryCommand command = new CategoryCommand();
    command.setId(source.getId());
    command.setDescription(source.getDescription());
    return command;
  }
}
