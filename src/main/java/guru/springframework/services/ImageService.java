package guru.springframework.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by carlosmartinez on 2019-03-02 12:19
 */
public interface ImageService {

  void saveImageFile(Long recipeId, MultipartFile file);
}
