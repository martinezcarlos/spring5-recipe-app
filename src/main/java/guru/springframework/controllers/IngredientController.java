package guru.springframework.controllers;

import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by carlosmartinez on 2019-02-27 16:47
 */
@RequiredArgsConstructor
@Controller
public class IngredientController {

  private final RecipeService recipeService;
  private final IngredientService ingredientService;

  @GetMapping("/recipe/{recipeId}/ingredients")
  public String listIngredients(@PathVariable final long recipeId, final Model model) {
    model.addAttribute("recipe", recipeService.findCommandById(recipeId));
    return "recipe/ingredient/list";
  }

  @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
  public String listIngredients(@PathVariable final long recipeId,
      @PathVariable final long ingredientId, final Model model) {
    model.addAttribute("ingredient",
        ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
    return "recipe/ingredient/show";
  }
}
