package ru.eta.appliancecontrol.service.oven;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ru.eta.appliancecontrol.domain.Oven;
import ru.eta.appliancecontrol.domain.Recipe;
import ru.eta.appliancecontrol.domain.embeddable.CookingParam;
import ru.eta.appliancecontrol.repository.OvenRepository;
import ru.eta.appliancecontrol.repository.RecipeRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class OvenServiceImplTest {

    private final long RECIPE_ID = 1;
    private final long OVEN_ID = 2;

    private OvenServiceImpl ovenService;
    private OvenRepository ovenRepository;
    private RecipeRepository recipeRepository;

    private Oven oven;
    private Recipe recipe;

    @Before
    public void init() {
        ovenRepository = mock(OvenRepository.class);
        recipeRepository = mock(RecipeRepository.class);
        ovenService = new OvenServiceImpl(ovenRepository, recipeRepository);

        CookingParam cp = new CookingParam();
        cp.setTemperature(123);
        cp.setCookingTimeSeconds(234);

        recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookingParam(cp);

        oven = new Oven();
        oven.setId(OVEN_ID);

        when(ovenRepository.findById(OVEN_ID)).thenReturn(Optional.of(oven));
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(Optional.of(recipe));
    }

    @Test
    public void setRecipe_CookingParamCopiedToOvenFromRecipe() {
        assertNotEquals(oven.getCookingParam(), recipe.getCookingParam());

        ovenService.setRecipeAndCookingParam(OVEN_ID, RECIPE_ID);

        ArgumentCaptor<Oven> capturedOven = ArgumentCaptor.forClass(Oven.class);
        verify(ovenRepository).save(capturedOven.capture());

        CookingParam resultedOvenCookingParam = capturedOven.getValue().getCookingParam();
        CookingParam recipeCookingParam1 = recipe.getCookingParam();

        assertEquals(resultedOvenCookingParam, recipeCookingParam1);
    }
}