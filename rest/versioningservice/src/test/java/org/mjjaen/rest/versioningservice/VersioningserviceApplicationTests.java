package org.mjjaen.rest.versioningservice;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mjjaen.rest.versioningservice.controller.PersonVersioningController;
import org.mjjaen.rest.versioningservice.model.businessObject.Name;
import org.mjjaen.rest.versioningservice.model.businessObject.PersonV1;
import org.mjjaen.rest.versioningservice.model.businessObject.PersonV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VersioningserviceApplicationTests {
	@Autowired
    private MockMvc mockMvc;
	
	WebTestClient client;
	
	@Before
	public void config() {
		client = WebTestClient.bindToController(new PersonVersioningController()).build();
	}
	
	@Test
	public void textV1URI() {
		client.get().uri("/webservices/v1/person")
    		.exchange()
    		.expectStatus().isOk()
    		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
    		.expectBody(PersonV1.class).isEqualTo(new PersonV1("Manuel"));
	}
	
	@Test
	public void textV2URI() {
		client.get().uri("/webservices/v2/person")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBody(PersonV2.class).isEqualTo(new PersonV2(new Name("Manuel", "Jaén")));
	}
	
	@Test
	public void textV1Param() {
		client.get().uri("/webservices/person/param?version=1")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBody(PersonV1.class).isEqualTo(new PersonV1("Manuel"));
	}
	
	@Test
	public void textV2Param() {
		client.get().uri("/webservices/person/param?version=2")
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
			.expectBody(PersonV2.class).isEqualTo(new PersonV2(new Name("Manuel", "Jaén")));
	}
	
	@Test
	public void textV1AcceptHeader() throws Exception {
		mockMvc.perform(get("/webservices/person/header").header("X-API-VERSION", "1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is("Manuel")));
	}
	
	@Test
	public void textV2AcceptHeader() throws Exception {
		mockMvc.perform(get("/webservices/person/header").header("X-API-VERSION", "2"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name.firstName", is("Manuel")))
			.andExpect(jsonPath("$.name.lastName", is("Jaén")));
	}
	
	@Test
	public void textV1ContentNegotiation() throws Exception {
		mockMvc.perform(get("/webservices/person/negotiation").header("Accept", "application/vnd.company.app-v1+json"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", is("Manuel")));
	}
	
	@Test
	public void textV2ContentNegotiation() throws Exception {
		mockMvc.perform(get("/webservices/person/negotiation").header("Accept", "application/vnd.company.app-v2+json"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name.firstName", is("Manuel")))
			.andExpect(jsonPath("$.name.lastName", is("Jaén")));
	}
}
