package ru.eta.appliancecontrol.service.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.eta.appliancecontrol.domain.Recipe;
import ru.eta.appliancecontrol.exception.RecipeNotFoundException;
import ru.eta.appliancecontrol.repository.RecipeRepository;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(long id) {
        return recipeRepository.findById(id).orElseThrow(RecipeNotFoundException::new);
    }
}
