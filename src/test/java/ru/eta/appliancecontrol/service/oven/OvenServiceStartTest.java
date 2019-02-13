package ru.eta.appliancecontrol.service.oven;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ru.eta.appliancecontrol.domain.Oven;
import ru.eta.appliancecontrol.repository.HeatingModeRepository;
import ru.eta.appliancecontrol.repository.OvenRepository;
import ru.eta.appliancecontrol.repository.RecipeRepository;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class OvenServiceStartTest {

    private final long OVEN_ID = 1;

    private OvenServiceImpl ovenService;

    private Oven ovenFromDB;

    @Before
    public void init() {
        OvenRepository ovenRepository = mock(OvenRepository.class);
        RecipeRepository recipeRepository = mock(RecipeRepository.class);
        HeatingModeRepository heatingModeRepository = mock(HeatingModeRepository.class);
        ovenService = new OvenServiceImpl(ovenRepository, recipeRepository, heatingModeRepository);

        ovenFromDB = new Oven();
        ovenFromDB.setId(OVEN_ID);
        ovenFromDB.setDoorIsOpen(true);

        when(ovenRepository.findById(OVEN_ID)).thenReturn(Optional.of(ovenFromDB));

        Oven savedOven = new Oven();
        when(ovenRepository.save(any())).thenReturn(savedOven);
    }

    @Test
    public void startOven_doorMustCloseAutomatically() {

        assertTrue(ovenFromDB.isDoorIsOpen());

        OvenServiceImpl ovenServiceSpy = spy(ovenService);

        ovenServiceSpy.setIsCookingMustGoOn(ovenFromDB.getId(), true);

        ArgumentCaptor<Boolean> capturedOven = ArgumentCaptor.forClass(Boolean.class);
        verify(ovenServiceSpy).setIsDoorOpen(anyLong(), capturedOven.capture());

        assertFalse(capturedOven.getValue());
    }
}