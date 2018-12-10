package guru.springframework.repositories;

import guru.springframework.domain.Category;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by carlosmartinez on 04/12/2018 15:44
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

  Optional<Category> findByDescription(String description);

  Iterable<Category> findAllByDescription(Iterable<String> descriptions);
}
