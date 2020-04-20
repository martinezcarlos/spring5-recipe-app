package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** Created by carlosmartinez on 2019-03-04 20:27 */
@RequiredArgsConstructor
@Log4j2
@Service
public class ImageServiceImpl implements ImageService {

  private final RecipeRepository recipeRepository;

  @Override
  public void saveImageFile(final Long recipeId, final MultipartFile file) {
    try {
      final Recipe recipe = recipeRepository.findById(recipeId).get();

      final Byte[] byteObjects = new Byte[file.getBytes().length];
      int i = 0;
      for (final byte b : file.getBytes()) {
        byteObjects[i++] = b;
      }

      recipe.setImage(byteObjects);

      recipeRepository.save(recipe);
    } catch (final IOException e) {
      // todo: handle better
      log.debug(" Error occurred", e);
      e.printStackTrace();
    }
  }
}
