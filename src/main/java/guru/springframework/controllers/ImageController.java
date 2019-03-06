package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by carlosmartinez on 2019-03-02 12:18
 */
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/recipe/{recipeId}/")
@Controller
public class ImageController {

  private final ImageService imageService;
  private final RecipeService recipeService;

  @GetMapping("image")
  public String showUploadForm(@PathVariable final long recipeId, final Model model) {
    model.addAttribute("recipe", recipeService.findCommandById(recipeId));
    return "recipe/imageuploadform";
  }

  @PostMapping("image")
  public String handleImagePost(@PathVariable final long recipeId,
      @RequestParam("imagefile") final MultipartFile file) {
    imageService.saveImageFile(recipeId, file);
    return "redirect:/recipe/" + recipeId + "/show";
  }

  @GetMapping("recipeimage")
  public void renderImageFromDB(@PathVariable final long recipeId,
      final HttpServletResponse response) throws IOException {
    final RecipeCommand command = recipeService.findCommandById(recipeId);
    if (command == null || command.getImage() == null) {
      return;
    }
    final byte[] byteArray = new byte[command.getImage().length];
    int i = 0;
    for (final Byte b : command.getImage()) {
      byteArray[i++] = b;
    }
    response.setContentType("image/jpeg");
    final InputStream is = new ByteArrayInputStream(byteArray);
    IOUtils.copy(is, response.getOutputStream());
  }
}
