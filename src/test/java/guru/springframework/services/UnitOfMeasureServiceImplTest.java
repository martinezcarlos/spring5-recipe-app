package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import java.util.Collections;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by carlosmartinez on 2019-02-28 21:46
 */
public class UnitOfMeasureServiceImplTest {

  @Mock
  private UnitOfMeasureRepository unitOfMeasureRepository;
  @Mock
  private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
  private UnitOfMeasureService unitOfMeasureService;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,
        unitOfMeasureToUnitOfMeasureCommand);
  }

  @Test
  public void noResultsFound() {
    // Given
    final Set<UnitOfMeasure> unitOfMeasures = Collections.emptySet();
    when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);
    // When
    final Set<UnitOfMeasureCommand> unitOfMeasureCommands
        = unitOfMeasureService.findAllUnitOfMeasuresCommand();
    // Then
    assertTrue(unitOfMeasureCommands.isEmpty());
    verify(unitOfMeasureRepository, times(1)).findAll();
  }

  @Test
  public void findAllUnitOfMeasureCommand() {
    // Given
    final Set<UnitOfMeasure> unitOfMeasures = Collections.singleton(new UnitOfMeasure());
    when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);
    // When
    final Set<UnitOfMeasureCommand> unitOfMeasureCommands
        = unitOfMeasureService.findAllUnitOfMeasuresCommand();
    // Then
    assertFalse(unitOfMeasureCommands.isEmpty());
    verify(unitOfMeasureRepository, times(1)).findAll();
  }
}
