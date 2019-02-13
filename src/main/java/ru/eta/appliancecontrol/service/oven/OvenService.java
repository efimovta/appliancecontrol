package ru.eta.appliancecontrol.service.oven;

import ru.eta.appliancecontrol.domain.Oven;
import ru.eta.appliancecontrol.domain.embeddable.CookingParam;

public interface OvenService {

    Oven setIsDoorOpen(long ovenId, boolean isDoorMustBeOpen);

    Oven setIsLightBulbShine(long ovenId, boolean isLightBulbMustShine);

    Oven getOven(long ovenId);

    Oven setRecipeAndItsCookingParam(long ovenId, long recipeId);

    Oven setCookingParam(long ovenId, CookingParam cookingParam);

    Oven setIsCookingMustGoOn(long ovenId, boolean isCookingMustGoOn);
}
