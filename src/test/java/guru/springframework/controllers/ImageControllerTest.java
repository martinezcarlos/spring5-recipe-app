package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import java.nio.charset.StandardCharsets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by carlosmartinez on 2019-03-02 12:26
 */
public class ImageControllerTest {

  @Mock
  private ImageService imageService;
  @Mock
  private RecipeService recipeService;
  private ImageController imageController;
  private MockMvc mockMvc;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    imageController = new ImageController(imageService, recipeService);
    mockMvc = MockMvcBuilders.standaloneSetup(imageController)
        .setControllerAdvice(new ControllerExceptionHandler())
        .build();
  }

  @Test
  public void getImageForm() throws Exception {
    // Given
    final RecipeCommand command = new RecipeCommand();
    command.setId(1L);
    when(recipeService.findCommandById(anyLong())).thenReturn(command);
    // When
    mockMvc.perform(get("/recipe/1/image"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("recipe"));
    verify(recipeService, times(1)).findCommandById(anyLong());
  }

  @Test
  public void handleImagePost() throws Exception {
    // Given
    final MockMultipartFile file = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
        "Spring Framework Guru".getBytes(StandardCharsets.UTF_8));
    // When
    // Then
    mockMvc.perform(multipart("/recipe/1/image").file(file))
        .andExpect(status().isFound())
        .andExpect(header().string("Location", "/recipe/1/show"));
    verify(imageService, times(1)).saveImageFile(anyLong(), any());
  }

  @Test
  public void renderImageFromDB() throws Exception {
    // Given
    final RecipeCommand command = new RecipeCommand();
    command.setId(1L);

    final String s = "fake image text";
    final byte[] sBytes = s.getBytes();
    final Byte[] boxed = new Byte[sBytes.length];

    int i = 0;
    for (final byte b : sBytes) {
      boxed[i++] = b;
    }

    command.setImage(boxed);

    when(recipeService.findCommandById(anyLong())).thenReturn(command);

    // When
    final MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse();

    // Then
    assertEquals(sBytes.length, response.getContentAsByteArray().length);
  }
}
