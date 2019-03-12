package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by carlosmartinez on 05/12/2018 10:08
 */
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/recipe/")
@Controller
public class RecipeController {

  public static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
  private final RecipeService recipeService;

  @PostMapping("saveOrUpdate")
  public String saveOrUpdate(@Valid @ModelAttribute("recipe") final RecipeCommand command,
      final BindingResult result) {
    if (result.hasErrors()) {
      result.getAllErrors().forEach(log::debug);
      return RECIPE_RECIPEFORM_URL;
    }
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
    return RECIPE_RECIPEFORM_URL;
  }

  @GetMapping("{id}/update")
  public String updateRecipe(@PathVariable final String id, final Model model) {
    model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
    return RECIPE_RECIPEFORM_URL;
  }

  @GetMapping("{id}/delete")
  public String deleteById(@PathVariable final String id) {
    recipeService.deleteById(Long.valueOf(id));
    return "redirect:/";
  }
}
