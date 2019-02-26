package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by carlosmartinez on 05/12/2018 10:08
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe/")
public class RecipeController {

  private final RecipeService recipeService;

  @PostMapping
  @RequestMapping("saveOrUpdate")
  public String saveOrUpdate(@ModelAttribute final RecipeCommand command) {
    final RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
    return "redirect:/recipe/" + savedCommand.getId() + "/show";
  }

  @RequestMapping("{id}/show")
  public String showById(@PathVariable("id") final long id, final Model model) {
    model.addAttribute("recipe", recipeService.findById(id));
    return "recipe/show";
  }

  @RequestMapping("new")
  public String newRecipe(final Model model) {
    model.addAttribute("recipe", new RecipeCommand());
    return "recipe/recipeform";
  }

  @RequestMapping("{id}/update")
  public String updateRecipe(@PathVariable final String id, final Model model) {
    model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
    return "recipe/recipeform";
  }
}
