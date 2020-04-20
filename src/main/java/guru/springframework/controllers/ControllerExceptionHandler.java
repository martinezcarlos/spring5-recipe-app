package guru.springframework.controllers;

import guru.springframework.exceptions.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/** Created by carlosmartinez on 2019-03-12 20:53 */
@Log4j2
@ControllerAdvice
public class ControllerExceptionHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ModelAndView handleNotFound(final Exception exception) {
    log.error("Handling NotFoundException", exception);
    final ModelAndView mav = new ModelAndView("404error");
    mav.addObject("exception", exception);
    return mav;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(NumberFormatException.class)
  public ModelAndView handleNumberFormatException(final Exception exception) {
    log.error("Handling NumberFormatException", exception);
    final ModelAndView mav = new ModelAndView("400error");
    mav.addObject("exception", exception);
    return mav;
  }
}
