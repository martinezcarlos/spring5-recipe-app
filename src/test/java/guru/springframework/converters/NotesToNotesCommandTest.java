package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by carlosmartinez on 2019-02-24 16:14
 */
public class NotesToNotesCommandTest {

  private static final Long ID_VALUE = new Long(1L);
  private static final String RECIPE_NOTES = "Notes";
  private NotesToNotesCommand converter;

  @Before
  public void setUp() throws Exception {
    converter = new NotesToNotesCommand();
  }

  @Test
  public void convert() throws Exception {
    //given
    final Notes notes = new Notes();
    notes.setId(ID_VALUE);
    notes.setRecipeNotes(RECIPE_NOTES);

    //when
    final NotesCommand notesCommand = converter.convert(notes);

    //then
    assertEquals(ID_VALUE, notesCommand.getId());
    assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
  }

  @Test
  public void testNull() throws Exception {
    assertNull(converter.convert(null));
  }

  @Test
  public void testEmptyObject() throws Exception {
    assertNotNull(converter.convert(new Notes()));
  }
}
