package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/** Created by carlosmartinez on 2019-02-24 15:51 */
@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

  @Synchronized
  @Nullable
  @Override
  public NotesCommand convert(final Notes source) {
    if (source == null) {
      return null;
    }
    final NotesCommand notesCommand = new NotesCommand();
    notesCommand.setId(source.getId());
    notesCommand.setRecipeNotes(source.getRecipeNotes());
    return notesCommand;
  }
}
