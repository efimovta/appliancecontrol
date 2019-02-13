package ru.eta.appliancecontrol.service.oven;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ru.eta.appliancecontrol.domain.HeatingMode;
import ru.eta.appliancecontrol.domain.Oven;
import ru.eta.appliancecontrol.domain.Recipe;
import ru.eta.appliancecontrol.domain.embeddable.CookingParam;
import ru.eta.appliancecontrol.repository.HeatingModeRepository;
import ru.eta.appliancecontrol.repository.OvenRepository;
import ru.eta.appliancecontrol.repository.RecipeRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class OvenServiceSetCookingParamAndRecipeTests {

    private final long RECIPE_ID = 1;
    private final long OVEN_ID = 1;
    private final long HEATING_MODE_ID = 1;

    private OvenServiceImpl ovenService;
    private OvenRepository ovenRepository;
    private RecipeRepository recipeRepository;
    private HeatingModeRepository heatingModeRepository;

    private Oven ovenFromDB;
    private Recipe recipeFromDB;
    private CookingParam cookingParamToSet;

    @Before
    public void init() {
        ovenRepository = mock(OvenRepository.class);
        recipeRepository = mock(RecipeRepository.class);
        heatingModeRepository = mock(HeatingModeRepository.class);
        ovenService = new OvenServiceImpl(ovenRepository, recipeRepository, heatingModeRepository);

        cookingParamToSet = new CookingParam();
        HeatingMode heatingMode = new HeatingMode();
        cookingParamToSet.setTemperature(50);
        cookingParamToSet.setCookingTimeSeconds(51);
        cookingParamToSet.setHeatingMode(heatingMode);
        heatingMode.setId(HEATING_MODE_ID);
        heatingMode.setName("Some ignored name of heating mode while set it, value from db will be used");

        HeatingMode heatingModeFromDB = new HeatingMode();
        heatingModeFromDB.setId(HEATING_MODE_ID);
        heatingModeFromDB.setName("Real heating name");

        recipeFromDB = new Recipe();
        recipeFromDB.setId(RECIPE_ID);
        recipeFromDB.setCookingParam(cookingParamToSet);

        ovenFromDB = new Oven();
        ovenFromDB.setId(OVEN_ID);

        when(ovenRepository.findById(OVEN_ID)).thenReturn(Optional.of(ovenFromDB));
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(Optional.of(recipeFromDB));
        when(heatingModeRepository.findById(HEATING_MODE_ID)).thenReturn(Optional.of(heatingModeFromDB));
    }

    @Test
    public void setRecipe_CookingParamCopiedToOvenFromRecipe() {
        assertNotEquals(ovenFromDB.getCookingParam(), recipeFromDB.getCookingParam());

        ovenService.setRecipeAndItsCookingParam(OVEN_ID, RECIPE_ID);

        ArgumentCaptor<Oven> capturedOven = ArgumentCaptor.forClass(Oven.class);
        verify(ovenRepository).save(capturedOven.capture());

        CookingParam resultedOvenCookingParam = capturedOven.getValue().getCookingParam();
        CookingParam recipeCookingParam1FromRecipe = recipeFromDB.getCookingParam();

        assertNotEquals("Transferred heating name ignored and now must be not equals",
                resultedOvenCookingParam.getHeatingMode().getName(), recipeCookingParam1FromRecipe.getHeatingMode().getName());

        recipeCookingParam1FromRecipe.getHeatingMode().setName(resultedOvenCookingParam.getHeatingMode().getName());
        assertEquals("Other params saved as is", resultedOvenCookingParam, recipeCookingParam1FromRecipe);
    }

    @Test
    public void setCookingParamToOven() {
        assertNotEquals(ovenFromDB.getCookingParam(), recipeFromDB.getCookingParam());

        ovenService.setCookingParam(ovenFromDB.getId(), cookingParamToSet);

        ArgumentCaptor<Oven> capturedOven = ArgumentCaptor.forClass(Oven.class);
        verify(ovenRepository).save(capturedOven.capture());

        CookingParam resultedOvenCookingParam = capturedOven.getValue().getCookingParam();
        HeatingMode resultedHeatingMode = resultedOvenCookingParam.getHeatingMode();

        assertNotEquals("Transferred heating name ignored and now must be not equals",
                resultedHeatingMode.getName(), cookingParamToSet.getHeatingMode().getName());

        cookingParamToSet.getHeatingMode().setName(resultedHeatingMode.getName());
        assertEquals("Other params saved as is", resultedOvenCookingParam, cookingParamToSet);
    }
}