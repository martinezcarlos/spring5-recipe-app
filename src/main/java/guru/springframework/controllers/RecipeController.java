package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by carlosmartinez on 05/12/2018 10:08
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe/")
public class RecipeController {

  private final RecipeService recipeService;

  @PostMapping("saveOrUpdate")
  public String saveOrUpdate(@ModelAttribute final RecipeCommand command) {
    final RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
    return "redirect:/recipe/" + savedCommand.getId() + "/show";
  }

  @GetMapping("{id}/show")
  public ModelAndView showById(@PathVariable final String id) {
    final ModelAndView mav = new ModelAndView("recipe/show");
    mav.addObject(recipeService.findById(Long.valueOf(id)));
    return mav;
  }

  @GetMapping("new")
  public String newRecipe(final Model model) {
    model.addAttribute("recipe", new RecipeCommand());
    return "recipe/recipeform";
  }

  @GetMapping("{id}/update")
  public String updateRecipe(@PathVariable final String id, final Model model) {
    model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
    return "recipe/recipeform";
  }

  @GetMapping("{id}/delete")
  public String deleteById(@PathVariable final String id) {
    recipeService.deleteById(Long.valueOf(id));
    return "redirect:/";
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ModelAndView handleNotFound(final Exception exception) {
    final ModelAndView mav = new ModelAndView("404error");
    mav.addObject("exception", exception);
    return mav;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(NumberFormatException.class)
  public ModelAndView handleNumberFormatException(final Exception exception) {
    final ModelAndView mav = new ModelAndView("400error");
    mav.addObject("exception", exception);
    return mav;
  }
}
