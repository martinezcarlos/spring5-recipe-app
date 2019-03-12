package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import java.util.HashSet;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * Created by carlosmartinez on 2018-12-10 12:46
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

  private final RecipeRepository recipeRepository;
  private final RecipeCommandToRecipe recipeCommandToRecipe;
  private final RecipeToRecipeCommand recipeToRecipeCommand;

  @Override
  @Transactional
  public void deleteById(final Long id) {
    log.debug("=== Deleting recipe with id {} ===", id);
    recipeRepository.deleteById(id);
  }

  @Override
  public Set<Recipe> getRecipes() {
    log.debug("=== Getting recipes ===");
    final Set<Recipe> recipes = new HashSet<>();
    recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
    log.debug("=== {} recipes found ===", recipes.size());
    return recipes;
  }

  @Override
  public Recipe findById(final Long id) {
    log.debug("=== Getting recipe with id {} ===", id);
    return recipeRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Recipe not found for id " + id));
  }

  @Override
  @Transactional
  public RecipeCommand saveRecipeCommand(final RecipeCommand command) {
    final Recipe deatachedEntity = recipeCommandToRecipe.convert(command);
    final Recipe savedRecipe = recipeRepository.save(deatachedEntity);
    log.debug("Saved recipe " + savedRecipe.getId());
    return recipeToRecipeCommand.convert(savedRecipe);
  }

  @Override
  @Transactional
  public RecipeCommand findCommandById(final Long l) {
    return recipeToRecipeCommand.convert(findById(l));
  }
}
