package guru.springframework.commands;

import guru.springframework.domain.Difficulty;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by carlosmartinez on 2019-02-24 14:36
 */
@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
  private Long id;
  private String description;
  private Integer prepTime;
  private Integer cookTime;
  private Integer servings;
  private String source;
  private String url;
  private String directions;
  private Byte[] image;
  private Set<IngredientCommand> ingredients = new HashSet<>();
  private Difficulty difficulty;
  private NotesCommand notes;
  private Set<CategoryCommand> categories = new HashSet<>();
}
