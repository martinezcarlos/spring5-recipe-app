package guru.springframework.controllers;

import guru.springframework.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/** Created by carlosmartinez on 05/12/2018 10:08 */
@Controller
@RequiredArgsConstructor
public class IndexController {

  private final RecipeService recipeService;

  @RequestMapping({"", "/", "/index"})
  public String getIndexPage(final Model model) {
    model.addAttribute("recipes", recipeService.getRecipes());
    return "index";
  }
}
