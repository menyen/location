package com.neogeo.location;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.neogeo.location.repository.LocationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LocationApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private LocationRepository locationRepository;

	@Before
	public void deleteAllBeforeTests() throws Exception {
		locationRepository.deleteAll();
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {

		mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$._links.locations").exists());
	}

	@Test
	public void shouldCreateEntity() throws Exception {

		createTestLocation().andExpect(header().string("Location", containsString("locations/")));
	}

	@Test
	public void shouldRetrieveEntity() throws Exception {

		MvcResult mvcResult = createTestLocation().andReturn();

		String location = mvcResult.getResponse().getHeader("Location").replace("http://localhost", "");
		mockMvc.perform(get(location)).andExpect(status().isOk())
		.andExpect(jsonPath("$.address").value("Avenida Pedro Álvares Cabral"))
		.andExpect(jsonPath("$.location.type").value("Point"))
		.andExpect(jsonPath("$.location.x").value(-23.5874162))
		.andExpect(jsonPath("$.location.y").value(-46.6576336))
		.andExpect(jsonPath("$.name").value("Parque Ibirapuera"))
		.andExpect(jsonPath("$.enabled").value(true));
	}
	
	@Test
	public void shouldQueryEntity() throws Exception {

		createTestLocation();

		mockMvc.perform(
				get("/locations/search/findByNameAndLocationNear?name={name}&latitude={latitude}&longitude={longitude}&distance={distance}", 
						"Parque Ibirapuera", -46.6576336, -23.5874162, 1))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].address").value("Avenida Pedro Álvares Cabral"));
	}

	@Test
	public void shouldCheckDuplication() throws Exception {

		createTestLocation();

		mockMvc.perform(post("/locations").content(
				"{\"address\":\"Avenida Pedro Álvares Cabral\", "+
						"\"location\":{\"type\": \"Point\", \"coordinates\": [-23.5874162, -46.6576336]}, "+
				"\"name\": \"Parque Ibirapuera\", \"enabled\": true}"))
		.andExpect(status().isConflict());
	}

	@Test
	public void shouldUpdateEntity() throws Exception {

		MvcResult mvcResult = createTestLocation().andReturn();

		String location = mvcResult.getResponse().getHeader("Location").replace("http://localhost", "");

		mockMvc.perform(put(location).contentType(MediaType.APPLICATION_JSON).content(
				"{\"address\": \"Avenida Pedro Álvares Cabral\", "+
						"\"location\":{\"type\": \"Point\", \"coordinates\": [-23.5874162, -46.6576336]}, "+
				"\"name\": \"Parque Ibirapueraaa\", \"enabled\": true}"))
		.andExpect(status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(
				jsonPath("$.name").value("Parque Ibirapueraaa"));
	}

	@Test
	public void shouldPartiallyUpdateEntity() throws Exception {

		MvcResult mvcResult = createTestLocation().andReturn();

		String location = mvcResult.getResponse().getHeader("Location").replace("http://localhost", "");

		mockMvc.perform(patch(location).contentType(MediaType.APPLICATION_JSON)
				.content("{\"address\": \"Avenida Pedro Álvares Cabraaal\"}"))
		.andExpect(status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isOk())
		.andExpect(jsonPath("$.address").value("Avenida Pedro Álvares Cabraaal"));
	}
	

	@Test
	public void shouldDeleteEntity() throws Exception {

		MvcResult mvcResult = createTestLocation().andReturn();

		String location = mvcResult.getResponse().getHeader("Location").replace("http://localhost", "");
		
		mockMvc.perform(delete(location)).andExpect(status().isNoContent());

		mockMvc.perform(get(location)).andExpect(status().isOk())
		.andExpect(jsonPath("$.enabled").value(false));
	}
	
	private ResultActions createTestLocation() throws Exception{
		return mockMvc.perform(post("/locations").content(
				"{\"address\":\"Avenida Pedro Álvares Cabral\", "+
						"\"location\":{\"type\": \"Point\", \"coordinates\": [-23.5874162, -46.6576336]}, "+
				"\"name\": \"Parque Ibirapuera\", \"enabled\": true}"))
		.andExpect(status().isCreated());
	}
}
