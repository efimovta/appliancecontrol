package ru.eta.appliancecontrol;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.eta.appliancecontrol.domain.HeatingMode;
import ru.eta.appliancecontrol.domain.Oven;
import ru.eta.appliancecontrol.domain.Recipe;
import ru.eta.appliancecontrol.domain.embeddable.CookingParam;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase
@Sql(value = "/data.sql")
@Transactional
public class AppliancecontrolApplicationIntegrationTests {

    private static final String OVEN_API = "/api/v1/oven";
    private static final String RECIPE_API = "/api/v1/recipe";
    private static final long OVEN_ID = 1;
    private static final long OVEN_WRONG_ID = 123123;
    private static final long RECIPE_ID = 1;
    private static final long HEATING_MODE_ID = 1;

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void lightOn() throws Exception {
        this.mockMvc.perform(put(OVEN_API + '/' + OVEN_ID + "/lightBulbIsOn")
                .content("\"true\"")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void openDoor() throws Exception {
        this.mockMvc.perform(put(OVEN_API + '/' + OVEN_ID + "/doorIsOpen")
                .content("\"true\"")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getOven() throws Exception {
        this.mockMvc.perform(get(OVEN_API + '/' + OVEN_ID))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(OVEN_ID))
                .andExpect(jsonPath("$.doorIsOpen").value(false))
                .andExpect(jsonPath("$.cookingParam.temperature").value(0))
                .andReturn();
    }

    @Test
    public void getOvenWithWrongId() throws Exception {
        this.mockMvc.perform(get(OVEN_API + '/' + OVEN_WRONG_ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void getAllRecipesAndCheckEquals() throws Exception {
        String jsonRecipeList = this.mockMvc.perform(get(RECIPE_API))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        InputStream stream = TypeReference.class.getResourceAsStream("/recipeList.json");
        TypeReference<List<Recipe>> type = new TypeReference<List<Recipe>>() {
        };

        List<Recipe> recipeListFromRequest = objectMapper.readValue(jsonRecipeList, type);
        List<Recipe> recipeListFromFile = objectMapper.readValue(stream, type);

        assertThat(recipeListFromRequest.size()).isEqualTo(1);
        assertEquals(recipeListFromRequest, recipeListFromFile);
    }


    @Test
    public void setRecipe_CookingParamCopiedToOvenFromRecipe() throws Exception {
        String ovenJson = this.mockMvc.perform(get(OVEN_API + '/' + OVEN_ID))
                .andReturn().getResponse().getContentAsString();
        String recipeJson = this.mockMvc.perform(get(RECIPE_API + '/' + RECIPE_ID))
                .andReturn().getResponse().getContentAsString();
        Oven oven = objectMapper.readValue(ovenJson, Oven.class);
        Recipe recipe = objectMapper.readValue(recipeJson, Recipe.class);

        assertNotEquals(oven.getCookingParam(), recipe.getCookingParam());

        String ovenAfterRecipeSetJson = this.mockMvc
                .perform(put(OVEN_API + '/' + OVEN_ID + "/recipe/id")
                        .content(Long.toString(RECIPE_ID)).contentType("application/json"))
                .andReturn().getResponse().getContentAsString();
        Oven ovenAfterRecipeSet = objectMapper.readValue(ovenAfterRecipeSetJson, Oven.class);

        CookingParam resultedOvenCookingParam = ovenAfterRecipeSet.getCookingParam();
        CookingParam recipeCookingParam1 = recipe.getCookingParam();

        assertEquals(resultedOvenCookingParam, recipeCookingParam1);
    }

    @Test
    public void setCookingParamToOven_heatingNameWillIgnored() throws Exception {
        CookingParam cookingParamToSet = new CookingParam();
        HeatingMode heatingMode = new HeatingMode();
        cookingParamToSet.setTemperature(50);
        cookingParamToSet.setCookingTimeSeconds(53);
        cookingParamToSet.setHeatingMode(heatingMode);
        heatingMode.setId(HEATING_MODE_ID);
        heatingMode.setName("Some ignored name of heating mode while set it, value from db will be used");

        String cpJson = objectMapper.writeValueAsString(cookingParamToSet);

        String ovenJson = this.mockMvc.perform(put(OVEN_API + '/' + OVEN_ID + "/cookingParam")
                .content(cpJson).contentType("application/json"))
                .andReturn().getResponse().getContentAsString();
        Oven oven = objectMapper.readValue(ovenJson, Oven.class);
        CookingParam resultedOvenCookingParam = oven.getCookingParam();
        HeatingMode resultedHeatingMode = resultedOvenCookingParam.getHeatingMode();

        assertNotEquals("Transferred heating name ignored and now no equals", resultedHeatingMode.getName(), cookingParamToSet.getHeatingMode().getName());

        cookingParamToSet.getHeatingMode().setName(resultedHeatingMode.getName());
        assertEquals("Other params saved as is", resultedOvenCookingParam, cookingParamToSet);

    }


    @Test
    public void startOven_doorMustCloseAutomatically() throws Exception {
        Oven ovenBefore = entityManager.find(Oven.class, OVEN_ID);
        ovenBefore.setDoorIsOpen(true);
        entityManager.persistAndFlush(ovenBefore);

        assertTrue(ovenBefore.isDoorIsOpen());

        this.mockMvc.perform(put(OVEN_API + '/' + OVEN_ID + "/cooking")
                .content("\"true\"").contentType("application/json"))
                .andExpect(status().isNoContent());

        Oven ovenAfter = entityManager.find(Oven.class, OVEN_ID);

        assertFalse(ovenAfter.isDoorIsOpen());
    }

}

