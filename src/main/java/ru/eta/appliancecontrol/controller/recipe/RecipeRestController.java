package ru.eta.appliancecontrol.controller.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.eta.appliancecontrol.domain.Recipe;
import ru.eta.appliancecontrol.service.recipe.RecipeService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/recipe")
public class RecipeRestController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> getAll() {
        return recipeService.getAll();
    }
}
