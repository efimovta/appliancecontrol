package ru.eta.appliancecontrol.service.oven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.eta.appliancecontrol.domain.Oven;
import ru.eta.appliancecontrol.domain.Recipe;
import ru.eta.appliancecontrol.domain.embeddable.CookingParam;
import ru.eta.appliancecontrol.exception.OvenNotFoundException;
import ru.eta.appliancecontrol.exception.RecipeNotFoundException;
import ru.eta.appliancecontrol.repository.OvenRepository;
import ru.eta.appliancecontrol.repository.RecipeRepository;

@Service
public class OvenServiceImpl implements OvenService {

    private final OvenRepository ovenRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public OvenServiceImpl(OvenRepository ovenRepository, RecipeRepository recipeRepository) {
        this.ovenRepository = ovenRepository;
        this.recipeRepository = recipeRepository;
    }

    public Oven controlDoor(long ovenId, boolean isDoorMustBeOpen) {
        Oven oven = getOven(ovenId);
        oven.setDoor(isDoorMustBeOpen);
        return ovenRepository.save(oven);
    }

    public Oven controlLightBulb(long ovenId, boolean isLightBulbMustShine) {
        Oven oven = getOven(ovenId);
        oven.setLightBulb(isLightBulbMustShine);
        return ovenRepository.save(oven);
    }

    public Oven getOven(long ovenId) {
        return ovenRepository.findById(ovenId).orElseThrow(OvenNotFoundException::new);
    }

    @Override
    public Oven setRecipeAndCookingParam(long ovenId, long recipeId) {
        Oven oven = getOven(ovenId);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);

        copyCookParamFromRecipe(recipe, oven);
        oven.setRecipe(recipe);

        return ovenRepository.save(oven);
    }

    private void copyCookParamFromRecipe(Recipe from, Oven to) {
        CookingParam copy = from.getCookingParam().copy();
        to.setCookingParam(copy);
    }

}
