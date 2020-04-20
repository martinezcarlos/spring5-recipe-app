package guru.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Created by carlosmartinez on 2019-02-24 14:36 */
@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
  private Long id;
  private String recipeNotes;
}
