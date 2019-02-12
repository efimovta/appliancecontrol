package ru.eta.appliancecontrol;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.eta.appliancecontrol.domain.Recipe;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Sql(value = "/data.sql")
@Transactional
public class AppliancecontrolApplicationIntegrationTests {

    private static final String OVEN_API = "/api/v1/oven";
    private static final String RECIPE_API = "/api/v1/recipe";
    private static final long OVEN_ID = 1;
    private static final long OVEN_WRONG_ID = 123123;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void lightOn() throws Exception {
        this.mockMvc.perform(put(OVEN_API + '/' + OVEN_ID + "/lightBulb")
                .content("\"true\"")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void openDoor() throws Exception {
        this.mockMvc.perform(put(OVEN_API + '/' + OVEN_ID + "/door")
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
                .andExpect(jsonPath("$.door").value(false))
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

}

