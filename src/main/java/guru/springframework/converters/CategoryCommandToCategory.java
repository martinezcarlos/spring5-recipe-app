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
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {
  @Synchronized
  @Nullable
  @Override
  public Category convert(final CategoryCommand source) {
    if (source == null) {
      return null;
    }
    final Category entity = new Category();
    entity.setId(source.getId());
    entity.setDescription(source.getDescription());
    return entity;
  }
}
