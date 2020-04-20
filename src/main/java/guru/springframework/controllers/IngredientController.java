package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/** Created by carlosmartinez on 2019-02-27 16:47 */
@RequiredArgsConstructor
@RequestMapping("/recipe/{recipeId}/")
@Controller
public class IngredientController {

  private final RecipeService recipeService;
  private final IngredientService ingredientService;
  private final UnitOfMeasureService unitOfMeasureService;

  @GetMapping("ingredients")
  public String listIngredients(@PathVariable final long recipeId, final Model model) {
    model.addAttribute("recipe", recipeService.findCommandById(recipeId));
    return "recipe/ingredient/list";
  }

  @GetMapping("ingredient/{ingredientId}/show")
  public String listIngredients(
      @PathVariable final long recipeId, @PathVariable final long ingredientId, final Model model) {
    model.addAttribute(
        "ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
    return "recipe/ingredient/show";
  }

  @GetMapping("ingredient/{ingredientId}/update")
  public String updateIngredient(
      @PathVariable final long recipeId, @PathVariable final long ingredientId, final Model model) {
    model.addAttribute(
        "ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
    model.addAttribute("uomList", unitOfMeasureService.findAllUnitOfMeasuresCommand());
    return "recipe/ingredient/ingredientform";
  }

  @PostMapping("ingredient")
  public String saveOrUpdate(@ModelAttribute final IngredientCommand command) {
    final IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
    return "redirect:/recipe/"
        + savedCommand.getRecipeId()
        + "/ingredient/"
        + savedCommand.getId()
        + "/show";
  }

  @GetMapping("ingredient/new")
  public String newIngredient(@PathVariable final long recipeId, final Model model) {
    final RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
    // TODO: raise exception if null
    final IngredientCommand ingredientCommand = new IngredientCommand();
    ingredientCommand.setRecipeId(recipeId);
    ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

    model.addAttribute("ingredient", ingredientCommand);
    model.addAttribute("uomList", unitOfMeasureService.findAllUnitOfMeasuresCommand());
    return "recipe/ingredient/ingredientform";
  }

  @PostMapping
  @RequestMapping("ingredient/{ingredientId}/delete")
  public String deleteIngredient(
      @PathVariable final long recipeId, @PathVariable final long ingredientId) {
    ingredientService.deleteById(recipeId, ingredientId);
    return "redirect:/recipe/" + recipeId + "/ingredients";
  }
}
