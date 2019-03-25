package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by carlosmartinez on 05/12/2018 18:21
 */
@Component
@Log4j2
@RequiredArgsConstructor
@Profile({
    "dev",
    "prod"
})
public class DataLoaderMySQL implements ApplicationListener<ContextRefreshedEvent> {

  private final CategoryRepository categoryRepository;
  private final UnitOfMeasureRepository unitOfMeasureRepository;

  @Override
  public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {

    if (categoryRepository.count() == 0L) {
      log.debug("Loading Categories");
      loadCategories();
    }

    if (unitOfMeasureRepository.count() == 0L) {
      log.debug("Loading UOMs");
      loadUom();
    }
  }

  private void loadCategories() {
    final Category cat1 = new Category();
    cat1.setDescription("American");
    categoryRepository.save(cat1);

    final Category cat2 = new Category();
    cat2.setDescription("Italian");
    categoryRepository.save(cat2);

    final Category cat3 = new Category();
    cat3.setDescription("Mexican");
    categoryRepository.save(cat3);

    final Category cat4 = new Category();
    cat4.setDescription("Fast Food");
    categoryRepository.save(cat4);
  }

  private void loadUom() {
    final UnitOfMeasure uom1 = new UnitOfMeasure();
    uom1.setDescription("Teaspoon");
    unitOfMeasureRepository.save(uom1);

    final UnitOfMeasure uom2 = new UnitOfMeasure();
    uom2.setDescription("Tablespoon");
    unitOfMeasureRepository.save(uom2);

    final UnitOfMeasure uom3 = new UnitOfMeasure();
    uom3.setDescription("Cup");
    unitOfMeasureRepository.save(uom3);

    final UnitOfMeasure uom4 = new UnitOfMeasure();
    uom4.setDescription("Pinch");
    unitOfMeasureRepository.save(uom4);

    final UnitOfMeasure uom5 = new UnitOfMeasure();
    uom5.setDescription("Ounce");
    unitOfMeasureRepository.save(uom5);

    final UnitOfMeasure uom6 = new UnitOfMeasure();
    uom6.setDescription("Each");
    unitOfMeasureRepository.save(uom6);

    final UnitOfMeasure uom7 = new UnitOfMeasure();
    uom7.setDescription("Pint");
    unitOfMeasureRepository.save(uom7);

    final UnitOfMeasure uom8 = new UnitOfMeasure();
    uom8.setDescription("Dash");
    unitOfMeasureRepository.save(uom8);
  }
}
