package ru.eta.appliancecontrol.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.eta.appliancecontrol.domain.Oven;
import ru.eta.appliancecontrol.exception.OvenByIdNotFoundException;
import ru.eta.appliancecontrol.service.OvenService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(OvenRestController.class)
public class OvenRestControllerTest {

    private static final String API = "/api/v1/oven";
    private static final long OVEN_ID = 1;
    private static final long OVEN_WRONG_ID = 123123;


    @MockBean
    private OvenService ovenService;


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void lightOn() throws Exception {
        this.mockMvc.perform(put(API + '/' + OVEN_ID + "/lightBulb")
                .content("\"true\"")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void openDoor() throws Exception {
        this.mockMvc.perform(put(API + '/' + OVEN_ID + "/door")
                .content("\"true\"")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void getOven() throws Exception {
        Oven oven = new Oven();
        oven.setId(OVEN_ID);
        when(ovenService.getOven(OVEN_ID)).thenReturn(oven);

        this.mockMvc.perform(get(API + '/' + OVEN_ID))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(OVEN_ID))
                .andExpect(jsonPath("$.door").value(false))
                .andExpect(jsonPath("$.temperature").value(0));
    }

    @Test
    public void getOvenWithWrongId() throws Exception {
        when(ovenService.getOven(OVEN_WRONG_ID)).thenThrow(new OvenByIdNotFoundException());
        this.mockMvc.perform(get(API + '/' + OVEN_WRONG_ID))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }
}