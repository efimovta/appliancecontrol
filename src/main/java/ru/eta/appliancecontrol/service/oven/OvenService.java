package ru.eta.appliancecontrol.service.oven;

import ru.eta.appliancecontrol.domain.Oven;

public interface OvenService {

    Oven controlDoor(long id, boolean isDoorMustBeOpen);

    Oven controlLightBulb(long id, boolean isLightBulbMustShine);

    Oven getOven(long id);

    Oven setRecipeAndCookingParam(long id, long recipeId);
}
