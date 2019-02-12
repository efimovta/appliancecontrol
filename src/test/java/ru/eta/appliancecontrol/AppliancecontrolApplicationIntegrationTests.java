package ru.eta.appliancecontrol;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AppliancecontrolApplicationIntegrationTests {

	private static final String API = "/api/v1/oven";
	private static final long OVEN_ID = 1;
	private static final long OVEN_WRONG_ID = 123123;


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
		this.mockMvc.perform(get(API + '/' + OVEN_ID))
				.andDo(print())
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.id").value(OVEN_ID))
				.andExpect(jsonPath("$.door").value(false))
				.andExpect(jsonPath("$.temperature").value(0))
				.andReturn();
	}

	@Test
	public void getOvenWithWrongId() throws Exception {
		this.mockMvc.perform(get(API + '/' + OVEN_WRONG_ID))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andReturn();
	}

}

