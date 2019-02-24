package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by carlosmartinez on 05/12/2018 10:08
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

  private final RecipeService recipeService;

  @RequestMapping("/show/{id}")
  public String showById(@PathVariable("id") final long id, final Model model) {
    model.addAttribute("recipe", recipeService.findById(id));
    return "recipe/show";
  }
}
