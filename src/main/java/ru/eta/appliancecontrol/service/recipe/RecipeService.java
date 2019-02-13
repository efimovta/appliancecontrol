package ru.eta.appliancecontrol.service.recipe;

import ru.eta.appliancecontrol.domain.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> getAll();

    Recipe getRecipe(long recipeId);
}
