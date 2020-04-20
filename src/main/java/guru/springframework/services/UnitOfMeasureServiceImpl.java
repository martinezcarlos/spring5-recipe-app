package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.repositories.UnitOfMeasureRepository;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/** Created by carlosmartinez on 2019-02-28 21:45 */
@RequiredArgsConstructor
@Log4j2
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

  private final UnitOfMeasureRepository unitOfMeasureRepository;
  private final UnitOfMeasureToUnitOfMeasureCommand converter;

  @Override
  public Set<UnitOfMeasureCommand> findAllUnitOfMeasuresCommand() {
    log.debug("=== Looking for all the unit of measures ===");
    return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
        .map(converter::convert)
        // .sorted(Comparator.comparing(UnitOfMeasureCommand::getDescription))
        .collect(Collectors.toSet());
  }
}
