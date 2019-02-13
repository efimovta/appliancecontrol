package ru.eta.appliancecontrol.service.oven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.eta.appliancecontrol.domain.HeatingMode;
import ru.eta.appliancecontrol.domain.Oven;
import ru.eta.appliancecontrol.domain.Recipe;
import ru.eta.appliancecontrol.domain.embeddable.CookingParam;
import ru.eta.appliancecontrol.exception.HeatingModeNotFoundException;
import ru.eta.appliancecontrol.exception.OvenNotFoundException;
import ru.eta.appliancecontrol.exception.RecipeNotFoundException;
import ru.eta.appliancecontrol.repository.HeatingModeRepository;
import ru.eta.appliancecontrol.repository.OvenRepository;
import ru.eta.appliancecontrol.repository.RecipeRepository;

@Service
public class OvenServiceImpl implements OvenService {

    private final OvenRepository ovenRepository;
    private final RecipeRepository recipeRepository;
    private final HeatingModeRepository heatingModeRepository;

    @Autowired
    public OvenServiceImpl(OvenRepository ovenRepository, RecipeRepository recipeRepository, HeatingModeRepository heatingModeRepository) {
        this.ovenRepository = ovenRepository;
        this.recipeRepository = recipeRepository;
        this.heatingModeRepository = heatingModeRepository;
    }

    public Oven setIsDoorOpen(long ovenId, boolean isDoorMustBeOpen) {
        Oven oven = getOven(ovenId);
        if (oven.isDoorIsOpen() == isDoorMustBeOpen) {
            return oven;
        }
        oven.setDoorIsOpen(isDoorMustBeOpen);
        return ovenRepository.save(oven);
    }

    public Oven setIsLightBulbShine(long ovenId, boolean isLightBulbMustShine) {
        Oven oven = getOven(ovenId);
        if (oven.isLightBulbIsOn() == isLightBulbMustShine) {
            return oven;
        }
        oven.setLightBulbIsOn(isLightBulbMustShine);
        return ovenRepository.save(oven);
    }

    public Oven getOven(long ovenId) {
        return ovenRepository.findById(ovenId).orElseThrow(OvenNotFoundException::new);
    }

    @Override
    public Oven setRecipeAndItsCookingParam(long ovenId, long recipeId) {
        Oven oven = getOven(ovenId);
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);

        copyCookParamAndFetchHeatingMode(recipe, oven);
        oven.setRecipe(recipe);

        return ovenRepository.save(oven);
    }

    @Override
    public Oven setCookingParam(long ovenId, CookingParam cookingParam) {
        Oven oven = getOven(ovenId);
        copyCookParamAndFetchHeatingMode(cookingParam, oven);
        return ovenRepository.save(oven);
    }

    @Override
    public Oven setIsCookingMustGoOn(long ovenId, boolean isCookingMustGoOn) {
        if (isCookingMustGoOn) {
            return startCookingOrDoNothing(ovenId);
        } else {
            return finishCookingOrDoNothing(ovenId);
        }
    }

    private Oven startCookingOrDoNothing(long ovenId) {
        Oven oven = getOven(ovenId);
        if (oven.isCooking()) {
            return oven;
        }
        oven = validateOvenStateBeforeStart(oven);
        oven.setCooking(true);
        return ovenRepository.save(oven);
    }

    private Oven validateOvenStateBeforeStart(Oven oven) {
        if (oven.isDoorIsOpen()) {
            oven = setIsDoorOpen(oven.getId(), false);
        }
        return oven;
    }

    private Oven finishCookingOrDoNothing(long ovenId) {
        Oven oven = getOven(ovenId);
        if (!oven.isCooking()) {
            return oven;
        }
        oven.setCooking(false);
        return ovenRepository.save(oven);
    }

    private void copyCookParamAndFetchHeatingMode(Recipe from, Oven to) {
        CookingParam cookingParam = from.getCookingParam();
        copyCookParamAndFetchHeatingMode(cookingParam, to);
    }

    private void copyCookParamAndFetchHeatingMode(CookingParam from, Oven to) {
        CookingParam copy = from.copy();
        long heatingModeId = copy.getHeatingMode().getId();
        HeatingMode heatingMode = heatingModeRepository.findById(heatingModeId).orElseThrow(HeatingModeNotFoundException::new);
        copy.setHeatingMode(heatingMode);
        to.setCookingParam(copy);
    }

}
