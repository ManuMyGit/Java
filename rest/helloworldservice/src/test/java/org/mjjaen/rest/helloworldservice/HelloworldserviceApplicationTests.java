package org.mjjaen.rest.helloworldservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mjjaen.rest.helloworldservice.controller.HelloWorldController;
import org.mjjaen.rest.helloworldservice.model.businessObject.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloworldserviceApplicationTests {
	@Autowired
    private MockMvc mockMvc;
	
	WebTestClient client;
	
	/*
	 * Examples:
	 * 		https://www.petrikainulainen.net/programming/spring-framework/integration-testing-of-spring-mvc-applications-write-clean-assertions-with-jsonpath/
	 * 		https://www.petrikainulainen.net/programming/spring-framework/integration-testing-of-spring-mvc-applications-rest-api-part-one/
	 * 		https://www.petrikainulainen.net/programming/spring-framework/integration-testing-of-spring-mvc-applications-rest-api-part-two/
	 */	
	
	@Before
	public void config() {
		client = WebTestClient.bindToController(new HelloWorldController()).build();
	}
	
    @Test
    public void envEndpointNotHiddenWebFlux()  {
    	client.get().uri("/webservices/hello-world?name=Turgon")
        	.exchange()
        	.expectStatus().isOk()
        	.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        	.expectBody(HelloWorldBean.class).isEqualTo(new HelloWorldBean("Turgon"));
    }
    
    @Test
    public void envEndpointNotHiddenMockMvc() throws Exception {
    	mockMvc.perform(get("/webservices/hello-world?name=Turgon"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.name", is("Hello World Turgon!")));
    }
}
