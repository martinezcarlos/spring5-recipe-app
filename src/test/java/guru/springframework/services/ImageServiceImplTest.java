package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Created by carlosmartinez on 2019-03-04 20:45 */
public class ImageServiceImplTest {

  @Mock private RecipeRepository recipeRepository;

  private ImageService imageService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    imageService = new ImageServiceImpl(recipeRepository);
  }

  @Test
  public void saveImageFile() throws IOException {
    // Given
    final MultipartFile multipartFile =
        new MockMultipartFile(
            "imagefile",
            "testing.txt",
            "text/plain",
            "Spring Framework Guru".getBytes(StandardCharsets.UTF_8));
    final Recipe recipe = new Recipe();
    recipe.setId(1L);
    when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
    final ArgumentCaptor<Recipe> captor = ArgumentCaptor.forClass(Recipe.class);

    // When
    imageService.saveImageFile(1L, multipartFile);

    // Then
    verify(recipeRepository, times(1)).findById(anyLong());
    verify(recipeRepository, times(1)).save(captor.capture());
    final Recipe savedRecipe = captor.getValue();
    assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
  }
}
